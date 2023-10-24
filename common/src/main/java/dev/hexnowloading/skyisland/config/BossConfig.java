package dev.hexnowloading.skyisland.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BossConfig {
    public static ForgeConfigSpec.BooleanValue TOGGLE_MULTIPLAYER_SCALING;
    public static ForgeConfigSpec.BooleanValue TOGGLE_BOSS_RESET;

    public static ForgeConfigSpec.DoubleValue BOSS_HEALTH_SCALE;
    public static ForgeConfigSpec.DoubleValue BOSS_DAMAGE_SCALE;
    public static ForgeConfigSpec.DoubleValue MULTIPLAYER_BOSS_HEALTH_SCALE;
    public static ForgeConfigSpec.DoubleValue MULTIPLAYER_BOSS_ATTACK_SCALE;

    public static void registerServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("multiplayer-boss-scaling");
        TOGGLE_MULTIPLAYER_SCALING = builder.comment("Whether the boss scales with number of players.").translation("toggle_multiplayer_scaling").define("toggle_multiplayer_scaling", true);
        MULTIPLAYER_BOSS_HEALTH_SCALE = builder.comment("Multiplies the boss health by this value per additional player.").translation("multiplayer_boss_health_scale").defineInRange("multiplayer_boss_health_scale", 0.5D, 0.0D, Double.MAX_VALUE);
        MULTIPLAYER_BOSS_ATTACK_SCALE = builder.comment("Multiplies the boss attack by this value per additional player.").translation("multiplayer_boss_attack_scale").defineInRange("multiplayer_boss_attack_scale", 0.2, 0.0D, Double.MAX_VALUE);
        builder.pop();
        builder.push("boss-scaling");
        BOSS_HEALTH_SCALE = builder.comment("Multiplies the boss health by this value.").translation("boss_health_scale").defineInRange("boss_health_scale", 1.0D, 0.0D, Double.MAX_VALUE);
        BOSS_DAMAGE_SCALE = builder.comment("Multiplies the boss attack damage by this value.").translation("boss_damage_scale").defineInRange("boss_damage_scale", 1.0D, 0.0D, Double.MAX_VALUE);
        TOGGLE_BOSS_RESET = builder.comment("Enables the boss to reset when no player is near the boss.").translation("toggle_boss_reset").define("toggle_boss_reset", true);

        builder.pop();
    }
}
