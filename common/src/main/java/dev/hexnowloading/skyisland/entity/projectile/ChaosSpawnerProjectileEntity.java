package dev.hexnowloading.skyisland.entity.projectile;

import dev.hexnowloading.skyisland.registry.SkyislandEntityTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ChaosSpawnerProjectileEntity extends AbstractHurtingProjectile {

    public ChaosSpawnerProjectileEntity(EntityType<? extends AbstractHurtingProjectile> $$0, Level $$1) {
        super($$0, $$1);
    }

    public ChaosSpawnerProjectileEntity(Level level, LivingEntity livingEntity, double d0, double d1, double d2) {
        super(SkyislandEntityTypes.CHAOS_SPAWNER_PROJECTILE.get(), livingEntity, d0, d1, d2, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level().isClientSide) {
            boolean entityHurted = entityHitResult.getEntity().hurt(this.damageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 8.0F);
            if (entityHurted) {
                if (entityHitResult.getEntity().isAlive()) {
                    this.doEnchantDamageEffects((LivingEntity) this.getOwner(), entityHitResult.getEntity());
                }
            }
        }
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource $$0, float $$1) {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }
}
