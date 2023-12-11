package dev.hexnowloading.dungeonnowloading.entity.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class HollowModel<T extends HollowEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DungeonNowLoading.MOD_ID, "hollow"), "main");
    private final ModelPart right_hand;
    private final ModelPart left_hand;
    private final ModelPart head;
    private final ModelPart root;

    public HollowModel(ModelPart root) {
        this.root = root;
        this.right_hand = root.getChild("right_hand");
        this.left_hand = root.getChild("left_hand");
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition right_hand = partdefinition.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(40, 10).addBox(-10.0F, -10.0F, -5.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 24.0F, -4.0F));

        PartDefinition left_hand = partdefinition.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(40, 0).addBox(-1.0F, -12.0F, -6.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 24.0F, -4.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(4.0F, 24.0F, -4.0F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.5F, -5.0F, 10.0F, 13.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -8.5F, 4.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(HollowEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        animateIdlePose(entity, ageInTicks);
    }

    private void animateIdlePose(HollowEntity entity, float ageInTicks) {
        float f;
        if (entity.IsCharging()) {
            f = ageInTicks * 0.5F;
        } else {
            f = ageInTicks * 0.1F;
        }
        float f1 = Mth.sin(f);
        float f2 = Mth.cos(f);
        float f3 = Mth.cos(f + Mth.PI * 0.5F);
        this.head.y += 2.0F * f1;
        this.left_hand.y += 2.0F * f2;
        this.right_hand.y += 2.0F * f3;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        right_hand.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_hand.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
