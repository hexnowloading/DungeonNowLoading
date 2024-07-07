package dev.hexnowloading.dungeonnowloading.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class LargeFlameParticle extends TextureSheetParticle {

    private SpriteSet spriteSet;

    protected LargeFlameParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.quadSize *= 2.9F + level.random.nextFloat() * 0.5F;
        this.hasPhysics = true;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.bCol = 1.0F;
        this.gCol = 1.0F;
        this.spriteSet = spriteSet;
        this.friction = 0.95F;
        this.lifetime = 5 + level.random.nextInt(10);
    }

    @Override
    public void tick() {
        //this.rCol = (float) (this.lifetime - this.age) / this.lifetime;
        this.quadSize -= this.quadSize > 0 ? 0.05F : 0;
        this.gCol = 1.0F - 0.9F * ((float)this.age / this.lifetime);
        this.bCol = 1.0F - 0.9F * ((float)this.age / this.lifetime);
        //this.gCol = (float) this.age / this.lifetime;
        //this.bCol = (float) this.age / this.lifetime;
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        int sprite = this.age % 8;
        System.out.println(sprite);
        this.setSprite(spriteSet.get(sprite, 8));
        if (sprite > 0) {
            this.xd = 0;
            this.yd = 0;
            this.zd = 0;
        }
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.move(this.xd, this.yd, this.zd);
            this.xd *= (double) this.friction;
            this.yd *= (double) this.friction;
            this.zd *= (double) this.friction;
        }
    }

    @Override
    public ParticleRenderType getRenderType() { return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT; }

    public static class Factory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            LargeFlameParticle particle = new LargeFlameParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
            particle.setSprite(sprites.get(0, 1));
            return particle;
        }
    }
}
