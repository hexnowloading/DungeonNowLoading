// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class Scuttle<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "scuttle"), "main");
	private final ModelPart torchgolem;
	private final ModelPart body;
	private final ModelPart flame_thrower_n_upper;
	private final ModelPart flame_thrower;
	private final ModelPart cannon;
	private final ModelPart upper;
	private final ModelPart lower;
	private final ModelPart lowerjaw;
	private final ModelPart Legs;
	private final ModelPart right_back_leg;
	private final ModelPart right_front_leg;
	private final ModelPart left_back_leg;
	private final ModelPart left_front_leg;

	public Scuttle(ModelPart root) {
		this.torchgolem = root.getChild("torchgolem");
		this.body = root.getChild("body");
		this.flame_thrower_n_upper = root.getChild("flame_thrower_n_upper");
		this.flame_thrower = root.getChild("flame_thrower");
		this.cannon = root.getChild("cannon");
		this.upper = root.getChild("upper");
		this.lower = root.getChild("lower");
		this.lowerjaw = root.getChild("lowerjaw");
		this.Legs = root.getChild("Legs");
		this.right_back_leg = root.getChild("right_back_leg");
		this.right_front_leg = root.getChild("right_front_leg");
		this.left_back_leg = root.getChild("left_back_leg");
		this.left_front_leg = root.getChild("left_front_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torchgolem = partdefinition.addOrReplaceChild("torchgolem", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = torchgolem.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition flame_thrower_n_upper = body.addOrReplaceChild("flame_thrower_n_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition flame_thrower = flame_thrower_n_upper.addOrReplaceChild("flame_thrower", CubeListBuilder.create().texOffs(64, 9).addBox(-2.0F, -10.0F, -2.0F, 4.0F, 22.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(80, 7).addBox(-3.0F, -10.0F, -3.0F, 6.0F, 22.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -21.0F, 0.0F));

		PartDefinition cannon = flame_thrower.addOrReplaceChild("cannon", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -2.0F, -7.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = cannon.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r2 = cannon.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r3 = cannon.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition upper = flame_thrower_n_upper.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -5.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 74).addBox(-6.0F, -18.0F, -6.0F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 107).addBox(-8.0F, -20.0F, -8.0F, 16.0F, 5.0F, 16.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -16.0F, 0.0F));

		PartDefinition lower = body.addOrReplaceChild("lower", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition lowerjaw = lower.addOrReplaceChild("lowerjaw", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -13.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(-0.001F))
		.texOffs(0, 32).addBox(-8.0F, -18.0F, -8.0F, 16.0F, 10.0F, 16.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition Legs = torchgolem.addOrReplaceChild("Legs", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition right_back_leg = Legs.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(0, 58).addBox(-6.0F, 0.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 6.0F));

		PartDefinition right_front_leg = Legs.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 58).addBox(-6.0F, 0.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, -6.0F));

		PartDefinition left_back_leg = Legs.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(0, 58).addBox(-2.0F, 0.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 6.0F));

		PartDefinition left_front_leg = Legs.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 58).addBox(-2.0F, 0.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, -6.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		torchgolem.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}