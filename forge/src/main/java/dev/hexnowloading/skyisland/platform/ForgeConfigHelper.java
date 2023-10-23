package dev.hexnowloading.skyisland.platform;

import dev.hexnowloading.skyisland.platform.services.ConfigHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ForgeConfigHelper implements ConfigHelper {
    @Override
    public void registerConfig(ModConfig.Type type, ForgeConfigSpec spec) {
        ModLoadingContext.get().registerConfig(type, spec);
    }
}
