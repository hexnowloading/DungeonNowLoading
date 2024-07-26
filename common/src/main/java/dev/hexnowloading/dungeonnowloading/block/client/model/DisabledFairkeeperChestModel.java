package dev.hexnowloading.dungeonnowloading.block.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class DisabledFairkeeperChestModel extends Model {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DungeonNowLoading.MOD_ID, "disabled_fairkeeper_chest"), "main");
    public final ModelPart lid;
    public final ModelPart base;

    public DisabledFairkeeperChestModel(ModelPart root) {
        super(RenderType::armorCutoutNoCull);

        this.lid = root.getChild("lid");
        this.base = root.getChild("base");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("lid",
                CubeListBuilder.create()
                        .texOffs(0, 23).addBox(-7.5F, -8.25F, -14.0F, 15.0F, 8.0F, 15.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-7.5F, -8.25F, -14.0F, 15.0F, 8.0F, 15.0F, new CubeDeformation(0.25F))
                        .texOffs(0, 0).addBox(-2.5F, -3.25F, -14.5F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.25F, 6.5F));

        partDefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(47, 10).addBox(-6.5F, -8.0F, -6.5F, 13.0F, 8.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(0, 46).addBox(-6.5F, -8.0F, -6.5F, 13.0F, 8.0F, 13.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        lid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setupAnim(float openProgress) {
        lid.xRot = -(float)(openProgress * Math.PI * 0.5F);
    }

}
