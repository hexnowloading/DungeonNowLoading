package dev.hexnowloading.dungeonnowloading.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GeneralConfig {

    public static ForgeConfigSpec.BooleanValue TOGGLE_HELPFUL_ITEM_TOOLTIP;
    public static ForgeConfigSpec.BooleanValue TOGGLE_DESTRUCTIVE_BLOCKS;

    public static void registerServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("general-settings");
        TOGGLE_HELPFUL_ITEM_TOOLTIP = builder.comment("Whether the items show helpful tooltip").translation("toggle_helpful_item_tooltip").define("toggle_helpful_tooltip", true);
        TOGGLE_DESTRUCTIVE_BLOCKS = builder.comment("Whether the blocks added by the mod cause destruction").translation("toggle_destructive_blocks").define("toggle_destructive_blocks", false);
        builder.pop();
    }
}
