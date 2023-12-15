package dev.hexnowloading.dungeonnowloading.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class PvpConfig {

    public static ForgeConfigSpec.BooleanValue TOGGLE_PVP_MODE;

    public static void registerServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("pvp-settings");
        TOGGLE_PVP_MODE = builder.comment("Whether some features work for Pvp purpose").translation("toggle_pvp").define("toggle_pvp", false);
        builder.pop();
    }
}
