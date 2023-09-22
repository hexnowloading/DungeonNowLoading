package dev.hexnowloading.skyisland;

import dev.hexnowloading.skyisland.registry.SkyislandEntityTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class SkyislandFabric implements ModInitializer {
    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Skyisland.init();
        registerEntityAttributes();
        Skyisland.LOGGER.info("Hello Fabric world!");
    }

    private void registerEntityAttributes() {
        for (EntityType<? extends LivingEntity> type : SkyislandEntityTypes.getAllAttributes().keySet()) {
            FabricDefaultAttributeRegistry.register(type, SkyislandEntityTypes.getAllAttributes().get(type));
        }
    }
}
