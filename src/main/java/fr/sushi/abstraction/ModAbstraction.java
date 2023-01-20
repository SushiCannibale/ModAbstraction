package fr.sushi.abstraction;

import com.mojang.logging.LogUtils;
import fr.sushi.abstraction.entities.ModEntities;
import fr.sushi.abstraction.events.ModEvents;
import fr.sushi.abstraction.events.ModForgeEvents;
import fr.sushi.abstraction.items.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModAbstraction.MODID)
public class ModAbstraction
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "abstraction";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ModAbstraction()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModEntities.register(eventBus);

        eventBus.register(new ModEvents());
        MinecraftForge.EVENT_BUS.register(ModForgeEvents.class);
    }
}
