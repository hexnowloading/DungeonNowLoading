package dev.hexnowloading.skyisland.client;

import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.entity.client.model.ChaosSpawnerModel;
import dev.hexnowloading.skyisland.entity.client.renderer.ChaosSpawnerRenderer;
import dev.hexnowloading.skyisland.registry.SkyislandEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class SkyislandFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerRenderers();
        registerModelLayers();
    }

    private void registerRenderers() {
        EntityRendererRegistry.register(SkyislandEntityTypes.CHAOS_SPAWNER.get(), ChaosSpawnerRenderer::new);
    }

    private void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(ChaosSpawnerModel.LAYER_LOCATION, ChaosSpawnerModel::createBodyLayer);
    }
}
