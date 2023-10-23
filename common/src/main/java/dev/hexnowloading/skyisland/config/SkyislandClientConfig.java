package dev.hexnowloading.skyisland.config;

import dev.hexnowloading.skyisland.platform.Services;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class SkyislandClientConfig {
    public static void register() {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        Services.CONFIG.registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }
}
