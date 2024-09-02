package dev.hexnowloading.dungeonnowloading.entity.client.renderer;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.client.model.StonePillarProjectileModel;
import dev.hexnowloading.dungeonnowloading.entity.projectile.StonePillarProjectileEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class StonePillarProjectileRenderer<T extends StonePillarProjectileEntity> extends EntityRenderer<StonePillarProjectileEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(DungeonNowLoading.MOD_ID, "textures/entity/stone_pillar/stone_pillar.png");
    private StonePillarProjectileModel model;

    public StonePillarProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new StonePillarProjectileModel(context.bakeLayer(StonePillarProjectileModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(StonePillarProjectileEntity entity) {
        return TEXTURE;
    }
}
