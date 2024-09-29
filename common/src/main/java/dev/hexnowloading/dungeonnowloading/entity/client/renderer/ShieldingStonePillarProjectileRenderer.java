package dev.hexnowloading.dungeonnowloading.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.client.model.ShieldingStonePillarProjectileModel;
import dev.hexnowloading.dungeonnowloading.entity.projectile.ShieldingStonePillarProjectileEntity;
import dev.hexnowloading.dungeonnowloading.entity.projectile.StonePillarProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ShieldingStonePillarProjectileRenderer<T extends ShieldingStonePillarProjectileEntity> extends EntityRenderer<ShieldingStonePillarProjectileEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/entity/stone_pillar/shielding_stone_pillar.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucent(TEXTURE);
    private ShieldingStonePillarProjectileModel model;

    public ShieldingStonePillarProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new ShieldingStonePillarProjectileModel(context.bakeLayer(ShieldingStonePillarProjectileModel.LAYER_LOCATION));
    }

    @Override
    public void render(ShieldingStonePillarProjectileEntity entity, float v, float v1, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.scale(-0.99f, -0.99F, 0.99F);
        poseStack.translate(0.0f, -entity.getBbHeight() + 0.5F, 0.0f);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RENDER_TYPE);
        this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(entity, v, v1, poseStack, multiBufferSource, i);
    }

    @Override
    public ResourceLocation getTextureLocation(ShieldingStonePillarProjectileEntity entity) {
        return TEXTURE;
    }
}
