package dev.hexnowloading.skyisland.client;

import dev.hexnowloading.skyisland.entity.ChaosSpawnerEntity;
import dev.hexnowloading.skyisland.entity.client.model.ChaosSpawnerModel;
import dev.hexnowloading.skyisland.entity.client.model.ChaosSpawnerProjectileModel;
import dev.hexnowloading.skyisland.entity.client.renderer.ChaosSpawnerProjectileRenderer;
import dev.hexnowloading.skyisland.entity.client.renderer.ChaosSpawnerRenderer;
import dev.hexnowloading.skyisland.registry.SkyislandEntityTypes;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class SkyislandForgeClientEvents {
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Bosses
        event.registerLayerDefinition(ChaosSpawnerModel.LAYER_LOCATION, ChaosSpawnerModel::createBodyLayer);

        // Projectiles
        event.registerLayerDefinition(ChaosSpawnerProjectileModel.LAYER_LOCATION, ChaosSpawnerProjectileModel::createBodyLayer);
    }
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        // Bosses
        event.registerEntityRenderer(SkyislandEntityTypes.CHAOS_SPAWNER.get(), ChaosSpawnerRenderer::new);

        // Projectiles
        event.registerEntityRenderer(SkyislandEntityTypes.CHAOS_SPAWNER_PROJECTILE.get(), ChaosSpawnerProjectileRenderer::new);
    }
}
