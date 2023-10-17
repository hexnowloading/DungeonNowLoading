package dev.hexnowloading.skyisland.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.entity.client.model.ChaosSpawnerModel;
import dev.hexnowloading.skyisland.entity.client.model.ChaosSpawnerProjectileModel;
import dev.hexnowloading.skyisland.entity.projectile.ChaosSpawnerProjectileEntity;
import dev.hexnowloading.skyisland.registry.SkyislandEntityTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class ChaosSpawnerProjectileRenderer<T extends ChaosSpawnerProjectileEntity> extends EntityRenderer<ChaosSpawnerProjectileEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Skyisland.MOD_ID, "textures/entity/chaos_spawner/chaos_spawner_projectile.png");
    private ChaosSpawnerProjectileModel model;
    private static final RenderType RENDER_TYPE;

    public ChaosSpawnerProjectileRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
        model = new ChaosSpawnerProjectileModel(renderManager.bakeLayer(ChaosSpawnerProjectileModel.LAYER_LOCATION));
    }

    @Override
    public void render(ChaosSpawnerProjectileEntity chaosSpawnerProjectileEntity, float v, float v1, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        //poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        float yRot = Mth.rotLerp(v1, chaosSpawnerProjectileEntity.yRotO, chaosSpawnerProjectileEntity.getYRot());
        float xRot = Mth.lerp(v1, chaosSpawnerProjectileEntity.xRotO, chaosSpawnerProjectileEntity.getXRot());
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        this.model.headAnim(yRot, xRot);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RENDER_TYPE);
        if (chaosSpawnerProjectileEntity.tickCount > 4) {
            this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.0F);
        }
        poseStack.popPose();
        super.render(chaosSpawnerProjectileEntity, v, v1, poseStack, multiBufferSource, i);
    }

    @Override
    public ResourceLocation getTextureLocation(ChaosSpawnerProjectileEntity chaosSpawnerProjectile) {
        return TEXTURE;
    }

    static {
        RENDER_TYPE = RenderType.entityTranslucent(TEXTURE);
    }
}
