package dev.hexnowloading.dungeonnowloading.entity.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.dungeonnowloading.entity.client.model.ChaosSpawnerModel;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.dungeonnowloading.entity.client.renderer.ChaosSpawnerRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class ChaosSpawnerEmissiveLayer<T extends ChaosSpawnerEntity, M extends ChaosSpawnerModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/entity/chaos_spawner/chaos_spawner_glowing_eye.png");

    public ChaosSpawnerEmissiveLayer(ChaosSpawnerRenderer renderer) {
        super(renderer);
    }

    private float getAlphaForRender(ChaosSpawnerEntity entityIn, float partialTicks) {
        return ((float) Math.sin((entityIn.tickCount + partialTicks) * 0.1F) + 1.5F) * 0.1F + 0.5F;
    }

    private float getTransparentToVisible(ChaosSpawnerEntity entityIn, float partialTicks) {
        if (entityIn.getAwakeningTick() > 20) {
            return 0F;
        } else {
            return (float)(( 21 - entityIn.getAwakeningTick() - partialTicks) / 20) * 0.65F;
        }
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, ChaosSpawnerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE, true));
        if (entitylivingbaseIn.getPhase() == 0) {
            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, 15728640, LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0), 1.0F, 1.0F, 1.0F, 0F);
        } else {
            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, 15728640, LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0), 1.0F, 1.0F, 1.0F, 0.8F);
        }
    }
}
