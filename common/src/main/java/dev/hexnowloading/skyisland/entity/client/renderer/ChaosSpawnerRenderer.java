package dev.hexnowloading.skyisland.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.skyisland.entity.client.layer.ChaosSpawnerEmissiveLayer;
import dev.hexnowloading.skyisland.entity.client.model.ChaosSpawnerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ChaosSpawnerRenderer<T extends ChaosSpawnerEntity> extends MobRenderer<T, ChaosSpawnerModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Skyisland.MOD_ID, "textures/entity/chaos_spawner/chaos_spawner_sleeping.png");
    public ChaosSpawnerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ChaosSpawnerModel<>(renderManager.bakeLayer(ChaosSpawnerModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new ChaosSpawnerEmissiveLayer(this));
    }

    protected void scale(ChaosSpawnerEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(3.0F, 3.0F, 3.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(ChaosSpawnerEntity instance) { return TEXTURE; }
}
