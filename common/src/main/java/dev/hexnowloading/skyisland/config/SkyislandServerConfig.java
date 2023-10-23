package dev.hexnowloading.skyisland.config;

import dev.hexnowloading.skyisland.platform.Services;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class SkyislandServerConfig {
    public static void register() {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        BossConfig.registerServerConfig(SERVER_BUILDER);

        Services.CONFIG.registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }
}
