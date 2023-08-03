package dev.hexnowloading.skyisland.client;

import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.registry.SkyislandEntityTypes;
import dev.hexnowloading.skyisland.world.entity.client.model.WindstoneModel;
import dev.hexnowloading.skyisland.world.entity.client.renderer.WindstoneRenderer;
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
        EntityRendererRegistry.register(SkyislandEntityTypes.WINDSTONE.get(), WindstoneRenderer::new);
    }

    private void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(WindstoneModel.LAYER_LOCATION, WindstoneModel::createBodyLayer);
    }
}
