package fr.sushi.abstraction.events;

import com.mojang.logging.LogUtils;
import fr.sushi.abstraction.ModAbstraction;
import fr.sushi.abstraction.capabilities.PlayerControlCapProvider;
import fr.sushi.abstraction.capabilities.PlayerControlCapability;
import fr.sushi.abstraction.entities.Argarok;
import fr.sushi.abstraction.entities.GuardianStatue;
import fr.sushi.abstraction.entities.IControllable;
import fr.sushi.abstraction.entities.ModEntities;
import fr.sushi.abstraction.entities.models.ModelArgarok;
import fr.sushi.abstraction.entities.models.ModelControlBullet;
import fr.sushi.abstraction.entities.models.ModelGuardianStatue;
import fr.sushi.abstraction.entities.renderers.RendererArgarok;
import fr.sushi.abstraction.entities.renderers.RendererControlBullet;
import fr.sushi.abstraction.entities.renderers.RendererGuardianStatue;
import fr.sushi.abstraction.items.ModItems;
import fr.sushi.abstraction.network.ModPacketManager;
import fr.sushi.abstraction.network.PlayerMoveC2SPacket;
import fr.sushi.abstraction.network.PlayerSwingC2SPacket;
import fr.sushi.abstraction.util.Util;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

import java.util.UUID;
import java.util.function.Consumer;

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

            if (!player.level.isClientSide)
                return;

            if (!(Util.isMovingAxis(player, Direction.Axis.X) || Util.isMovingAxis(player, Direction.Axis.Z)))
                return;

            /* Mouvement du joueur uniquement côté client */
            ModPacketManager.sendToServeur(new PlayerMoveC2SPacket(player));
        }

        @SubscribeEvent
        public static void onPlayerSwing(PlayerInteractEvent.LeftClickEmpty event) {
            // TODO : Send packet to entity to tell player attack smth
            ModPacketManager.sendToServeur(new PlayerSwingC2SPacket(event.getEntity()));
        }
    }
}
