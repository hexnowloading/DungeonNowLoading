package dev.hexnowloading.skyisland.client;

import dev.hexnowloading.skyisland.entity.ChaosSpawnerEntity;
import dev.hexnowloading.skyisland.entity.client.model.ChaosSpawnerModel;
import dev.hexnowloading.skyisland.entity.client.renderer.ChaosSpawnerRenderer;
import dev.hexnowloading.skyisland.registry.SkyislandEntityTypes;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class SkyislandForgeClientEvents {
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ChaosSpawnerModel.LAYER_LOCATION, ChaosSpawnerModel::createBodyLayer);
    }
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SkyislandEntityTypes.CHAOS_SPAWNER.get(), ChaosSpawnerRenderer::new);
    }
}
