package dev.hexnowloading.skyisland.entity.client.model;// Made with Blockbench 4.8.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.skyisland.entity.client.animation.ChaosSpawnerAnimation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ChaosSpawnerModel<T extends ChaosSpawnerEntity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Skyisland.MOD_ID, "chaos_spawner"), "main");
	private final ModelPart root;
	private final ModelPart skull_1;
	private final ModelPart skull_2;
	private final ModelPart cage;

	public ChaosSpawnerModel(ModelPart root) {
		this.root = root;
		this.skull_1 = root.getChild("skull_1");
		this.skull_2 = root.getChild("skull_2");
		this.cage = root.getChild("cage");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition skull_1 = partdefinition.addOrReplaceChild("skull_1", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition cube_r1 = skull_1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 16).addBox(-2.0F, 0.9088F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 16).addBox(-3.0F, -3.0912F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.4088F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition skull_2 = partdefinition.addOrReplaceChild("skull_2", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition upper_jaw = skull_2.addOrReplaceChild("upper_jaw", CubeListBuilder.create(), PartPose.offset(0.25F, 1.5667F, -0.0833F));

		PartDefinition cube_r2 = upper_jaw.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 0).addBox(-4.25F, 0.75F, -3.0F, 9.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(14, 22).addBox(-5.25F, -6.15F, -5.0F, 10.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.4167F, 0.0833F, 0.0F, 1.5708F, 0.0F));

		PartDefinition lower_jaw = skull_2.addOrReplaceChild("lower_jaw", CubeListBuilder.create(), PartPose.offset(0.25F, 1.5667F, -0.0833F));

		PartDefinition cube_r3 = lower_jaw.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(30, 8).addBox(-4.25F, 1.65F, -4.0F, 9.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.4167F, 0.0833F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cage = partdefinition.addOrReplaceChild("cage", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cage_1 = cage.addOrReplaceChild("cage_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r4 = cage_1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cage_2 = cage.addOrReplaceChild("cage_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r5 = cage_2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cage_3 = cage.addOrReplaceChild("cage_3", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition cube_r6 = cage_3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, -1.5708F));

		PartDefinition cage_4 = cage.addOrReplaceChild("cage_4", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition cube_r7 = cage_4.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 0.0F));

		PartDefinition cage_5 = cage.addOrReplaceChild("cage_5", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition cage_6 = cage.addOrReplaceChild("cage_6", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition cube_r8 = cage_6.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 8.0F, -4.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cage_7 = cage.addOrReplaceChild("cage_7", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition cube_r9 = cage_7.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 4.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition cage_8 = cage.addOrReplaceChild("cage_8", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition cube_r10 = cage_8.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 4.0F, 3.1416F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.awakeningAnimationState, ChaosSpawnerAnimation.CHAOS_SPAWNER_AWAKENING, ageInTicks);
		this.animate(entity.sleepingAnimationState, ChaosSpawnerAnimation.CHAOS_SPAWNER_SLEEPING, ageInTicks);
		if (entity.getPhase() != 0) {
			this.animateHeadLookTarget(netHeadYaw, headPitch);
			this.animateIdlePose(ageInTicks);
		}
	}

	private void animateHeadLookTarget(float netHeadYaw, float headPitch) {
		this.skull_1.xRot = headPitch * ((float)Math.PI / 180F);
		this.skull_1.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.skull_2.xRot = headPitch * ((float)Math.PI / 180F);
		this.skull_2.yRot = netHeadYaw * ((float)Math.PI / 180F);
	}

	private void animateIdlePose(float ageInTicks) {
		float f = ageInTicks * 0.1F;
		float f1 = Mth.cos(f);
		float f2 = Mth.sin(f);
		this.skull_1.zRot += 0.06F * f1;
		this.skull_1.yRot += 0.06F * f2;
		this.skull_2.zRot += 0.06F * f1;
		this.skull_2.yRot += 0.06F * f2;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		skull_1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		skull_2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		cage.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}