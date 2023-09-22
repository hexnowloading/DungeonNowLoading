package dev.hexnowloading.skyisland.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.entity.ChaosSpawnerEntity;
import dev.hexnowloading.skyisland.entity.client.layer.ChaosSpawnerEmissiveLayer;
import dev.hexnowloading.skyisland.entity.client.model.ChaosSpawnerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.WardenEmissiveLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class ChaosSpawnerRenderer<T extends ChaosSpawnerEntity> extends MobRenderer<T, ChaosSpawnerModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Skyisland.MOD_ID, "textures/entity/chaos_spawner/chaos_spawner.png");
    public ChaosSpawnerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ChaosSpawnerModel<>(renderManager.bakeLayer(ChaosSpawnerModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new ChaosSpawnerEmissiveLayer(this));
    }

    protected void scale(ChaosSpawnerEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(2.8F, 2.8F, 2.8F);
    }

    @Override
    public ResourceLocation getTextureLocation(ChaosSpawnerEntity instance) { return TEXTURE; }
}
