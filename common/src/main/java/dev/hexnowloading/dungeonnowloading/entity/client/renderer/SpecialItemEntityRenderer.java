package dev.hexnowloading.dungeonnowloading.entity.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;

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
