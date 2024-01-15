package dev.hexnowloading.dungeonnowloading.entity.monster;

import dev.hexnowloading.dungeonnowloading.entity.ai.HollowChargeAttackGoal;
import dev.hexnowloading.dungeonnowloading.entity.ai.HollowMoveControl;
import dev.hexnowloading.dungeonnowloading.entity.ai.HollowRandomMoveGoal;
import dev.hexnowloading.dungeonnowloading.registry.DNLSounds;
import dev.hexnowloading.dungeonnowloading.registry.DNLTags;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class HollowEntity extends Monster {

    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(HollowEntity.class, EntityDataSerializers.BOOLEAN);

    public HollowEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new HollowMoveControl(this);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new HollowChargeAttackGoal(this));
        this.goalSelector.addGoal(8, new HollowRandomMoveGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, HollowEntity.class)).setAlertOthers());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CHARGING, false);
    }

    @Override
    public void move(MoverType moverType, Vec3 vec3) {
        super.move(moverType, vec3);
        this.checkInsideBlocks();
    }

    @Override
    public boolean hurt(DamageSource damageSource, float v) {
        if (!this.level().isClientSide && !this.isNoAi()) {
            if (damageSource.getEntity() instanceof Player player) {
                if (player.getAbilities().instabuild) {
                    return super.hurt(damageSource, v);
                }
            }
            if (!damageSource.is(DNLTags.HOLLOW_HURTABLE)) {
                return false;
            }
            if (damageSource.getDirectEntity() instanceof Arrow arrow) {
                if (arrow.potion == Potions.EMPTY) {
                    return false;
                }
                return super.hurt(damageSource, v);
            }
            return super.hurt(damageSource, v);
        }
        return false;
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return DNLSounds.HOLLOW_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return DNLSounds.HOLLOW_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource $$0) {
        return DNLSounds.HOLLOW_HURT.get();
    }

    public boolean IsCharging() { return this.entityData.get(CHARGING); }

    public void setCharging(boolean b) { this.entityData.set(CHARGING, b); }
}
