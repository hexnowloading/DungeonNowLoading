package dev.hexnowloading.dungeonnowloading.entity.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.client.model.HollowModel;
import dev.hexnowloading.dungeonnowloading.entity.client.renderer.HollowRenderer;
import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.swing.text.html.parser.Entity;

public class HollowTransparentLayer<T extends HollowEntity, M extends HollowModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/entity/hollow_body.png");
    private static final ResourceLocation EYE_TEXTURE = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/entity/hollow.png");

    public HollowTransparentLayer(HollowRenderer renderer) {
        super(renderer);
    }

    public float oscillatingAlphaValue(HollowEntity entity, float partialTicks, float oscillationRate) {
        return ((float) Math.sin((entity.tickCount + partialTicks) * oscillationRate) + 1.0F) * 0.5F;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, HollowEntity entity, float v, float v1, float v2, float v3, float v4, float v5) {
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE));
        //VertexConsumer vertexConsumer1 = multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(EYE_TEXTURE));
        //this.getParentModel().renderToBuffer(poseStack, vertexConsumer1, i, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        this.getParentModel().renderToBuffer(poseStack, vertexConsumer, i, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, oscillatingAlphaValue(entity, v2, 0.05F));
    }
}
