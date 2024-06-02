package dev.hexnowloading.dungeonnowloading.block.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperChestBlockEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class FairkeeperChestItemRenderer extends BlockEntityWithoutLevelRenderer {

    private static FairkeeperChestBlockEntity blockEntity;

    public FairkeeperChestItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        if (blockEntity == null) {
            blockEntity = new FairkeeperChestBlockEntity(BlockPos.ZERO, DNLBlocks.FAIRKEEPER_CHEST.get().defaultBlockState());
        }

        poseStack.pushPose();
        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(blockEntity, poseStack, multiBufferSource, light, overlay);
        poseStack.popPose();
    }

    public static FairkeeperChestItemRenderer getInstance() {
        return new FairkeeperChestItemRenderer();
    }
}
