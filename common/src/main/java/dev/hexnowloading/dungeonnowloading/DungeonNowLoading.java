package dev.hexnowloading.dungeonnowloading;

import dev.hexnowloading.dungeonnowloading.config.DNLClientConfig;
import dev.hexnowloading.dungeonnowloading.config.DNLServerConfig;
import dev.hexnowloading.dungeonnowloading.registry.*;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.IDN;
import java.util.Locale;

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
        DNLProperties.init();
        DNLItems.init();
        DNLMobEffects.init();
        DNLSounds.init();
        DNLParticleTypes.init();
        DNLFeatures.init();
        DNLStructures.init();
        DNLProcessors.init();
        DNLCreativeModeTabs.init();
    }

    private static void registerConfigs() {
        DNLServerConfig.register();
        DNLClientConfig.register();
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
