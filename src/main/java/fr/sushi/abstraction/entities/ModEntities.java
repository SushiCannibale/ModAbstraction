package fr.sushi.abstraction.entities;

import fr.sushi.abstraction.ModAbstraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> MOD_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ModAbstraction.MODID);

    public static final RegistryObject<EntityType<Argarok>> BOSS_GOLEM = MOD_ENTITY_TYPES.register("argarok",
            () -> EntityType.Builder.of(Argarok::new, MobCategory.MONSTER).clientTrackingRange(10).build(
                    new ResourceLocation(ModAbstraction.MODID, "argarok").toString()));
    public static final RegistryObject<EntityType<GuardianStatue>> GUARDIAN_STATUE = MOD_ENTITY_TYPES.register("guardian_statue",
            () -> EntityType.Builder.of(GuardianStatue::new, MobCategory.MISC).sized(1.0f, 1.0f).clientTrackingRange(10).build(
                    new ResourceLocation(ModAbstraction.MODID, "guardian_statue").toString()));

    public static final RegistryObject<EntityType<ControlBullet>> CONTROL_BULLET = MOD_ENTITY_TYPES.register("control_bullet",
            () -> EntityType.Builder.<ControlBullet>of(ControlBullet::new, MobCategory.MISC).clientTrackingRange(8).build(
                    new ResourceLocation(ModAbstraction.MODID, "control_bullet").toString()));


    public static void register(IEventBus bus) {
        MOD_ENTITY_TYPES.register(bus);
    }
}



