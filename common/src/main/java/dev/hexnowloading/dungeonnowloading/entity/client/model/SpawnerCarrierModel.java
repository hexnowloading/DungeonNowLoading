package dev.hexnowloading.dungeonnowloading.entity.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.client.animation.SpawnerCarrierAnimation;
import dev.hexnowloading.dungeonnowloading.entity.monster.SpawnerCarrierEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class SpawnerCarrierModel<T extends SpawnerCarrierEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DungeonNowLoading.MOD_ID, "spawner_carrier"), "main");
    private final ModelPart leg;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;
    private final ModelPart body;
    private final ModelPart eye;
    private final ModelPart root;

    public SpawnerCarrierModel(ModelPart root) {
        this.root = root;
        this.leg = root.getChild("leg");
        this.leg2 = root.getChild("leg2");
        this.leg3 = root.getChild("leg3");
        this.leg4 = root.getChild("leg4");
        this.body = root.getChild("body");
        this.eye = root.getChild("eye");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition leg = partdefinition.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(81, 29).addBox(-3.5F, -3.0F, -3.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(12.5F, 9.0F, -12.5F));

        PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(81, 29).addBox(-3.5F, -3.0F, -3.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(12.5F, 9.0F, 12.5F));

        PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(81, 29).addBox(-3.5F, -3.0F, -3.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.5F, 9.0F, 12.5F));

        PartDefinition leg4 = partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(81, 29).addBox(-3.5F, -3.0F, -3.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.5F, 9.0F, -12.5F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 59).addBox(-8.0F, -8.1667F, -8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-10.0F, -3.1667F, -10.0F, 20.0F, 3.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-11.0F, -1.1667F, -11.0F, 22.0F, 14.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.1667F, 0.0F));

        PartDefinition eye = partdefinition.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(24, 105).addBox(-6.5F, -4.5F, -0.5F, 13.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -10.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(SpawnerCarrierAnimation.SPAWNER_CARRIER_WALK, limbSwing, limbSwingAmount, 4.0F, 4.5F);
        this.animate(entity.attackAnimationState, SpawnerCarrierAnimation.SPAWNER_CARRIER_ATTACK, ageInTicks, 2.0F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        eye.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
