package dev.hexnowloading.skyisland;

import dev.hexnowloading.skyisland.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Skyisland {

    public static final String MOD_ID = "skyisland";
    public static final String MOD_NAME = "Skyisland";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init()
    {
        initRegistries();
    }

    private static void initRegistries()
    {
        SkyislandBlocks.init();
        SkyislandBlockEntityTypes.init();
        SkyislandItems.init();
        SkyislandCreativeModeTabs.init();
        SkyislandEntityTypes.init();
    }
}
