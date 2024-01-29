package dev.hexnowloading.dungeonnowloading.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.dungeonnowloading.entity.client.layer.ChaosSpawnerLayer;
import dev.hexnowloading.dungeonnowloading.entity.client.model.ChaosSpawnerModel;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;

public class ChaosSpawnerRenderer<T extends ChaosSpawnerEntity> extends MobRenderer<T, ChaosSpawnerModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/entity/chaos_spawner/chaos_spawner.png");
    public ChaosSpawnerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ChaosSpawnerModel<>(renderManager.bakeLayer(ChaosSpawnerModel.LAYER_LOCATION)), 1.0F);
        this.addLayer(new ChaosSpawnerLayer(this));
    }

    protected void scale(ChaosSpawnerEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(1.5F, 1.5F, 1.5F);
    }


    @Override
    protected float getFlipDegrees(T flipDegrees) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(ChaosSpawnerEntity instance) { return TEXTURE; }
}
