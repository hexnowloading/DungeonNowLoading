package dev.hexnowloading.skyisland;

import dev.hexnowloading.skyisland.registry.SkyislandForgeBlocks;
import dev.hexnowloading.skyisland.registry.SkyislandForgeCreativeModeTabs;
import dev.hexnowloading.skyisland.registry.SkyislandForgeItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class SkyislandForge {
    
    public SkyislandForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        SkyislandForgeBlocks.register(modEventBus);
        SkyislandForgeItems.register(modEventBus);
        SkyislandForgeCreativeModeTabs.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}