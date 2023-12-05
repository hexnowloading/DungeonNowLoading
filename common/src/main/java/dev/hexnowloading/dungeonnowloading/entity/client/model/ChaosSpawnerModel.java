package dev.hexnowloading.dungeonnowloading.entity.client.model;// Made with Blockbench 4.8.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.dungeonnowloading.entity.client.animation.ChaosSpawnerAnimation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ChaosSpawnerModel<T extends ChaosSpawnerEntity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DungeonNowLoading.MOD_ID, "chaos_spawner"), "main");
	private final ModelPart root;
	private final ModelPart skull_1;
	private float yRotOld;

	public ChaosSpawnerModel(ModelPart root) {
		this.root = root;
		this.skull_1 = root.getChild("skull_1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition skull_1 = partdefinition.addOrReplaceChild("skull_1", CubeListBuilder.create(), PartPose.offset(0.0F, 21.5F, 0.0F));

		PartDefinition cube_r1 = skull_1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 16).addBox(-2.0F, 0.9088F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 16).addBox(-3.0F, -3.0912F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.4088F, 0.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.awakeningAnimationState, ChaosSpawnerAnimation.CHAOS_SPAWNER_AWAKENING, ageInTicks);
		this.animate(entity.sleepingAnimationState, ChaosSpawnerAnimation.CHAOS_SPAWNER_SLEEPING, ageInTicks);
		this.animate(entity.smashAnimationState, ChaosSpawnerAnimation.CHAOS_SPAWNER_SMASH, ageInTicks);
		if (entity.getPhase() == 0) {
			if (entity.isAwakening() && entity.getAwakeningTick() < 80) {
				this.animateIdlePose(ageInTicks);
			}
		} else {
			this.animateIdlePose(ageInTicks);
			this.animateHeadLookTarget(entity, netHeadYaw, headPitch);
		}
	}

	private void animateHeadLookTarget(T entity, float netHeadYaw, float headPitch) {
		/*if (entity.isAttacking(ChaosSpawnerEntity.State.PUSH)) {
			if (entity.getAttackTick() > 80) {
				this.skull_1.yRot = headPitch * ((float)Math.PI / 180F);
			} else if (entity.getAttackTick() == 80) {
				this.yRotOld = this.skull_1.yRot;
			} else {
				this.skull_1.yRot = this.yRotOld;
			}
		} else {
			this.skull_1.xRot = headPitch * ((float)Math.PI / 180F);
			this.skull_1.yRot = netHeadYaw * ((float)Math.PI / 180F);
		}*/
		this.skull_1.xRot = headPitch * ((float)Math.PI / 180F);
		this.skull_1.yRot = netHeadYaw * ((float)Math.PI / 180F);
	}

	private void animateIdlePose(float ageInTicks) {
		float f = ageInTicks * 0.1F;
		float f1 = Mth.cos(f);
		float f2 = Mth.sin(f);
		//float f3 = Mth.cos(f * 0.5F);
		this.skull_1.zRot += 0.06F * f1;
		this.skull_1.yRot += 0.06F * f2;
		//this.skull_1.y += 0.48F * f3;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		skull_1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}