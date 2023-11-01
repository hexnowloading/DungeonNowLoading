package dev.hexnowloading.dungeonnowloading.platform;

import dev.hexnowloading.dungeonnowloading.platform.services.ConfigHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ForgeConfigHelper implements ConfigHelper {
    @Override
    public void registerConfig(ModConfig.Type type, ForgeConfigSpec spec) {
        ModLoadingContext.get().registerConfig(type, spec);
    }
}
