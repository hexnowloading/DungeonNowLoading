package dev.hexnowloading.skyisland.platform;

import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.platform.services.ConfigHelper;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class FabricConfigHelper implements ConfigHelper {
    @Override
    public void registerConfig(ModConfig.Type type, ForgeConfigSpec spec) {
        ForgeConfigRegistry.INSTANCE.register(Skyisland.MOD_ID, type, spec);
    }
}
