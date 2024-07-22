package dev.hexnowloading.dungeonnowloading.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.hexnowloading.dungeonnowloading.particle.type.AxisParticleType;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class FairkeeperBoundaryParticle extends TextureSheetParticle {

    private SpriteSet spriteSet;
    private int axis;
    private float degree;

    protected FairkeeperBoundaryParticle(ClientLevel clientLevel, double x, double y, double z, int axis, float degree, SpriteSet spriteSet) {
        super(clientLevel, x, y, z);
        this.quadSize = 1.0F;
        this.gravity = 0.0F;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.spriteSet = spriteSet;
        this.lifetime = 50;
        this.axis = axis;
        this.degree = degree;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        int sprite = Mth.clamp(4 * this.age / this.lifetime, 0, 4);
        this.setSprite(spriteSet.get(0, 4));
        if (sprite > 0) {
            this.xd = 0;
            this.yd = 0;
            this.zd = 0;
        }
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.move(this.xd, this.yd, this.zd);
        }
    }

    @Override
    public float getQuadSize(float f) {
        return this.quadSize * Mth.clamp(((float)this.age + f) / (float)this.lifetime * 0.75f, 0.0f, 1.0f);
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
        this.alpha = 1.0F - Mth.clamp((((float)this.age + partialTick) / (float)this.lifetime), 0.0f, 1.0f);
        this.rCol = 1.0f;
        this.gCol = 1.0f;
        this.renderRotatedParticle(vertexConsumer, camera, partialTick, this.axis, this.degree);
        this.rCol = 0.1f;
        this.gCol = 0.1f;
        this.renderRotatedParticle(vertexConsumer, camera, partialTick, this.axis, this.degree + 180F);
    }

    private void renderRotatedParticle(VertexConsumer vertexConsumer, Camera camera, float partialTick, int axis, float degree) {
        Vec3 vec3 = camera.getPosition();
        float f = (float) (Mth.lerp((double) partialTick, this.xo, this.x) - vec3.x());
        float f1 = (float) (Mth.lerp((double) partialTick, this.yo, this.y) - vec3.y());
        float f2 = (float) (Mth.lerp((double) partialTick, this.zo, this.z) - vec3.z());
        Quaternionf quaternion =
                switch (axis) {
                    default -> Axis.XP.rotationDegrees(degree);
                    case 1 -> Axis.YP.rotationDegrees(degree);
                    case 2 -> Axis.ZP.rotationDegrees(degree);
                };
        Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
        vector3f1.rotate(quaternion);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f4 = this.getQuadSize(partialTick);

        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.rotate(quaternion);
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);
        }

        float f7 = this.getU0();
        float f8 = this.getU1();
        float f5 = this.getV0();
        float f6 = this.getV1();
        int j = this.getLightColor(partialTick);
        vertexConsumer.vertex((double) avector3f[0].x(), (double) avector3f[0].y(), (double) avector3f[0].z()).uv(f8, f6).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        vertexConsumer.vertex((double) avector3f[1].x(), (double) avector3f[1].y(), (double) avector3f[1].z()).uv(f8, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        vertexConsumer.vertex((double) avector3f[2].x(), (double) avector3f[2].y(), (double) avector3f[2].z()).uv(f7, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        vertexConsumer.vertex((double) avector3f[3].x(), (double) avector3f[3].y(), (double) avector3f[3].z()).uv(f7, f6).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
    }

    @Override
    public int getLightColor(float f) {
        return 240;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<AxisParticleType.AxisParticleData> {

        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        public Particle createParticle(AxisParticleType.AxisParticleData data, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            FairkeeperBoundaryParticle particle = new FairkeeperBoundaryParticle(clientLevel, x, y, z, data.getAxis() , data.getDegree(), this.sprites);
            particle.setSprite(sprites.get(0, 1));
            particle.setAlpha(1.0F);
            return particle;
        }
    }
}
