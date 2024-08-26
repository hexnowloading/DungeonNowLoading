package dev.hexnowloading.dungeonnowloading.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.boss.FairkeeperEntity;
import dev.hexnowloading.dungeonnowloading.entity.client.model.FairkeeperModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FairkeeperRenderer<T extends FairkeeperEntity> extends MobRenderer<T, FairkeeperModel<T>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/entity/fairkeeper/fairkeeper.png");

    public FairkeeperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FairkeeperModel<>(renderManager.bakeLayer(FairkeeperModel.LAYER_LOCATION)), 1.0F);
    }

    @Override
    protected void scale(T entity, PoseStack poseStack, float v) {
        poseStack.scale(3.0F, 3.0F, 3.0F);
        super.scale(entity, poseStack, v);
    }

    @Override
    public ResourceLocation getTextureLocation(FairkeeperEntity fairkeeperEntity) {
        return TEXTURE;
    }
}
