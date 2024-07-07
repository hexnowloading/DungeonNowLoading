package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.monster.ScuttleEntity;
import dev.hexnowloading.dungeonnowloading.entity.projectile.FlameProjectileEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class ScuttleFlameThrowerAttackGoal extends Goal {

    private final ScuttleEntity scuttleEntity;
    private int nextScanTick;
    private int attackTicks;

    public ScuttleFlameThrowerAttackGoal(ScuttleEntity scuttleEntity) {
        this.scuttleEntity = scuttleEntity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    private int nextStartTick() { return reducedTickDelay(10); }

    private int nextCooldownStartTick() { return reducedTickDelay(100 + scuttleEntity.getRandom().nextInt(100)); }

    @Override
    public boolean canUse() {
        if (nextScanTick > 0) {
            --this.nextScanTick;
            return false;
        } else {
            boolean hasPlayerInRange = scuttleEntity.level().hasNearbyAlivePlayer(scuttleEntity.blockPosition().getX(), scuttleEntity.blockPosition().getY(), scuttleEntity.blockPosition().getZ(), 6);
            this.nextScanTick = this.nextStartTick();
            return scuttleEntity.isState(ScuttleEntity.ScuttleState.CLOSED) && hasPlayerInRange;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return scuttleEntity.isAttackingState();
    }

    @Override
    public void start() {
        scuttleEntity.setState(ScuttleEntity.ScuttleState.OPENING);
        scuttleEntity.triggerMouthOpenAnimation();
        this.attackTicks = 10;
        scuttleEntity.setAttackTick(0);
    }

    @Override
    public void tick() {
        if (this.attackTicks > 0) {
            this.attackTicks--;
        } else {
            switch (scuttleEntity.getState()) {
                case OPENING:
                    scuttleEntity.setState(ScuttleEntity.ScuttleState.OPENED);
                    scuttleEntity.triggerIdleOpenedAnimation();
                    this.attackTicks = 50;
                    break;
                case OPENED:
                    scuttleEntity.setState(ScuttleEntity.ScuttleState.CLOSING);
                    scuttleEntity.triggerMouthCloseAnimation();
                    this.attackTicks = 8;
                    break;
                default:
                    scuttleEntity.setState(ScuttleEntity.ScuttleState.CLOSED);
                    scuttleEntity.triggerIdleClosedAnimation();
                    this.nextScanTick = this.nextCooldownStartTick();
                    break;
            }
        }
        if (this.scuttleEntity.isState(ScuttleEntity.ScuttleState.OPENED)) {
            if (this.attackTicks % 2 == 0) {
                for (int i = 0; i < 4; i++) {
                    float PROJECTILE_SPEED = 0.3F;
                    float DISTANCE = 1.0F;
                    float rotationRadian = Mth.DEG_TO_RAD * scuttleEntity.getYRot() + 90 * i + (int) (180 * ((50 - (float)attackTicks) / 50));
                    FlameProjectileEntity flameProjectileEntity = new FlameProjectileEntity(scuttleEntity, scuttleEntity.level());
                    flameProjectileEntity.setOwner(scuttleEntity);
                    flameProjectileEntity.shootFromRotation(scuttleEntity, 0.0F, scuttleEntity.getYRot() + 90 * i + (int) (180 * ((50 - (float)attackTicks) / 50)), 0.0F, PROJECTILE_SPEED, DISTANCE);
                    scuttleEntity.level().addFreshEntity(flameProjectileEntity);
                    ((ServerLevel) scuttleEntity.level()).sendParticles(DNLParticleTypes.LARGE_FLAME_PARTICLE.get(), scuttleEntity.getX(), scuttleEntity.getY(), scuttleEntity.getZ(), 1, Mth.cos(rotationRadian), 0.0, Mth.sin(rotationRadian), 0.01);
                }
            }
        }
    }
}
