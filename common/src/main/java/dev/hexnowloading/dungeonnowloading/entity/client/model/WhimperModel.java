package dev.hexnowloading.dungeonnowloading.entity.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.passive.WhimperEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Arrays;
import java.util.List;

public class WhimperModel<T extends WhimperEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DungeonNowLoading.MOD_ID, "whimper"), "main");
    private final ModelPart head;
    private final ModelPart left_hand;
    private final ModelPart right_hand;
    private final ModelPart left_ear;
    private final ModelPart right_ear;
    private final ModelPart tail;
    private final ModelPart root;

    public WhimperModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.left_hand = root.getChild("left_hand");
        this.right_hand = root.getChild("right_hand");
        this.left_ear = head.getChild("left_ear");
        this.right_ear = head.getChild("right_ear");
        this.tail = head.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, -3.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -3.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

        head.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 26).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 4.0F, 0.5236F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(0, 16).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 22.5F, -6.5F));

        partdefinition.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(0, 16).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, 22.5F, -6.5F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateHeadLookTarget(netHeadYaw, headPitch);
        this.animateIdlePose(ageInTicks);
    }

    private void animateHeadLookTarget(float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
    }

    private void animateIdlePose(float ageInTicks) {

        float AGEINTICKS_TO_SECONDS = ageInTicks / 20;

        float UPDOWN_OSCILLATION_SECOND = 2.0F;
        float UPDOWN_DISTANCE_PIXEL = 1.0f;
        float updownRate = UPDOWN_DISTANCE_PIXEL * Mth.sin(AGEINTICKS_TO_SECONDS * Mth.TWO_PI / UPDOWN_OSCILLATION_SECOND);
        ModelPart[] UPDOWN_ANIMATION_PARTS = {this.head, this.right_hand, this.left_hand};
        Arrays.stream(UPDOWN_ANIMATION_PARTS).forEach(modelPart -> modelPart.y += updownRate);

        float FLAPPING_OSCILLATION_SECOND = 2.0F;
        float FLAPPING_ROTATION_DEGREE = 25;
        float flappingRate = Mth.DEG_TO_RAD * FLAPPING_ROTATION_DEGREE * Mth.cos(AGEINTICKS_TO_SECONDS * Mth.TWO_PI / FLAPPING_OSCILLATION_SECOND);
        this.right_ear.zRot += flappingRate;
        this.left_ear.zRot -= flappingRate;
        this.tail.xRot += flappingRate;

        float HAND_OSCILLATION_SECOND = 3.0F;
        float HAND_ROTATION_DEGREE = 10;
        float handRotationRate = Mth.DEG_TO_RAD * HAND_ROTATION_DEGREE * Mth.cos(AGEINTICKS_TO_SECONDS * Mth.TWO_PI / HAND_OSCILLATION_SECOND);
        this.right_hand.xRot += handRotationRate;
        this.left_hand.xRot += handRotationRate;
        this.right_hand.yRot += handRotationRate;
        this.left_hand.yRot -= handRotationRate;
    }

    public ModelPart root() { return root; }
}
