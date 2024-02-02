package dev.hexnowloading.dungeonnowloading;

import dev.hexnowloading.dungeonnowloading.config.DNLClientConfig;
import dev.hexnowloading.dungeonnowloading.config.DNLServerConfig;
import dev.hexnowloading.dungeonnowloading.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DungeonNowLoading {

    public static final String MOD_ID = "dungeonnowloading";
    public static final String MOD_NAME = "Dungeon Now Loading";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        initRegistries();
        registerConfigs();
    }

    private static void initRegistries()
    {
        DNLEntityTypes.init();
        DNLBlocks.init();
        DNLBlockEntityTypes.init();
        DNLItems.init();
        DNLSounds.init();
        DNLFeatures.init();
        DNLStructures.init();
        DNLProcessors.init();
        DNLCreativeModeTabs.init();
    }

    private static void registerConfigs() {
        DNLServerConfig.register();
        DNLClientConfig.register();
    }
}
