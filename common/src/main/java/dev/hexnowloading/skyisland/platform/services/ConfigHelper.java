package dev.hexnowloading.skyisland.platform.services;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public interface ConfigHelper {
    void registerConfig(ModConfig.Type type, ForgeConfigSpec spec);
}
