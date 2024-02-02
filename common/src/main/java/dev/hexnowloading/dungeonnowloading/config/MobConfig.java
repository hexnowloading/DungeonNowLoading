package dev.hexnowloading.dungeonnowloading.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MobConfig {

    public static ForgeConfigSpec.DoubleValue POPULATE_WITH_STRONG_MOBS;
    public static ForgeConfigSpec.DoubleValue DUNGEON_MOB_HEALTH_SCALE;
    public static ForgeConfigSpec.DoubleValue DUNGEON_MOB_ATTACK_SCALE;

    public static void registerServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("mob-scaling-config");
        POPULATE_WITH_STRONG_MOBS = builder.comment("Populates the pre spawned mobs in the dungeon with stronger variants by this percentage.").translation("populate_with_strong_mobs").defineInRange("populate_with_strong_mobs", 0.1D, 0.0D, 1.0D);
        DUNGEON_MOB_HEALTH_SCALE = builder.comment("Multiplies the health of the pre spawned mob in the dungeon by this value.").translation("dungeon_mob_health_scale").defineInRange("dungeon_mob_health_scale", 1.0D, 0.0D, Double.MAX_VALUE);
        DUNGEON_MOB_ATTACK_SCALE = builder.comment("Multiplies the attack of the pre spawned mob in the dungeon by this value.").translation("dungeon_mob_attack_scale").defineInRange("dungeon_mob_attack_scale", 1.0D, 0.0D, Double.MAX_VALUE);

        builder.pop();
    }
}
