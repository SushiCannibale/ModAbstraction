package fr.sushi.abstraction.events;

import com.mojang.logging.LogUtils;
import fr.sushi.abstraction.ModAbstraction;
import fr.sushi.abstraction.capabilities.PlayerControlCapProvider;
import fr.sushi.abstraction.capabilities.PlayerControlCapability;
import fr.sushi.abstraction.network.ModPacketManager;
import fr.sushi.abstraction.network.PlayerSyncC2SPacket;
import fr.sushi.abstraction.network.PlayerSwingC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

public class ModForgeEvents {

    public static final Logger LOGGER = LogUtils.getLogger();

    @Mod.EventBusSubscriber(modid = ModAbstraction.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeClientEvents { }

    @Mod.EventBusSubscriber(modid = ModAbstraction.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommonEvents {
        @SubscribeEvent
        public static void onRegisterCaps(RegisterCapabilitiesEvent event) {
            event.register(PlayerControlCapability.class);
        }

        @SubscribeEvent
        public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                if (!event.getObject().getCapability(PlayerControlCapProvider.CONTROL_CAP).isPresent()) {
                    event.addCapability(new ResourceLocation(ModAbstraction.MODID, "properties"), new PlayerControlCapProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;

            if (!player.level.isClientSide) {
                System.out.println("[CLIENT] X-Rot = " + player.getXRot() + " ; Y-Rot = " + player.getYRot());
                return;
            }

            System.out.println("[SERVER] X-Rot = " + player.getXRot() + " ; Y-Rot = " + player.getYRot());

            /* Mouvement & Rotation du joueur uniquement côté client */
            ModPacketManager.sendToServeur(new PlayerSyncC2SPacket(player));
        }

        @SubscribeEvent
        public static void onPlayerSwing(PlayerInteractEvent.LeftClickEmpty event) {

            // TODO : Send packet to entity to tell player attack smth
            ModPacketManager.sendToServeur(new PlayerSwingC2SPacket(event.getEntity()));
        }
    }
}
