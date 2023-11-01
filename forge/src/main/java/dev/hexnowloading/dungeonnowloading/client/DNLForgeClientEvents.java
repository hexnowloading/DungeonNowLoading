package dev.hexnowloading.dungeonnowloading.client;

import dev.hexnowloading.dungeonnowloading.entity.client.model.ChaosSpawnerModel;
import dev.hexnowloading.dungeonnowloading.entity.client.model.ChaosSpawnerProjectileModel;
import dev.hexnowloading.dungeonnowloading.entity.client.renderer.ChaosSpawnerProjectileRenderer;
import dev.hexnowloading.dungeonnowloading.entity.client.renderer.ChaosSpawnerRenderer;
import dev.hexnowloading.dungeonnowloading.entity.client.renderer.SpecialItemEntityRenderer;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class DNLForgeClientEvents {
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Bosses
        event.registerLayerDefinition(ChaosSpawnerModel.LAYER_LOCATION, ChaosSpawnerModel::createBodyLayer);

        // Projectiles
        event.registerLayerDefinition(ChaosSpawnerProjectileModel.LAYER_LOCATION, ChaosSpawnerProjectileModel::createBodyLayer);
    }
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        // Bosses
        event.registerEntityRenderer(DNLEntityTypes.CHAOS_SPAWNER.get(), ChaosSpawnerRenderer::new);

        // Projectiles
        event.registerEntityRenderer(DNLEntityTypes.CHAOS_SPAWNER_PROJECTILE.get(), ChaosSpawnerProjectileRenderer::new);

        // Misc
        event.registerEntityRenderer(DNLEntityTypes.SPECIAL_ITEM_ENTITY.get(), SpecialItemEntityRenderer::new);
    }
}
