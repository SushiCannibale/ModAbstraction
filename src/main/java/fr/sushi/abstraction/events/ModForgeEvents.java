package fr.sushi.abstraction.events;

import fr.sushi.abstraction.ModAbstraction;
import fr.sushi.abstraction.capabilities.PlayerControlCapProvider;
import fr.sushi.abstraction.capabilities.PlayerControlCapability;
import fr.sushi.abstraction.entities.IControllable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.function.Consumer;

public class ModForgeEvents {

    @SubscribeEvent
    public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerControlCapProvider.CONTROL_CAP).isPresent()) {
                event.addCapability(new ResourceLocation(ModAbstraction.MODID, "properties"), new PlayerControlCapProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterCaps(RegisterCapabilitiesEvent event) {
        event.register(PlayerControlCapability.class);
    }

    /* Fais se déplacer l'entité controlée par le player a chaque tick */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        actionOn(player, entity -> {
            entity.move(player.getDeltaMovement());
        });
    }

    @SubscribeEvent
    public static void onPlayerSwing(PlayerInteractEvent.LeftClickEmpty event) {
        // TODO : Send a packet to the entity to tell that the player has done attack movement
//        Player player = event.getEntity();
//
//        actionOn(player, IControllable::attack);
    }

    private static void actionOn(Player player, Consumer<IControllable> consumer) {
        player.getCapability(PlayerControlCapProvider.CONTROL_CAP).ifPresent(cap -> {
            if (cap.isControllingEntity()) {
                ServerLevel sLevel = (ServerLevel)player.level;
                IControllable entity = (IControllable) sLevel.getEntity(cap.getControlled());

                if (entity != null)
                    consumer.accept(entity);
            }
        });
    }
}
