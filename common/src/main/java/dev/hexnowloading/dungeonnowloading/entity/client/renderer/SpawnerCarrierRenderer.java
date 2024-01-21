package dev.hexnowloading.dungeonnowloading.entity.client.renderer;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.client.model.SpawnerCarrierModel;
import dev.hexnowloading.dungeonnowloading.entity.monster.SpawnerCarrierEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpawnerCarrierRenderer<T extends SpawnerCarrierEntity> extends MobRenderer<T, SpawnerCarrierModel<T>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/entity/spawner_carrier.png");

    public SpawnerCarrierRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SpawnerCarrierModel<>(renderManager.bakeLayer(SpawnerCarrierModel.LAYER_LOCATION)), 1.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(SpawnerCarrierEntity spawnerCarrierEntity) {
        return TEXTURE;
    }
}
