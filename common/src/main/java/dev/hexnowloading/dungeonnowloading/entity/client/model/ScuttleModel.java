package dev.hexnowloading.dungeonnowloading.entity.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.client.animation.ScuttleAnimation;
import dev.hexnowloading.dungeonnowloading.entity.monster.ScuttleEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ScuttleModel<T extends ScuttleEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DungeonNowLoading.MOD_ID, "scuttle"), "main");
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
    private final ModelPart root;

    public ScuttleModel(ModelPart root) {
        this.root = root;
        this.torchgolem = root.getChild("torchgolem");
        this.body = torchgolem.getChild("body");
        this.flame_thrower_n_upper = body.getChild("flame_thrower_n_upper");
        this.flame_thrower = flame_thrower_n_upper.getChild("flame_thrower");
        this.cannon = flame_thrower.getChild("cannon");
        this.upper = flame_thrower_n_upper.getChild("upper");
        this.lower = body.getChild("lower");
        this.lowerjaw = lower.getChild("lowerjaw");
        this.Legs = torchgolem.getChild("Legs");
        this.right_back_leg = Legs.getChild("right_back_leg");
        this.right_front_leg = Legs.getChild("right_front_leg");
        this.left_back_leg = Legs.getChild("left_back_leg");
        this.left_front_leg = Legs.getChild("left_front_leg");
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
    public void setupAnim(ScuttleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (!entity.isStationary()) {
            this.animateWalk(ScuttleAnimation.SCUTTLE_WALKING_CLOSED, limbSwing, limbSwingAmount, 4.0F, 4.5F);
        }
        if (entity.isState(ScuttleEntity.ScuttleState.SLUMBERING) || (entity.isState(ScuttleEntity.ScuttleState.AWAKENING)) && !entity.wakingUpAnimationState.isStarted() && !entity.idleClosedAnimationState.isStarted()) {
            left_front_leg.z += 2.0F;
            left_front_leg.x -= 1.0F;
            right_front_leg.z += 2.0F;
            right_front_leg.x += 1.0F;
            left_back_leg.z -= 2.0F;
            left_back_leg.x -= 1.0F;
            right_back_leg.z -= 2.0F;
            right_back_leg.x += 1.0F;
        }
        this.animateFlameThrowerRotation(entity, ageInTicks, 12, 0.3F, 0.4F, 0.3F);
        this.animate(entity.wakingUpAnimationState, ScuttleAnimation.SCUTTLE_WAKING_UP, ageInTicks);
        this.animate(entity.mouthOpenAnimationState, ScuttleAnimation.SCUTTLE_MOUTH_OPEN, ageInTicks);
        this.animate(entity.mouthCloseAnimationState, ScuttleAnimation.SCUTTLE_MOUTH_CLOSE, ageInTicks);
        this.animate(entity.idleClosedAnimationState, ScuttleAnimation.SCUTTLE_IDLE_CLOSED, ageInTicks);
        this.animate(entity.idleOpenedAnimationState, ScuttleAnimation.SCUTTLE_IDLE_OPENED, ageInTicks);
        this.animate(entity.blockFormAnimationState, ScuttleAnimation.SCUTTLE_BLOCK_FORM, ageInTicks);
    }

    private void animateFlameThrowerRotation(ScuttleEntity entity, float ageInTicks, int numberOfRotation, float timeEaseIn, float timeConstant, float timeEaseOut) {
        if (entity.isState(ScuttleEntity.ScuttleState.OPENING) && !entity.isRotateStarted()) {
            entity.setOldAgeInTicks(ageInTicks);
            entity.setRotateStarted(true);
        }
        if (entity.isState(ScuttleEntity.ScuttleState.OPENING) || entity.isState(ScuttleEntity.ScuttleState.OPENED)) {

            float timeInSeconds = (ageInTicks - entity.getOldAgeInTicks()) / 20.0F;
            float ROTATION_RAD = Mth.DEG_TO_RAD * numberOfRotation * 180.0F;
            float FULL_ROTATION_SECOND = 10.0F;
            float time = timeInSeconds / FULL_ROTATION_SECOND;
            easeInPhase(ROTATION_RAD, time, timeEaseIn);
            constantPhase(ROTATION_RAD, time, timeEaseIn, timeConstant);
            easeOutPhase(ROTATION_RAD, time, timeEaseIn, timeConstant, timeEaseOut);
            //flame_thrower.yRot += 3 * ROTATION_RAD * time * time - 2 * ROTATION_RAD * time * time * time;
            //flame_thrower.yRot += ROTATION_RAD * time * time;
            //flame_thrower.yRot += (float) (ROTATION_RAD * -Math.exp(-time));
            //flame_thrower.yRot += Mth.DEG_TO_RAD * ROTATION_DEGREE * Mth.sin(AGEINTICKS_TO_SECONDS * Mth.TWO_PI / FULL_ROTATION_SECOND);
        }
        if (!entity.isAttackingState() && entity.isRotateStarted()) {
            entity.setRotateStarted(false);
        }
    }

    private void easeInPhase(float rotationRad, float time, float timeIn) {
        if (time < timeIn) {
            flame_thrower.yRot = easeInRotation(rotationRad, time, timeIn);
        }
    }

    private void constantPhase(float rotationRad, float time, float timeIn, float timeConstant) {
        if ((time == timeIn || time > timeIn) && time < timeIn + timeConstant) {
            flame_thrower.yRot = constantRotation(rotationRad, time, timeIn, timeConstant);
        }
    }

    private void easeOutPhase(float rotationRad, float time, float timeIn, float timeConstant, float timeOut) {
        if ((time == timeIn + timeConstant || time > timeIn + timeConstant) && (time < 1 || time == 1)) {
            flame_thrower.yRot = easeOutRotation(rotationRad, time, timeOut);
        }
    }

    private float easeInRotation(float rotationRad, float time, float timeIn) {
        float a = time / timeIn;
        return rotationRad * (float) Math.pow(a, 2);
    }

    private float constantRotation(float rotationRad, float time, float timeIn, float timeConstant) {
        float a = (2 * time - timeIn) / timeConstant;
        return rotationRad * a;
    }

    private float easeOutRotation(float rotationRad, float time, float timeOut) {
        float a = (1 - time) / timeOut;
        return rotationRad - rotationRad * (float) Math.pow(a, 2);
    }

    private float easeInSteepness(int steepness, float time, float rotationRad) {
        /*float a = 1.0F;
        for (int i = 0; i < steepness; i++) {
            a *= time;
        }*/
        return (float) Math.pow(2, steepness - 1) * rotationRad * (float) Math.pow(time, steepness);
    }

    private float easeOutSteepness(int steepness, float time, float rotationRad) {
        /*float a = 1.0F;
        for (int i = 0; i < steepness; i++) {
            a *= (-2 * time + 2);
        }*/
        float a = (-2.0F * time + 2.0F);
        return rotationRad * (1.0F - (float) Math.pow(a, steepness) / 2.0F);
    }

    @Override
    public ModelPart root() { return root; }
}