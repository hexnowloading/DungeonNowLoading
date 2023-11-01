package dev.hexnowloading.dungeonnowloading.config;

import dev.hexnowloading.dungeonnowloading.platform.Services;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class DNLClientConfig {
    public static void register() {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        Services.CONFIG.registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }
}
