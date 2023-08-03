package dev.hexnowloading.skyisland.world.entity.client.renderer;

import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.world.entity.WindstoneEntity;
import dev.hexnowloading.skyisland.world.entity.client.model.WindstoneModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WindstoneRenderer extends EntityRenderer<WindstoneEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Skyisland.MOD_ID, "textures/entity/windstone.png");
    private final WindstoneModel model;
    public WindstoneRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new WindstoneModel(context.bakeLayer(WindstoneModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(WindstoneEntity instance) {
        return TEXTURE;
    }
}
