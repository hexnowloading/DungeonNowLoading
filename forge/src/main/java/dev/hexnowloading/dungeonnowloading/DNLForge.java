package dev.hexnowloading.dungeonnowloading;

import dev.hexnowloading.dungeonnowloading.client.DNLForgeClientEvents;
import dev.hexnowloading.dungeonnowloading.entity.DNLForgeEntityEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(DungeonNowLoading.MOD_ID)
public class DNLForge {
    
    public DNLForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        DungeonNowLoading.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        //MinecraftForge.EVENT_BUS.register(this);
        addModListeners(bus);
        if (FMLEnvironment.dist.isClient()) addModClientListeners(bus);


        DungeonNowLoading.LOGGER.info("Hello Forge world!");
    }
    private void addModListeners(IEventBus bus) {
        bus.addListener(DNLForgeEntityEvents::onEntityAttributeCreation);
    }

    private void addModClientListeners(IEventBus bus) {
        bus.addListener(DNLForgeClientEvents::onRegisterRenderer);
        bus.addListener(DNLForgeClientEvents::onRegisterLayers);
    }
}