package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.monster.ScuttleEntity;
import dev.hexnowloading.dungeonnowloading.entity.projectile.FlameProjectileEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLParticleTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class ScuttleFlameThrowerAttackGoal extends Goal {

    private static final UUID FULL_KNOCKBACK_RESISTANCE_MODIFIER_UUID = UUID.fromString("2a6f22a4-5468-4eed-b100-fe77cdc8bd98");
    private final AttributeModifier FULL_KNOCKBACK_RESISTANCE = new AttributeModifier(FULL_KNOCKBACK_RESISTANCE_MODIFIER_UUID, "Full knockback resistance", 0.5F, AttributeModifier.Operation.ADDITION);
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
            double DETECTION_RANGE = 5.0d;
            boolean hasTargetInRange = scuttleEntity.getTarget() != null && scuttleEntity.getPerceivedTargetDistanceSquareForMeleeAttack(scuttleEntity.getTarget()) < DETECTION_RANGE * DETECTION_RANGE;
            this.nextScanTick = this.nextStartTick();
            return scuttleEntity.isState(ScuttleEntity.ScuttleState.CLOSED) && hasTargetInRange;
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
        scuttleEntity.playShootingOpenSound();
        AttributeInstance attributeInstance = scuttleEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (attributeInstance != null) {
            attributeInstance.addTransientModifier(FULL_KNOCKBACK_RESISTANCE);
        }
        this.attackTicks = 10;
    }

    @Override
    public void tick() {
        if (this.attackTicks % 10 == 0) {
            AABB aabb = (new AABB(scuttleEntity.blockPosition())).inflate(1.2F);
            List<Player> targets = scuttleEntity.level().getEntitiesOfClass(Player.class, aabb);
            for (Player player : targets) {
                player.setSecondsOnFire(5);
                scuttleEntity.doHurtTarget(player);
            }
        }
        if (this.attackTicks > 0) {
            this.attackTicks--;
        } else {
            switch (scuttleEntity.getState()) {
                case OPENING:
                    scuttleEntity.setState(ScuttleEntity.ScuttleState.OPENED);
                    scuttleEntity.triggerIdleOpenedAnimation();
                    scuttleEntity.playShootingChargeSound();
                    this.attackTicks = 90;
                    break;
                case OPENED:
                    scuttleEntity.setState(ScuttleEntity.ScuttleState.CLOSING);
                    scuttleEntity.triggerMouthCloseAnimation();
                    this.attackTicks = 8;
                    break;
                default:
                    AttributeInstance attributeInstance = scuttleEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
                    if (attributeInstance != null) {
                        attributeInstance.removeModifier(FULL_KNOCKBACK_RESISTANCE);
                    }
                    scuttleEntity.setState(ScuttleEntity.ScuttleState.CLOSED);
                    scuttleEntity.triggerIdleClosedAnimation();
                    this.nextScanTick = this.nextCooldownStartTick();
                    break;
            }
        }
        if (this.scuttleEntity.isState(ScuttleEntity.ScuttleState.CLOSING) && this.attackTicks == 7) {
            scuttleEntity.playShootingCloseSound();

        }
        if (this.scuttleEntity.isState(ScuttleEntity.ScuttleState.OPENED)) {
            if (this.attackTicks == 70) {
                scuttleEntity.playShootingBurstSound();
            }
            if (this.attackTicks == 40) {
                scuttleEntity.playShootingStopSound();
            }
            if (this.attackTicks > 20 && this.attackTicks < 70 && this.attackTicks % 2 == 0) {
                scuttleEntity.playShootingFlameSound();
                for (int i = 0; i < 4; i++) {
                    float PROJECTILE_SPEED = 0.3F;
                    float DISTANCE = 1.0F;
                    float rotationRadian = Mth.DEG_TO_RAD * scuttleEntity.getYRot() + 90 * i + (int) (180 * ((50 - (float)attackTicks) / 50));
                    FlameProjectileEntity flameProjectileEntity = new FlameProjectileEntity(scuttleEntity, scuttleEntity.level());
                    flameProjectileEntity.setOwner(scuttleEntity);
                    flameProjectileEntity.shootFromRotation(scuttleEntity, 0.0F, scuttleEntity.getYRot() + 90 * i + (int) (180 * ((50 - (float)attackTicks) / 50)), 0.0F, PROJECTILE_SPEED, DISTANCE);
                    scuttleEntity.level().addFreshEntity(flameProjectileEntity);
                    //((ServerLevel) scuttleEntity.level()).sendParticles(DNLParticleTypes.LARGE_FLAME_PARTICLE.get(), scuttleEntity.getX(), scuttleEntity.getY(), scuttleEntity.getZ(), 1, Mth.cos(rotationRadian), 0.0, Mth.sin(rotationRadian), 0.01);
                }
            }
        }
    }
}
