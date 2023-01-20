package fr.sushi.abstraction.items;

import fr.sushi.abstraction.ModAbstraction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> MOD_ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ModAbstraction.MODID);

    public static final RegistryObject<Item> DEBUGGER = MOD_ITEM_REGISTRY.register("debugger", () -> new Debugger(new Item.Properties()
            .durability(256)
            .rarity(Rarity.RARE)));

    public static void register(IEventBus bus) {
        MOD_ITEM_REGISTRY.register(bus);
    }
}
