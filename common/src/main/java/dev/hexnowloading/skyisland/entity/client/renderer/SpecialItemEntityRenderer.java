package dev.hexnowloading.skyisland.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.skyisland.entity.misc.SpecialItemEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SpecialItemEntityRenderer extends ItemEntityRenderer {

    private final ItemRenderer itemRenderer;

    public SpecialItemEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    /*@Override
    public boolean shouldRender(ItemEntity itemEntity, Frustum $$1, double $$2, double $$3, double $$4) {
        SpecialItemEntity specialItemEntity = (SpecialItemEntity) itemEntity;
        //System.out.println(this.entityRenderDispatcher.camera.getEntity().getUUID());
        //System.out.println(specialItemEntity.getPickerUUID());
        //System.out.println(specialItemEntity.getX());
        if (!this.entityRenderDispatcher.camera.getEntity().getUUID().toString().equals(specialItemEntity.getPickerUUID())) {
            return false;
        }
        return super.shouldRender(itemEntity, $$1, $$2, $$3, $$4);
    }*/
}
