package dev.hexnowloading.skyisland;

import dev.hexnowloading.skyisland.registry.SkyislandFabricBlocks;
import dev.hexnowloading.skyisland.registry.SkyislandFabricCreativeModeTabs;
import dev.hexnowloading.skyisland.registry.SkyislandFabricItems;
import net.fabricmc.api.ModInitializer;

public class SkyislandFabric implements ModInitializer {
    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        SkyislandFabricBlocks.registerBlocks();
        SkyislandFabricItems.registerItems();
        SkyislandFabricCreativeModeTabs.registerCreativeModeTabs();


    }
}
