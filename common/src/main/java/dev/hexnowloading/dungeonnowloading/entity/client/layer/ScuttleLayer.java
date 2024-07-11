package dev.hexnowloading.dungeonnowloading.entity.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.client.model.ScuttleModel;
import dev.hexnowloading.dungeonnowloading.entity.client.renderer.ScuttleRenderer;
import dev.hexnowloading.dungeonnowloading.entity.monster.ScuttleEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class ScuttleLayer<T extends ScuttleEntity, M extends ScuttleModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation TEXTURE_EMISSIVE = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/entity/scuttle/scuttle_emissive.png");
    public ScuttleLayer(ScuttleRenderer renderer) { super(renderer); }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLightIn, ScuttleEntity scuttleEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE_EMISSIVE, true));
        if (scuttleEntity.isState(ScuttleEntity.ScuttleState.OPENING) && !scuttleEntity.isRenderHeating()) {
            scuttleEntity.setRenderOldTick(scuttleEntity.tickCount);
            scuttleEntity.setRenderHeating(true);
            scuttleEntity.setRenderCooling(false);
        }
        if (scuttleEntity.isRenderHeating()) {
            float DURATION = 3.0F;
            float timeInSeconds = (float) (scuttleEntity.tickCount - scuttleEntity.getRenderOldTick()) / 20.0F;
            if (scuttleEntity.isState(ScuttleEntity.ScuttleState.OPENING) || scuttleEntity.isState(ScuttleEntity.ScuttleState.OPENED)) {
                this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLightIn, LivingEntityRenderer.getOverlayCoords(scuttleEntity, 0), 1.0F, 1.0F, 1.0F, timeInSeconds < DURATION ? timeInSeconds/DURATION : 1.0F);
            }
        }
        if (scuttleEntity.isState(ScuttleEntity.ScuttleState.CLOSING) && scuttleEntity.isRenderHeating()) {
            scuttleEntity.setRenderOldTick(scuttleEntity.tickCount);
            scuttleEntity.setRenderHeating(false);
            scuttleEntity.setRenderCooling(true);
        }
        if (scuttleEntity.isRenderCooling()) {
            float DURATION = 5.0F;
            float timeInSeconds = (float) (scuttleEntity.tickCount - scuttleEntity.getRenderOldTick()) / 20.0F;
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLightIn, LivingEntityRenderer.getOverlayCoords(scuttleEntity, 0), 1.0F, 1.0F, 1.0F, timeInSeconds < DURATION ? (DURATION - timeInSeconds)/DURATION : 0.0F);
        }
    }
}
