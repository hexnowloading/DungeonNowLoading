package dev.hexnowloading.dungeonnowloading.entity.client.renderer;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.client.layer.ScuttleLayer;
import dev.hexnowloading.dungeonnowloading.entity.client.model.ScuttleModel;
import dev.hexnowloading.dungeonnowloading.entity.monster.ScuttleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ScuttleRenderer<T extends ScuttleEntity> extends MobRenderer<T, ScuttleModel<T>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/entity/scuttle/scuttle.png");

    public ScuttleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ScuttleModel<>(renderManager.bakeLayer(ScuttleModel.LAYER_LOCATION)), 1.0F);
        this.addLayer(new ScuttleLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ScuttleEntity scuttleEntity) { return TEXTURE; }
}
