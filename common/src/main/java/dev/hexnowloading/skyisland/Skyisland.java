package dev.hexnowloading.skyisland;

import dev.hexnowloading.skyisland.config.SkyislandClientConfig;
import dev.hexnowloading.skyisland.config.SkyislandServerConfig;
import dev.hexnowloading.skyisland.registry.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class Skyisland {

    public static final String MOD_ID = "skyisland";
    public static final String MOD_NAME = "Skyisland";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        initRegistries();
        registerConfigs();
    }

    private static void initRegistries()
    {
        SkyislandEntityTypes.init();
        SkyislandBlocks.init();
        SkyislandBlockEntityTypes.init();
        SkyislandItems.init();
        SkyislandCreativeModeTabs.init();
    }

    private static void registerConfigs() {
        SkyislandServerConfig.register();
        SkyislandClientConfig.register();
    }
}
