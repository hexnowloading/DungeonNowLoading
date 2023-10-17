package dev.hexnowloading.skyisland.entity.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.entity.projectile.ChaosSpawnerProjectileEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ChaosSpawnerProjectileModel<T extends ChaosSpawnerProjectileEntity> extends HierarchicalModel<T> {
// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Skyisland.MOD_ID, "chaos_spawner_projectile"), "main");
    private final ModelPart root;
    private final ModelPart head;

    public ChaosSpawnerProjectileModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        //PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.0F, -7.0F, 10.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
        //PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -8.0F, -7.0F, 10.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(4, 4).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //this.root().getAllParts().forEach(ModelPart::resetPose);
        //float yRot = Mth.rotLerp(limbSwingAmount, entity.yRotO, entity.getYRot());
        //float xRot = Mth.lerp(limbSwingAmount, entity.xRotO, entity.getXRot());
        //this.head.yRot = yRot * ((float)Math.PI / 180F);
        //this.head.xRot = xRot * ((float)Math.PI / 180F);
    }

    public void headAnim(float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() { return this.root; }
}
