package dev.hexnowloading.dungeonnowloading;

import dev.hexnowloading.dungeonnowloading.entity.DNLEntityEvents;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class DNLFabric implements ModInitializer {
    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        DungeonNowLoading.init();
        registerEntityAttributes();
        DungeonNowLoading.LOGGER.info("Hello Fabric world!");
    }

    private void registerEntityAttributes() {
        for (EntityType<? extends LivingEntity> type : DNLEntityTypes.getAllAttributes().keySet()) {
            FabricDefaultAttributeRegistry.register(type, DNLEntityTypes.getAllAttributes().get(type));
        }
    }
}
