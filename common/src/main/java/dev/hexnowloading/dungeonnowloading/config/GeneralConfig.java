package dev.hexnowloading.dungeonnowloading.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GeneralConfig {

    public static ForgeConfigSpec.BooleanValue TOGGLE_HELPFUL_ITEM_TOOLTIP;

    public static void registerGeneralConfig(ForgeConfigSpec.Builder builder) {
        builder.push("general-settings");
        TOGGLE_HELPFUL_ITEM_TOOLTIP = builder.comment("Whether the items show helpful tooltip").translation("toggle_helpful_item_tooltip").define("toggle_helpful_tooltip", true);
        builder.pop();
    }
}
