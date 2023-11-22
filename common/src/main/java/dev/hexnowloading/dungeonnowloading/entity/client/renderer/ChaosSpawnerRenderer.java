package dev.hexnowloading.dungeonnowloading.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.dungeonnowloading.entity.client.layer.ChaosSpawnerEmissiveLayer;
import dev.hexnowloading.dungeonnowloading.entity.client.model.ChaosSpawnerModel;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ChaosSpawnerRenderer<T extends ChaosSpawnerEntity> extends MobRenderer<T, ChaosSpawnerModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/entity/chaos_spawner/chaos_spawner_sleeping.png");
    public ChaosSpawnerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ChaosSpawnerModel<>(renderManager.bakeLayer(ChaosSpawnerModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new ChaosSpawnerEmissiveLayer(this));
    }

    protected void scale(ChaosSpawnerEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(5.0F, 5.0F, 5.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(ChaosSpawnerEntity instance) { return TEXTURE; }
}
