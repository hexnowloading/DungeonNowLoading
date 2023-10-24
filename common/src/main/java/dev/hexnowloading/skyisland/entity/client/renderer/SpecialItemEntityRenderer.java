package dev.hexnowloading.skyisland.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.hexnowloading.skyisland.entity.misc.SpecialItemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SpecialItemEntityRenderer extends EntityRenderer<SpecialItemEntity> {
    private final ItemRenderer itemRenderer;
    private final RandomSource random = RandomSource.create();

    public SpecialItemEntityRenderer(EntityRendererProvider.Context $$0) {
        super($$0);
        this.itemRenderer = $$0.getItemRenderer();
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

//    @Override
//    public boolean shouldRender(SpecialItemEntity specialItemEntity, Frustum $$1, double $$2, double $$3, double $$4) {
//        //System.out.println(this.entityRenderDispatcher.camera.getEntity().getUUID());
//        //System.out.println(specialItemEntity.getPickerUUID());
////        if (this.entityRenderDispatcher.camera.getEntity().getUUID() != specialItemEntity.getPickerUUID()) {
////            return false;
////        }
//        return super.shouldRender(specialItemEntity, $$1, $$2, $$3, $$4);
//    }

    private int getRenderAmount(ItemStack itemStack) {
        int $$1 = 1;
        if (itemStack.getCount() > 48) {
            $$1 = 5;
        } else if (itemStack.getCount() > 32) {
            $$1 = 4;
        } else if (itemStack.getCount() > 16) {
            $$1 = 3;
        } else if (itemStack.getCount() > 1) {
            $$1 = 2;
        }

        return $$1;
    }

    @Override
    public void render(SpecialItemEntity specialItemEntity, float x, float y, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        ItemStack $$6 = specialItemEntity.getItem();
        int $$7 = $$6.isEmpty() ? 187 : Item.getId($$6.getItem()) + $$6.getDamageValue();
        this.random.setSeed((long)$$7);
        BakedModel $$8 = this.itemRenderer.getModel($$6, specialItemEntity.level(), (LivingEntity)null, specialItemEntity.getId());
        boolean $$9 = $$8.isGui3d();
        int $$10 = this.getRenderAmount($$6);
        float $$11 = 0.25F;
        float $$12 = Mth.sin(((float)specialItemEntity.getAge() + y) / 10.0F + specialItemEntity.bobOffs) * 0.1F + 0.1F;
        float $$13 = $$8.getTransforms().getTransform(ItemDisplayContext.GROUND).scale.y();
        poseStack.translate(0.0F, $$12 + 0.25F * $$13, 0.0F);
        float $$14 = specialItemEntity.getSpin(y);
        poseStack.mulPose(Axis.YP.rotation($$14));
        float $$15 = $$8.getTransforms().ground.scale.x();
        float $$16 = $$8.getTransforms().ground.scale.y();
        float $$17 = $$8.getTransforms().ground.scale.z();
        float $$22;
        float $$23;
        if (!$$9) {
            float $$18 = -0.0F * (float)($$10 - 1) * 0.5F * $$15;
            $$22 = -0.0F * (float)($$10 - 1) * 0.5F * $$16;
            $$23 = -0.09375F * (float)($$10 - 1) * 0.5F * $$17;
            poseStack.translate($$18, $$22, $$23);
        }

        for(int $$21 = 0; $$21 < $$10; ++$$21) {
            poseStack.pushPose();
            if ($$21 > 0) {
                if ($$9) {
                    $$22 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    $$23 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float $$24 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    poseStack.translate($$22, $$23, $$24);
                } else {
                    $$22 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    $$23 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    poseStack.translate($$22, $$23, 0.0F);
                }
            }

            this.itemRenderer.render($$6, ItemDisplayContext.GROUND, false, poseStack, multiBufferSource, i, OverlayTexture.NO_OVERLAY, $$8);
            poseStack.popPose();
            if (!$$9) {
                poseStack.translate(0.0F * $$15, 0.0F * $$16, 0.09375F * $$17);
            }
        }

        poseStack.popPose();
        super.render(specialItemEntity, x, y, poseStack, multiBufferSource, i);
    }

    @Override
    public ResourceLocation getTextureLocation(SpecialItemEntity specialItemEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
