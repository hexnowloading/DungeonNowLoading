package dev.hexnowloading.dungeonnowloading.client;

import dev.hexnowloading.dungeonnowloading.entity.client.model.ChaosSpawnerModel;
import dev.hexnowloading.dungeonnowloading.entity.client.model.ChaosSpawnerProjectileModel;
import dev.hexnowloading.dungeonnowloading.entity.client.model.HollowModel;
import dev.hexnowloading.dungeonnowloading.entity.client.renderer.ChaosSpawnerProjectileRenderer;
import dev.hexnowloading.dungeonnowloading.entity.client.renderer.ChaosSpawnerRenderer;
import dev.hexnowloading.dungeonnowloading.entity.client.renderer.HollowRenderer;
import dev.hexnowloading.dungeonnowloading.entity.client.renderer.SpecialItemEntityRenderer;
import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class DNLFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerRenderers();
        registerModelLayers();
    }

    private void registerRenderers() {
        // Bosses
        EntityRendererRegistry.register(DNLEntityTypes.CHAOS_SPAWNER.get(), ChaosSpawnerRenderer::new);

        // Monsters
        EntityRendererRegistry.register(DNLEntityTypes.HOLLOW.get(), HollowRenderer::new);

        // Projectiles
        EntityRendererRegistry.register(DNLEntityTypes.CHAOS_SPAWNER_PROJECTILE.get(), ChaosSpawnerProjectileRenderer::new);

        // Misc
        EntityRendererRegistry.register(DNLEntityTypes.SPECIAL_ITEM_ENTITY.get(), SpecialItemEntityRenderer::new);
    }

    private void registerModelLayers() {
        // Bosses
        EntityModelLayerRegistry.registerModelLayer(ChaosSpawnerModel.LAYER_LOCATION, ChaosSpawnerModel::createBodyLayer);

        // Monsters
        EntityModelLayerRegistry.registerModelLayer(HollowModel.LAYER_LOCATION, HollowModel::createBodyLayer);

        //Projectiles
        EntityModelLayerRegistry.registerModelLayer(ChaosSpawnerProjectileModel.LAYER_LOCATION, ChaosSpawnerProjectileModel::createBodyLayer);
    }
}
