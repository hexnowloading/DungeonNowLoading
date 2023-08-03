package dev.hexnowloading.skyisland.world.entity.client.model;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class WindstoneModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "protected_windstone"), "main");
	private final ModelPart bb_main;

	public WindstoneModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(20, 30).addBox(6.5F, 8.0F, -5.0F, 5.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 76).addBox(-12.5F, -8.0F, -5.0F, 5.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 35).addBox(8.5F, -5.0F, -5.0F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(7.5F, -12.0F, -5.0F, 5.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 60).addBox(-12.5F, 2.0F, -5.0F, 5.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 47).addBox(-12.5F, 8.0F, -5.0F, 17.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(30, 10).addBox(8.5F, -2.0F, -5.0F, 3.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.5F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -18.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}