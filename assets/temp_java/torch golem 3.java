// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class torch golem 3<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "torch_golem 3"), "main");
	private final ModelPart right_back_leg;
	private final ModelPart right_front_leg;
	private final ModelPart left_back_leg;
	private final ModelPart left_front_leg;
	private final ModelPart lower;
	private final ModelPart upper;
	private final ModelPart flame_thrower;
	private final ModelPart cannon;

	public torch golem 3(ModelPart root) {
		this.right_back_leg = root.getChild("right_back_leg");
		this.right_front_leg = root.getChild("right_front_leg");
		this.left_back_leg = root.getChild("left_back_leg");
		this.left_front_leg = root.getChild("left_front_leg");
		this.lower = root.getChild("lower");
		this.upper = root.getChild("upper");
		this.flame_thrower = root.getChild("flame_thrower");
		this.cannon = root.getChild("cannon");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition right_back_leg = partdefinition.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(0, 58).addBox(-1.0F, -8.0F, 9.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 24.0F, -9.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 58).addBox(-9.0F, 0.0F, -1.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 16.0F, -7.0F));

		PartDefinition left_back_leg = partdefinition.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(0, 58).addBox(-4.0F, -4.0F, 6.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 20.0F, -6.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 58).addBox(-1.0F, 0.0F, -1.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 16.0F, -7.0F));

		PartDefinition lower = partdefinition.addOrReplaceChild("lower", CubeListBuilder.create().texOffs(0, 32).addBox(-9.0F, -5.0F, -1.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(-9.0F, -10.0F, -1.0F, 16.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 16.0F, -7.0F));

		PartDefinition upper = partdefinition.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(0, 32).addBox(-9.0F, -5.0F, -1.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-9.0F, -16.0F, -1.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 74).addBox(-7.0F, -18.0F, 1.0F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 107).addBox(-9.0F, -20.0F, -1.0F, 16.0F, 5.0F, 16.0F, new CubeDeformation(0.25F)), PartPose.offset(1.0F, 8.0F, -7.0F));

		PartDefinition cube_r1 = upper.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 89).addBox(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -14.0F, 7.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r2 = upper.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 89).addBox(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -14.0F, 7.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition flame_thrower = partdefinition.addOrReplaceChild("flame_thrower", CubeListBuilder.create().texOffs(64, 9).addBox(5.0F, -29.0F, 7.0F, 4.0F, 22.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(80, 7).addBox(4.0F, -29.0F, 6.0F, 6.0F, 22.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 22.0F, -9.0F));

		PartDefinition cannon = partdefinition.addOrReplaceChild("cannon", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -2.0F, -7.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition cube_r3 = cannon.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r4 = cannon.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r5 = cannon.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		right_back_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_front_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_back_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_front_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		lower.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		upper.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		flame_thrower.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		cannon.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}