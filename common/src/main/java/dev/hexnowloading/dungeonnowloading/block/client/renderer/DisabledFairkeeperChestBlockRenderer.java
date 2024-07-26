package dev.hexnowloading.dungeonnowloading.block.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.block.client.model.DisabledFairkeeperChestModel;
import dev.hexnowloading.dungeonnowloading.block.client.model.FairkeeperChestModel;
import dev.hexnowloading.dungeonnowloading.block.entity.DisabledFairkeeperChestBlockEntity;
import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperChestBlockEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DisabledFairkeeperChestBlockRenderer implements BlockEntityRenderer<DisabledFairkeeperChestBlockEntity> {

    private final DisabledFairkeeperChestModel fairkeeperChestModel;
    private static final ResourceLocation TEXTURE_OFF = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/block/entity/fairkeeper_chest_off.png");
    private static final ResourceLocation TEXTURE_ON = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/block/entity/fairkeeper_chest_on.png");

    public DisabledFairkeeperChestBlockRenderer(BlockEntityRendererProvider.Context renderer) {
        this.fairkeeperChestModel = new DisabledFairkeeperChestModel(renderer.bakeLayer(DisabledFairkeeperChestModel.LAYER_LOCATION));
    }

    @Override
    public void render(DisabledFairkeeperChestBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        //ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        poseStack.pushPose();
        Direction facing = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(facing.getRotation());
        poseStack.mulPose(Axis.XP.rotationDegrees(90));
        VertexConsumer vertexConsumer;
        if (blockEntity.getBlockState().is(DNLBlocks.FIERCE_FAIRKEEPER_CHEST.get())) {
            vertexConsumer = multiBufferSource.getBuffer(fairkeeperChestModel.renderType(TEXTURE_ON));
        } else {
            vertexConsumer = multiBufferSource.getBuffer(fairkeeperChestModel.renderType(TEXTURE_OFF));
        }

        //float openProgress = blockEntity.getOpenProgress(partialTicks);
        /*float turnFactor = 90;
        float rotationDegree = openProgress % turnFactor * 360 / turnFactor;*/
        float openProgress = blockEntity.getOpenProgress(partialTicks);
        fairkeeperChestModel.lid.xRot = -(float)(((3.0F * openProgress - 3.0F * openProgress * openProgress + openProgress * openProgress * openProgress)) * Math.PI * 0.5F);
        //fairkeeperChestModel.lid.xRot = -(float)(((1.0F - openProgress * openProgress * openProgress)) * Math.PI * 0.5F);
        fairkeeperChestModel.renderToBuffer(poseStack, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }
}
