package fr.sushi.abstraction.events;

import com.mojang.logging.LogUtils;
import fr.sushi.abstraction.ModAbstraction;
import fr.sushi.abstraction.capabilities.PlayerControlCapProvider;
import fr.sushi.abstraction.capabilities.PlayerControlCapability;
import fr.sushi.abstraction.entities.Argarok;
import fr.sushi.abstraction.entities.GuardianStatue;
import fr.sushi.abstraction.entities.ModEntities;
import fr.sushi.abstraction.entities.models.ModelArgarok;
import fr.sushi.abstraction.entities.models.ModelControlBullet;
import fr.sushi.abstraction.entities.models.ModelGuardianStatue;
import fr.sushi.abstraction.entities.renderers.RendererArgarok;
import fr.sushi.abstraction.entities.renderers.RendererControlBullet;
import fr.sushi.abstraction.entities.renderers.RendererGuardianStatue;
import fr.sushi.abstraction.items.ModItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = ModAbstraction.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CommonEvents {

        public static final Logger LOGGER = LogUtils.getLogger();

        @SubscribeEvent
        public static void registerTab(CreativeModeTabEvent.Register event) {
            event.registerCreativeModeTab(new ResourceLocation(ModAbstraction.MODID, "abstraction_tab"), builder -> {
                builder.title(Component.translatable("item_group." + ModAbstraction.MODID + ".name"));
                builder.icon(() -> new ItemStack(ModItems.DEBUGGER.get()));

                builder.displayItems((enabledFlags, populator, hasPermissions) -> {
                    populator.accept(ModItems.DEBUGGER.get());
                });

                builder.build();
            });
        }

        /* Créé les attributs de base de l'entitée */
        @SubscribeEvent
        public static void onAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(ModEntities.BOSS_GOLEM.get(), Argarok.setAttributes());
            event.put(ModEntities.GUARDIAN_STATUE.get(), GuardianStatue.setAttributes());
        }

//        @SubscribeEvent
//        public static void commonSetup(final FMLCommonSetupEvent event)
//        {
//            // Some common setup code
//            LOGGER.info("HELLO FROM COMMON SETUP");
//            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
//        }
    }

    @Mod.EventBusSubscriber(modid = ModAbstraction.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientEvents {

        /* Enregistre le Renderer */
        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.BOSS_GOLEM.get(), RendererArgarok::new);
            EntityRenderers.register(ModEntities.GUARDIAN_STATUE.get(), RendererGuardianStatue::new);
            EntityRenderers.register(ModEntities.CONTROL_BULLET.get(), RendererControlBullet::new);
        }

        /* Associe le model créé dans #createBodyLayer() à l'entitée */
        @SubscribeEvent
        public static void onLayersDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModelArgarok.LAYER_LOCATION, ModelArgarok::createBodyLayer);
            event.registerLayerDefinition(ModelGuardianStatue.LAYER_LOCATION, ModelGuardianStatue::createBodyLayer);
            event.registerLayerDefinition(ModelControlBullet.BULLET, ModelControlBullet::createBodyLayer);
        }
    }

}
