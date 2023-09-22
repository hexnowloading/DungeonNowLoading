package dev.hexnowloading.skyisland;

import dev.hexnowloading.skyisland.client.SkyislandForgeClientEvents;
import dev.hexnowloading.skyisland.entity.SkyislandForgeEntityEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Skyisland.MOD_ID)
public class SkyislandForge {
    
    public SkyislandForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        Skyisland.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        //MinecraftForge.EVENT_BUS.register(this);
        addModListeners(bus);
        if (FMLEnvironment.dist.isClient()) addModClientListeners(bus);


        Skyisland.LOGGER.info("Hello Forge world!");
    }
    private void addModListeners(IEventBus bus) {
        bus.addListener(SkyislandForgeEntityEvents::onEntityAttributeCreation);
    }

    private void addModClientListeners(IEventBus bus) {
        bus.addListener(SkyislandForgeClientEvents::onRegisterRenderer);
        bus.addListener(SkyislandForgeClientEvents::onRegisterLayers);
    }
}