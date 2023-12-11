package dev.hexnowloading.dungeonnowloading.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.client.layer.HollowTransparentLayer;
import dev.hexnowloading.dungeonnowloading.entity.client.model.HollowModel;
import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class HollowRenderer<T extends HollowEntity> extends MobRenderer<T, HollowModel<T>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/entity/hollow.png");

    public HollowRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HollowModel<>(renderManager.bakeLayer(HollowModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new HollowTransparentLayer<>(this));
    }

    /*@Override
    public void render(T entity, float v, float v1, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        super.render(entity, v, v1, poseStack, multiBufferSource, i);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, i, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 0.5F);
    }*/

    @Override
    public ResourceLocation getTextureLocation(HollowEntity hollowEntity) {
        return TEXTURE;
    }
}
