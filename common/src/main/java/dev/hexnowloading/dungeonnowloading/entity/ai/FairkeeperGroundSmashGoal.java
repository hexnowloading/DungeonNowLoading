package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.ai.control.FairkeeperFlyingMoveControl;
import dev.hexnowloading.dungeonnowloading.entity.boss.FairkeeperEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class FairkeeperGroundSmashGoal extends Goal {

    private final FairkeeperEntity fairkeeperEntity;
    private final FairkeeperEntity.FairkeeperState state;
    private final int strafeToSmashInterval;
    private final double strafeMaxSpeed;
    private final double strafeMinSpeed;
    private final double strafeStopAccuracy;
    private final float damagePercentage;
    private final double groundSmashRange;
    private final double horizontalKnockbackStrength;
    private final double verticalKnockbackStrength;
    private final boolean shieldPenetration;
    private final float shieldDamageReduction;
    private final int postSmashInterval;

    private int phase;
    private int tickCount;
    private BlockPos targetBlockPos;
    private double altitude;

    public FairkeeperGroundSmashGoal(FairkeeperEntity fairkeeperEntity, FairkeeperEntity.FairkeeperState state, int strafeToSmashInterval, double strafeMaxSpeed, double strafeMinSpeed, double strafeStopAccuracy, float damagePercentage, double groundSmashRange, double horizontalKnockbackStrength, double verticalKnockbackStrength, boolean shieldPenetration, float shieldDamageReduction, int postSmashInterval) {
        this.fairkeeperEntity = fairkeeperEntity;
        this.state = state;
        this.strafeToSmashInterval = strafeToSmashInterval / 2;
        this.strafeMaxSpeed = strafeMaxSpeed;
        this.strafeMinSpeed = strafeMinSpeed;
        this.strafeStopAccuracy = strafeStopAccuracy;
        this.damagePercentage = damagePercentage;
        this.groundSmashRange = groundSmashRange;
        this.horizontalKnockbackStrength = horizontalKnockbackStrength;
        this.verticalKnockbackStrength = verticalKnockbackStrength;
        this.shieldPenetration = shieldPenetration;
        this.shieldDamageReduction = shieldDamageReduction;
        this.postSmashInterval = postSmashInterval / 2;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.fairkeeperEntity.isState(this.state) && this.fairkeeperEntity.getTarget() != null;
    }

    @Override
    public void start() {
        this.tickCount = 0;
        this.phase = 0;
        if (this.fairkeeperEntity.getTarget() != null) {
            this.targetBlockPos = this.fairkeeperEntity.getTarget().blockPosition();
        }

    }

    @Override
    public void tick() {
        if (this.phase == 0) {
            this.altitude = this.fairkeeperEntity.getY();
            ((FairkeeperFlyingMoveControl) this.fairkeeperEntity.getMoveControl()).setWantedPositionWithSpeed(targetBlockPos.getX(), altitude, targetBlockPos.getZ(), this.strafeMaxSpeed, this.strafeMinSpeed, this.strafeStopAccuracy);
            this.phase++;
        } else if (this.phase == 1) {
            if (!this.fairkeeperEntity.getMoveControl().hasWanted()) {
                this.fairkeeperEntity.setDeltaMovement(Vec3.ZERO);
                this.phase++;
                this.tickCount = this.strafeToSmashInterval;
            }
        } else if (this.phase == 2) {
            if (this.tickCount > 0) {
                this.tickCount--;
                return;
            }
            this.phase++;
        } else if (this.phase == 3) {
            ((FairkeeperFlyingMoveControl) this.fairkeeperEntity.getMoveControl()).setWantedPositionWithSpeed(targetBlockPos.getX(), this.fairkeeperEntity.getSpawnPoint().getY() - 3, targetBlockPos.getZ(), this.strafeMaxSpeed * 2.0, this.strafeMaxSpeed * 2.0, this.strafeStopAccuracy);
            //this.fairkeeperEntity.setNoGravity(false);
            this.phase++;
        } else if (this.phase == 4) {
            if (this.fairkeeperEntity.getDeltaMovement().length() < 0.001f) {
                this.fairkeeperEntity.setDeltaMovement(Vec3.ZERO);
                ((FairkeeperFlyingMoveControl) this.fairkeeperEntity.getMoveControl()).setWaitOperation();
                ((ServerLevel) this.fairkeeperEntity.level()).sendParticles(ParticleTypes.POOF, this.fairkeeperEntity.getX(), this.fairkeeperEntity.getY(), this.fairkeeperEntity.getZ(), 50, 5.0D, 0.0D, 5.0D, 0.0D);
                AABB aabb = this.fairkeeperEntity.getBoundingBox().inflate(this.groundSmashRange);
                List<LivingEntity> targets = this.fairkeeperEntity.level().getEntitiesOfClass(LivingEntity.class, aabb).stream().filter(livingEntity -> !(livingEntity instanceof FairkeeperEntity)).toList();
                for (LivingEntity mob : targets) {
                    this.pushNearbyMobs(mob);
                }
                this.phase++;
                this.tickCount = this.postSmashInterval;
            }
        } else if (this.phase == 5) {
            //this.fairkeeperEntity.setDeltaMovement(Vec3.ZERO);
            //((FairkeeperFlyingMoveControl) this.fairkeeperEntity.getMoveControl()).setWaitOperation();
            if (this.tickCount > 0) {
                tickCount--;
                return;
            }
            this.phase++;
        } else if (this.phase == 6) {
            //this.fairkeeperEntity.setNoGravity(true);
            ((FairkeeperFlyingMoveControl) this.fairkeeperEntity.getMoveControl()).setWantedPositionWithSpeed(targetBlockPos.getX(), this.altitude, targetBlockPos.getZ(), this.strafeMaxSpeed, this.strafeMinSpeed, this.strafeStopAccuracy);
            this.phase++;
        } else if (this.phase == 7) {
            if (!this.fairkeeperEntity.getMoveControl().hasWanted()) {
                this.fairkeeperEntity.stopAttacking(60);
            }
        }
    }

    private void pushNearbyMobs(LivingEntity mob) {
        float damageAmount = (float) (this.fairkeeperEntity.getAttackDamage() * this.damagePercentage);
        if (mob instanceof Player player && this.shieldPenetration && player.isBlocking()) {
            player.disableShield(true);
            damageAmount *= 1.0F - this.shieldDamageReduction;
        }
        double x = mob.getX() - this.fairkeeperEntity.getX();
        double z = mob.getZ() - this.fairkeeperEntity.getZ();
        double a = x * x + z * z;

        mob.push(x / a * this.horizontalKnockbackStrength, this.verticalKnockbackStrength, z / a * horizontalKnockbackStrength);
        mob.hurt(this.fairkeeperEntity.damageSources().mobAttack(this.fairkeeperEntity), (int) damageAmount);
    }
}
