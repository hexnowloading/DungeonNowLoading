package dev.hexnowloading.dungeonnowloading.entity.monster;

import dev.hexnowloading.dungeonnowloading.entity.ai.SpawnerCarrierAttackGoal;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Arrays;

public class SpawnerCarrierEntity extends Monster {
    public final AnimationState attackAnimationState = new AnimationState();
    private static final EntityDataAccessor<String> SUMMON_MOB_TYPE = SynchedEntityData.defineId(SpawnerCarrierEntity.class, EntityDataSerializers.STRING);
    private int summonTick = 200;
    private final float SPAWN_RANGE = 5;
    private final String[] SUMMON_MOB_TYPE_LIST = {"Zombie", "Skeleton", "Spider"};

    public SpawnerCarrierEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.0F);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.25)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SpawnerCarrierAttackGoal(this, 1.0F, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, SpawnerCarrierEntity.class)).setAlertOthers());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SUMMON_MOB_TYPE, "");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putString("SummonMobType", this.entityData.get(SUMMON_MOB_TYPE));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(SUMMON_MOB_TYPE, compoundTag.getString("SummonMobType"));
    }

    @Override
    protected void customServerAiStep() {
        if (this.getTarget() instanceof Player) {
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.FLAME, this.getX(), this.getY() + 1.75D, this.getZ(), 1, 0.25D, 0.15D, 0.25D, 0.0D);
            if (summonTick > 0) {
                summonTick--;
            } else {
                if (this.getSummonMobType().equals("")) {
                    this.setSummonMobType(Arrays.stream(SUMMON_MOB_TYPE_LIST).toList().get(random.nextInt(SUMMON_MOB_TYPE_LIST.length)));
                }
                int spawnCount = this.getRandom().nextInt(1, 4);
                BlockPos centerPos = this.getOnPos();
                for (int i = 0; i < spawnCount; i++) {
                    RandomSource randomSource = this.level().getRandom();
                    EntityType<?> spawningEntity = this.summonSpawnerMob();
                    double x = centerPos.getX() + (randomSource.nextDouble() - randomSource.nextDouble()) * (double)this.SPAWN_RANGE + 0.5;
                    double y = centerPos.getY() + randomSource.nextInt(3) - 1;
                    double z = centerPos.getZ() + (randomSource.nextDouble() - randomSource.nextDouble()) * (double)this.SPAWN_RANGE + 0.5;
                    if (spawningEntity != null && this.level().noCollision(spawningEntity.getAABB(x, y, z))) {
                        ((ServerLevel) this.level()).sendParticles(ParticleTypes.POOF, x + 0.5F, y + 0.5F, z + 0.5F, 20, 0.3D, 0.3D, 0.3D, 0.0D);
                        ((ServerLevel) this.level()).sendParticles(ParticleTypes.FLAME, x + 0.5F, y + 0.5F, z + 0.5F, 10, 0.3D, 0.3D, 0.3D, 0.0D);
                        Entity livingEntity = spawningEntity.create(this.level());
                        if (livingEntity != null) {
                            BlockPos summonPos = new BlockPos((int) x, (int) y, (int) z);
                            livingEntity.moveTo(x, y, z, 0.0F, 0.0F);
                            ((Monster) livingEntity).finalizeSpawn((ServerLevel) this.level(), this.level().getCurrentDifficultyAt(summonPos), MobSpawnType.MOB_SUMMONED, null, null);
                            this.level().addFreshEntity(livingEntity);
                        }
                    }

                }
                summonTick = 100 + this.getRandom().nextInt(0, 5) * 20;
            }
        }
        super.customServerAiStep();
    }

    private EntityType summonSpawnerMob() {
        switch (this.getSummonMobType()) {
            case "Zombie" -> {
                EntityType<Zombie> zombie = EntityType.ZOMBIE;
                return zombie;
            }
            case "Skeleton" -> {
                EntityType<Skeleton> skeleton = EntityType.SKELETON;
                return skeleton;
            }
            case "Spider" -> {
                EntityType<Spider> spider = EntityType.SPIDER;
                return spider;
            }
        }
        return null;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 0.95F;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.level().broadcastEntityEvent(this, (byte) 4);
        return super.doHurtTarget(entity);
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 4) {
            this.attackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(b);
        }
    }

    @Override
    protected void updateWalkAnimation(float v) {
        float w;
        if (this.getPose() == Pose.STANDING) {
            w = Math.min(v * 6.0F, 1.0F);
        } else {
            w = 0.0F;
        }

        this.walkAnimation.update(w, 1.0F);
    }

    public String getSummonMobType() {
        return this.entityData.get(SUMMON_MOB_TYPE);
    }

    public void setSummonMobType(String string) {
        this.entityData.set(SUMMON_MOB_TYPE, string);
    }
}
