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
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DungeonNowLoading.MOD_ID, "chaos_spawner"), "main");
	private final ModelPart shockwave;
	private final ModelPart body;
	private final ModelPart root;

	public ChaosSpawnerModel(ModelPart root) {
		this.root = root;
		this.shockwave = root.getChild("shockwave");
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition shockwave = partdefinition.addOrReplaceChild("shockwave", CubeListBuilder.create(), PartPose.offset(0.0F, 31.0F, 0.0F));

		PartDefinition wave4 = shockwave.addOrReplaceChild("wave4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -28.0F));

		PartDefinition cube_r1 = wave4.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(-8, 62).addBox(-11.0F, 0.0F, 20.0F, 22.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 24.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition wave3 = shockwave.addOrReplaceChild("wave3", CubeListBuilder.create(), PartPose.offset(28.0F, 0.0F, 0.0F));

		PartDefinition cube_r2 = wave3.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(-8, 62).addBox(-11.0F, 0.0F, 28.0F, 22.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-32.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition wave2 = shockwave.addOrReplaceChild("wave2", CubeListBuilder.create(), PartPose.offset(-28.0F, 0.0F, 0.0F));

		PartDefinition cube_r3 = wave2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(-8, 62).addBox(-15.0F, 0.0F, 24.0F, 22.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, 0.0F, 4.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition wave1 = shockwave.addOrReplaceChild("wave1", CubeListBuilder.create().texOffs(-8, 62).addBox(-11.0F, 0.0F, -4.0F, 22.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 28.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition chaos_hexahedron = body.addOrReplaceChild("chaos_hexahedron", CubeListBuilder.create().texOffs(32, 99).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(48, 40).addBox(-7.6659F, -4.103F, -15.8096F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-10.6659F, -17.103F, -18.8096F, 22.0F, 14.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(14, 99).addBox(-1.6659F, -15.103F, -19.8096F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.3341F, 10.103F, 8.8096F));

		PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 99).addBox(17.5F, -0.5F, -0.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 81).addBox(-17.5F, -2.5F, -2.5F, 35.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1659F, -11.603F, -10.3096F, 0.0F, -0.2182F, -0.1309F));

		PartDefinition cube_r5 = head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 99).addBox(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.241F, -7.2061F, -12.3612F, 1.5708F, 0.2182F, 3.0107F));

		PartDefinition lower_jaw = body.addOrReplaceChild("lower_jaw", CubeListBuilder.create().texOffs(0, 36).addBox(-8.0F, 0.0F, -16.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 9.0F));

		PartDefinition chain = body.addOrReplaceChild("chain", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

		PartDefinition cube_r6 = chain.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 109).addBox(-5.0F, -1.5F, 0.0F, 10.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.3827F, -17.3385F, -4.8502F, 0.0F, 0.2182F, 3.0107F));

		PartDefinition cube_r7 = chain.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 109).addBox(19.5F, 0.5F, 2.0F, 10.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -22.5F, -1.5F, 0.0F, -0.2182F, -0.1309F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.sleepingAnimationState, ChaosSpawnerAnimation.CHAOS_SPAWNER_SLEEPING, ageInTicks, 1.0F);
		this.animate(entity.awakeningAnimationState, ChaosSpawnerAnimation.CHAOS_SPAWNER_WAKE_UP, ageInTicks, 1.0F);
		this.animate(entity.smashAttackAnimationState, ChaosSpawnerAnimation.CHAOS_SPAWNER_SMASH_ATTACK, ageInTicks, 1.0F);
		this.animate(entity.rangeAttackAnimationState, ChaosSpawnerAnimation.CHAOS_SPAWNER_RANGE_ATTACK, ageInTicks, 1.0F);
		this.animate(entity.rangeBurstAttackAnimationState, ChaosSpawnerAnimation.CHAOS_SPAWNER_RANGE_BURST_ATTACK, ageInTicks, 1.0F);
		this.animate(entity.summonAnimationState, ChaosSpawnerAnimation.CHAOS_SPAWNER_SUMMON, ageInTicks, 1.0F);
		this.animate(entity.deathAnimationState, ChaosSpawnerAnimation.CHAOS_SPAWNER_DEATH, ageInTicks, 1.0F);
		this.animateHeadLookTarget(netHeadYaw, headPitch);
		if (entity.getState() != ChaosSpawnerEntity.State.SLEEPING) {
			this.animateIdlePose(ageInTicks);
		}
	}

	private void animateIdlePose(float ageInTicks) {

		float AGEINTICKS_TO_SECONDS = ageInTicks / 20;

		float UPDOWN_OSCILLATION_SECOND = 2.0F;
		float UPDOWN_DISTANCE_PIXEL = 1.0f;
		float updownRate = UPDOWN_DISTANCE_PIXEL * Mth.sin(AGEINTICKS_TO_SECONDS * Mth.TWO_PI / UPDOWN_OSCILLATION_SECOND);

		this.body.y += updownRate;
	}

	private void animateHeadLookTarget(float netHeadYaw, float headPitch) {
		this.body.xRot += headPitch * Mth.DEG_TO_RAD;
		this.body.yRot += netHeadYaw * Mth.DEG_TO_RAD;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		shockwave.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}