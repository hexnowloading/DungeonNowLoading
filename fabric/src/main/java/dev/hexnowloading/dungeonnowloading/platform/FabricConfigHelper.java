package dev.hexnowloading.dungeonnowloading.platform;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.platform.services.ConfigHelper;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class FabricConfigHelper implements ConfigHelper {
    @Override
    public void registerConfig(ModConfig.Type type, ForgeConfigSpec spec) {
        ForgeConfigRegistry.INSTANCE.register(DungeonNowLoading.MOD_ID, type, spec);
    }
}
