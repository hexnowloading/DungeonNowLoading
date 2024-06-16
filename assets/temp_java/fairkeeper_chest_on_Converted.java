// Made with Blockbench 4.10.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class fairkeeper_chest_on_Converted<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "fairkeeper_chest_on_converted"), "main");
	private final ModelPart lid;
	private final ModelPart base;

	public fairkeeper_chest_on_Converted(ModelPart root) {
		this.lid = root.getChild("lid");
		this.base = root.getChild("base");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition lid = partdefinition.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 23).addBox(-7.5F, -8.25F, -14.0F, 15.0F, 8.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-7.5F, -8.25F, -14.0F, 15.0F, 8.0F, 15.0F, new CubeDeformation(0.25F))
		.texOffs(0, 0).addBox(-2.5F, -3.25F, -14.5F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.25F, 6.5F));

		PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(47, 10).addBox(-6.5F, -8.0F, -6.5F, 13.0F, 8.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(0, 46).addBox(-6.5F, -8.0F, -6.5F, 13.0F, 8.0F, 13.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		lid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}