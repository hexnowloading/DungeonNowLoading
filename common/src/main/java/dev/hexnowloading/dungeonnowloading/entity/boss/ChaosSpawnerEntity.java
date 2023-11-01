package dev.hexnowloading.dungeonnowloading.entity.boss;

import com.google.common.collect.Sets;
import dev.hexnowloading.dungeonnowloading.config.BossConfig;
import dev.hexnowloading.dungeonnowloading.entity.misc.SpecialItemEntity;
import dev.hexnowloading.dungeonnowloading.entity.projectile.ChaosSpawnerProjectileEntity;
import dev.hexnowloading.dungeonnowloading.entity.util.EntityScale;
import dev.hexnowloading.dungeonnowloading.entity.util.EntityStates;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

import static net.minecraft.world.entity.boss.wither.WitherBoss.canDestroy;

public class ChaosSpawnerEntity extends Monster {
    private static final EntityDataAccessor<Boolean> DATA_FLAGS_ID = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<State> DATA_STATE;
    private static final EntityDataAccessor<BlockPos> SPAWN_POINT = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AWAKENING_TICKS = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ACTIVE_RANGE = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PLAYER_COUNT = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> PLAYER_UUID = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private static final int MAX_BOSS_RANGE = 30;

    protected int attackTickCount;
    private final Set<UUID> playerUUIDs;
    private UUID currentPlayerUUID;

    public final AnimationState awakeningAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    private final ServerBossEvent bossEvent;

    public ChaosSpawnerEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPersistenceRequired();
        this.bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        this.xpReward = 500;
        this.moveControl = new ChaosSpawnerMoveControl(this);
        this.playerUUIDs = Sets.newHashSet();
        this.currentPlayerUUID = UUID.randomUUID();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new ChaosSpawnerResetAI(this, MAX_BOSS_RANGE));
        //this.goalSelector.addGoal(2, new ChaosSpawnerSummonMobAttackAI(this, 1));
        this.goalSelector.addGoal(2, new ChaosSpawnerAbilityA());
        this.goalSelector.addGoal(2, new ChaosSpawnerAbilityB());
        this.goalSelector.addGoal(2, new ChaosSpawnerAbilityC());
        this.goalSelector.addGoal(2, new ChaosSpawnerAbilityE());
        //this.goalSelector.addGoal(2, new ChaosSpawnerAbilityF());
        this.goalSelector.addGoal(2, new ChaosSpawnerAbilityG());
        this.goalSelector.addGoal(2, new ChaosSpawnerLookAtPlayerGoal(this, Player.class, 30.0F, 1.0F));
        this.targetSelector.addGoal(1, new ChaosSpawnerPlayerTargetGoal());
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return super.createNavigation(level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPAWN_POINT, BlockPos.ZERO);
        this.entityData.define(DATA_STATE, State.NONE);
        this.entityData.define(PHASE, 0);
        this.entityData.define(DATA_FLAGS_ID, false);
        this.entityData.define(AWAKENING_TICKS, 0);
        this.entityData.define(ATTACK_TICK, 0);
        this.entityData.define(ACTIVE_RANGE, MAX_BOSS_RANGE);
        this.entityData.define(PLAYER_COUNT, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpawnPointX", this.getSpawnPointPos().getX());
        compoundTag.putInt("SpawnPointY", this.getSpawnPointPos().getY());
        compoundTag.putInt("SpawnPointZ", this.getSpawnPointPos().getZ());
        compoundTag.putInt("Phase", this.entityData.get(PHASE));
        compoundTag.putInt("AwakeningTicks", this.entityData.get(AWAKENING_TICKS));
        compoundTag.putInt("AttackTicks", this.attackTickCount);
        compoundTag.putInt("ActiveRange", this.entityData.get(ACTIVE_RANGE));
        compoundTag.putInt("PlayerCount", this.entityData.get(PLAYER_COUNT));
        ListTag listTag = new ListTag();
        CompoundTag uuidCompoundTag;
        for (Iterator var = this.playerUUIDs.iterator(); var.hasNext(); listTag.add(uuidCompoundTag)) {
            UUID uuid1 = (UUID)var.next();
            uuidCompoundTag = new CompoundTag();
            uuidCompoundTag.putUUID(uuid1.toString(), uuid1);
        }
        compoundTag.put("PlayerUUIDs", listTag);
//        ListTag listTag;
//        int a;
//        if (compoundTag.contains("PlayerUUIDs", 9)) {
//            listTag = compoundTag.getList("PlayerUUIDs", 5);
//
//            for (a = 0; a < listTag.size(); ++a) {
//                this.playerUUIDs[a] = listTag.getCompound(a);
//            }
//        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        int i = compoundTag.getInt("SpawnPointX");
        int j = compoundTag.getInt("SpawnPointY");
        int k = compoundTag.getInt("SpawnPointZ");
        this.entityData.set(SPAWN_POINT, new BlockPos(i, j, k));
        this.entityData.set(AWAKENING_TICKS, compoundTag.getInt("AwakeningTicks"));
        this.entityData.set(ACTIVE_RANGE, compoundTag.getInt("ActiveRange"));
        this.entityData.set(PLAYER_COUNT, compoundTag.getInt("PlayerCount"));
        this.attackTickCount = compoundTag.getInt("AttackTicks");
        int phase = compoundTag.getInt("Phase");
        if (phase < 1) {
            this.entityData.set(DATA_STATE, State.SLEEPING);
            this.entityData.set(PHASE, 0);
        } else {
            this.entityData.set(PHASE, phase);
        }
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
        ListTag listTag;
        int a;
        if (compoundTag.contains("PlayerUUIDs", 9)) {
            listTag = compoundTag.getList("PlayerUUIDs", 10);
            for (a = 0; a < listTag.size(); ++a) {
                this.playerUUIDs.add(listTag.getCompound(a).getUUID("PlayerUUIDs"));
            }
        }
    }

    @Override
    public void recreateFromPacket(@NotNull ClientboundAddEntityPacket clientboundAddEntityPacket) {
        super.recreateFromPacket(clientboundAddEntityPacket);
        this.yBodyRot = 0.0F;
        this.yBodyRotO = 0.0F;
    }

    protected @NotNull BodyRotationControl createBodyControl() {
        return new ChaosSpawnerBodyRotationControl(this);
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossEvent.setName(this.getDisplayName());
    }

//    @Override
//    public boolean isNoGravity() {
//        return this.entityData.get(DATA_STATE) != State.ABILITY_F;
//    }

    @Override
    // Called when entity starts rendering
    public void startSeenByPlayer(@NotNull ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
        if (this.entityData.get(PHASE) == 0) {
            disableBossBar();
        }
    }

    @Override
    // Called when entity stops rendering
    public void stopSeenByPlayer(@NotNull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    protected void customServerAiStep() {
        if (this.entityData.get(AWAKENING_TICKS) > 0) {
            int k1 = this.entityData.get(AWAKENING_TICKS) - 1;
            if (k1 <= 0) {
                this.entityData.set(PHASE, 1);
                this.entityData.set(DATA_STATE, State.IDLE);
            }
            this.entityData.set(AWAKENING_TICKS, k1);
            if (k1 == 0) {
                for (int i = 0; i < 50; ++i) {
                    ((ServerLevel) this.level()).sendParticles(ParticleTypes.FLAME, this.getRandomX(0.9D), this.getRandomY(), this.getRandomZ(0.9D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
                enableBossBar();
            }
        }
        if (this.entityData.get(PHASE) > 0) {
            if (attackTickCount > 0) {
                --this.attackTickCount;
            } else {
                AABB aabb = (new AABB(this.blockPosition())).inflate(3);
                List<Player> list = this.level().getEntitiesOfClass(Player.class, aabb);
                if (!list.isEmpty()) {
                    this.attackTickCount = 40;
                    Player player;
                    for (Iterator<Player> var2 = list.iterator(); var2.hasNext(); this.pushNearbyPlayers(player)) {
                        player = (Player) var2.next();
                        player.hurt(this.damageSources().mobAttack(this), 10.0F);
                    }
                } else {
                    int x = this.random.nextInt(100);
                    if (x == 0) {
                        this.entityData.set(DATA_STATE, State.ABILITY_A);
                    } else if (x == 1) {
                        this.entityData.set(DATA_STATE, State.ABILITY_B);
                    } else if (x == 2) {
                        this.entityData.set(DATA_STATE, State.ABILITY_C);
                    } else if (x == 3) {
                        this.entityData.set(DATA_STATE, State.ABILITY_E);
                    } else {
                        this.entityData.set(DATA_STATE, State.ABILITY_G);
                    }
                }
            }
        }
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    private void pushNearbyPlayers(Player player) {
        double x = player.getX() - this.getX();
        double z = player.getZ() - this.getZ();
        double a = Math.max(x * x + z * z, 0.001);
        player.push(x / a * 4.0, 0.2, z / a * 4.0);
    }



    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_STATE.equals(entityDataAccessor)) {
            State state = this.entityData.get(DATA_STATE);
            resetAnimations();
            switch (state) {
                case SLEEPING -> this.sleepingAnimationState.start(this.tickCount);
                case AWAKENING -> {
                    this.sleepingAnimationState.stop();
                    this.awakeningAnimationState.start(this.tickCount);
                }
            }
        }

        super.onSyncedDataUpdated(entityDataAccessor);
    }

    private void resetAnimations() {
        this.sleepingAnimationState.stop();
        this.awakeningAnimationState.stop();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, SpawnGroupData spawnGroupData, CompoundTag compoundTag) {
        this.entityData.set(SPAWN_POINT, this.blockPosition());
        this.entityData.set(DATA_STATE, State.SLEEPING);
        this.setYRot(0.0F);
        this.setXRot(0.0F);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        if (this.isAlive() && this.entityData.get(PHASE) < 1) {
            AABB bossArena = new AABB(this.blockPosition()).inflate(this.entityData.get(ACTIVE_RANGE));
            List<ServerPlayer> players = this.level().getEntitiesOfClass(ServerPlayer.class, bossArena);
            for (ServerPlayer p : players) {
                playerUUIDs.add(p.getUUID());
            }
            int playerCount = players.size();
            EntityScale.scaleHealth(this, playerCount);
            EntityScale.scaleAttack(this, playerCount);
            this.entityData.set(PLAYER_COUNT, playerCount);
            this.entityData.set(AWAKENING_TICKS, 100);
            this.entityData.set(DATA_STATE, State.AWAKENING);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(player, interactionHand);
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float damage) {
        if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !damageSource.isCreativePlayer() && (this.entityData.get(DATA_STATE) == State.SLEEPING || this.entityData.get(DATA_STATE) == State.AWAKENING)) {
            return false;
        }
        return super.hurt(damageSource, damage);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource damageSource, int a, boolean b) {
        if (BossConfig.TOGGLE_MULTIPLAYER_LOOT.get()) {
            for (UUID playerUUID : this.playerUUIDs) {
                this.currentPlayerUUID = playerUUID;
                this.spawnLootTableItems(damageSource, true);
            }
        } else {
            super.dropCustomDeathLoot(damageSource, a, b);
        }
    }

    @Override
    protected void dropFromLootTable(DamageSource $$0, boolean $$1) {
        if (!BossConfig.TOGGLE_MULTIPLAYER_LOOT.get()) {
            super.dropFromLootTable($$0, $$1);
        }
    }

    public void spawnLootTableItems(DamageSource damageSource, boolean b) {
        ResourceLocation resourceLocation = this.getLootTable();
        LootTable lootTable = this.level().getServer().getLootData().getLootTable(resourceLocation);
        LootParams.Builder lootparams$builder = (new LootParams.Builder((ServerLevel) this.level())).withParameter(LootContextParams.THIS_ENTITY, this).withParameter(LootContextParams.ORIGIN, this.position()).withParameter(LootContextParams.DAMAGE_SOURCE, damageSource).withOptionalParameter(LootContextParams.KILLER_ENTITY, damageSource.getEntity()).withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, damageSource.getDirectEntity());
        if (b && this.lastHurtByPlayer != null) {
            lootparams$builder = lootparams$builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, this.lastHurtByPlayer).withLuck(this.lastHurtByPlayer.getLuck());
        }

        LootParams lootParams = lootparams$builder.create(LootContextParamSets.ENTITY);
        lootTable.getRandomItems(lootParams, this.getLootTableSeed(), this::spawnSpecialItemAtLocation);
    }

    public SpecialItemEntity spawnSpecialItemAtLocation(ItemStack itemStack) {
        return spawnSpecialItemEntity(itemStack, 0.0F, this.currentPlayerUUID);
    }

    public SpecialItemEntity spawnSpecialItemEntity(ItemStack itemStack, float i, UUID uuid) {
        if (itemStack.isEmpty()) {
            return null;
        } else if (this.level().isClientSide) {
            return null;
        } else {
            SpecialItemEntity specialItemEntity = new SpecialItemEntity(this.level(), this.getX(), this.getY() + i, this.getZ(), itemStack);
            specialItemEntity.setPickerUUID(uuid);
            specialItemEntity.setDefaultPickUpDelay();
            this.level().addFreshEntity(specialItemEntity);
            return specialItemEntity;
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public void push(Entity entity) {
    }

    @Override
    protected boolean updateInWaterStateAndDoFluidPushing() {
        return false;
    }

    @Override
    protected void jumpFromGround() {
        Vec3 $$0 = this.getDeltaMovement();
        this.setDeltaMovement($$0.x, (double)this.getJumpPower(), $$0.z);
        this.hasImpulse = true;
    }

    //    @Override
//    public Vec3 getDeltaMovement() {
//        if (this.entityData.get(DATA_STATE) != State.ABILITY_F || this.entityData.get(DATA_STATE) == State.ABILITY_G) {
//            return Vec3.ZERO;
//        }
//        return super.getDeltaMovement();
//    }
//
//    @Override
//    public void setDeltaMovement(Vec3 vec3) {
//        if (this.entityData.get(DATA_STATE) == State.ABILITY_F || this.entityData.get(DATA_STATE) == State.ABILITY_G) {
//            super.setDeltaMovement(vec3);
//        }
//    }

    public boolean isAwakening() { return this.entityData.get(DATA_STATE) == State.AWAKENING; }

    public int getAwakeningTick() { return this.entityData.get(AWAKENING_TICKS); }

    public int getPhase() { return this.entityData.get(PHASE); }

    private void enableBossBar() { this.bossEvent.setVisible(true); }

    private void disableBossBar() { this.bossEvent.setVisible(false); }

    static {
        DATA_STATE = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityStates.CHAOS_SPAWNER_STATE);
    }

    public enum State {
        NONE,
        SLEEPING,
        AWAKENING,
        IDLE,
        ABILITY_A,
        ABILITY_B,
        ABILITY_C,
        ABILITY_D,
        ABILITY_E,
        ABILITY_F,
        ABILITY_G,
        ABILITY_H;

        private State() {}
    }

    BlockPos getSpawnPointPos() {
        return (BlockPos)this.entityData.get(SPAWN_POINT);
    }

    public class ChaosSpawnerResetAI extends Goal {
        private final double range;
        ChaosSpawnerEntity livingEntity;
        Level level;

        public ChaosSpawnerResetAI(ChaosSpawnerEntity livingEntity, double range) {
            this.livingEntity = livingEntity;
            this.range = range;
            this.level = livingEntity.level();
        }

        @Override
        public boolean canUse() {
            if (this.livingEntity.entityData.get(DATA_STATE) != State.SLEEPING && BossConfig.TOGGLE_BOSS_RESET.get()) {
                AABB aabb = (new AABB(this.livingEntity.blockPosition())).inflate(range);
                List<Player> list = level.getEntitiesOfClass(Player.class, aabb);
                return list.isEmpty();
            }
            return false;
        }

        public void start() {
            for (int i = 0; i < 50; ++i) {
                ((ServerLevel) this.level).sendParticles(ParticleTypes.POOF, this.livingEntity.getRandomX(1.0D), this.livingEntity.getRandomY(), this.livingEntity.getRandomZ(1.0D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            disableBossBar();
            this.livingEntity.entityData.set(DATA_STATE, State.SLEEPING);
            this.livingEntity.entityData.set(AWAKENING_TICKS, 0);
            this.livingEntity.entityData.set(PHASE, 0);
            this.livingEntity.setHealth(this.livingEntity.getMaxHealth());
            this.livingEntity.setPos(getSpawnPointPos().getX() + 0.5, getSpawnPointPos().getY(), getSpawnPointPos().getZ() + 0.5);
            this.livingEntity.attackTickCount = 0;
        }
    }

    public class ChaosSpawnerLookAtPlayerGoal extends Goal {
        public static final float DEFAULT_PROBABILITY = 0.02F;
        protected final Mob mob;
        @Nullable
        protected Entity lookAt;
        protected final float lookDistance;
        private int lookTime;
        protected final float probability;
        private final boolean onlyHorizontal;
        protected final Class<? extends LivingEntity> lookAtType;
        protected final TargetingConditions lookAtContext;

        public ChaosSpawnerLookAtPlayerGoal(Mob $$0, Class<? extends LivingEntity> $$1, float $$2) {
            this($$0, $$1, $$2, 0.02F);
        }

        public ChaosSpawnerLookAtPlayerGoal(Mob $$0, Class<? extends LivingEntity> $$1, float $$2, float $$3) {
            this($$0, $$1, $$2, $$3, false);
        }

        public ChaosSpawnerLookAtPlayerGoal(Mob $$0, Class<? extends LivingEntity> $$1, float $$2, float $$3, boolean $$4) {
            this.mob = $$0;
            this.lookAtType = $$1;
            this.lookDistance = $$2;
            this.probability = $$3;
            this.onlyHorizontal = $$4;
            this.setFlags(EnumSet.of(Flag.LOOK));
            if ($$1 == Player.class) {
                this.lookAtContext = TargetingConditions.forNonCombat().range((double)$$2).ignoreLineOfSight().ignoreInvisibilityTesting().selector(($$1x) -> {
                    return EntitySelector.notRiding($$0).test($$1x);
                });
            } else {
                this.lookAtContext = TargetingConditions.forNonCombat().range((double)$$2).ignoreLineOfSight().ignoreInvisibilityTesting();
            }

        }

        public boolean canUse() {
            if (this.mob.getRandom().nextFloat() >= this.probability) {
                return false;
            } else {
                if (this.mob.getTarget() != null) {
                    this.lookAt = this.mob.getTarget();
                }

                if (this.lookAtType == Player.class) {
                    this.lookAt = this.mob.level().getNearestPlayer(this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
                } else {
                    this.lookAt = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.lookAtType, this.mob.getBoundingBox().inflate((double)this.lookDistance, 3.0, (double)this.lookDistance), ($$0) -> {
                        return true;
                    }), this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
                }

                return this.lookAt != null;
            }
        }

        public boolean canContinueToUse() {
            if (!this.lookAt.isAlive()) {
                return false;
            } else if (this.mob.distanceToSqr(this.lookAt) > (double)(this.lookDistance * this.lookDistance)) {
                return false;
            } else {
                return this.lookTime > 0;
            }
        }

        public void start() {
            this.lookTime = this.adjustedTickDelay(40 + this.mob.getRandom().nextInt(40));
        }

        public void stop() {
            this.lookAt = null;
        }

        public void tick() {
            if (this.lookAt.isAlive()) {
                double $$0 = this.onlyHorizontal ? this.mob.getEyeY() : this.lookAt.getEyeY();
                this.mob.getLookControl().setLookAt(this.lookAt.getX(), $$0, this.lookAt.getZ());
                --this.lookTime;
            }
        }
    }

    public class ChaosSpawnerMoveControl extends MoveControl {
        private final ChaosSpawnerEntity chaosSpawner;

        public ChaosSpawnerMoveControl(ChaosSpawnerEntity chaosSpawnerEntity) {
            super(chaosSpawnerEntity);
            this.chaosSpawner = chaosSpawnerEntity;
        }

        public void tick() {
            if (this.operation == Operation.MOVE_TO) {
                double dx = this.wantedX - this.chaosSpawner.getX();
                double dz = this.wantedZ - this.chaosSpawner.getZ();
                double dy = this.wantedY - this.chaosSpawner.getY();
                Vec3 vec3 = new Vec3(dx, dy, dz);
                this.chaosSpawner.setDeltaMovement(this.chaosSpawner.getDeltaMovement().add(vec3.scale(0.1D)));
                if (vec3.length() == 0) {
                    this.operation = Operation.WAIT;
                }
                BlockPos blockPos = this.chaosSpawner.blockPosition();
                BlockState blockState = this.chaosSpawner.level().getBlockState(blockPos);
                VoxelShape voxelShape = blockState.getCollisionShape(this.chaosSpawner.level(), blockPos);
                if (dy > (double)this.chaosSpawner.maxUpStep() && dx * dx + dz * dz < (double)Math.max(1.0F, this.chaosSpawner.getBbWidth())
                        || !voxelShape.isEmpty()
                        && this.chaosSpawner.getY() < voxelShape.max(Direction.Axis.Y) + (double)blockPos.getY()
                        && !blockState.is(BlockTags.DOORS)
                        && !blockState.is(BlockTags.FENCES)) {
                    this.chaosSpawner.getJumpControl().jump();
                    this.operation = Operation.JUMPING;
                }
            } else if (this.operation == MoveControl.Operation.JUMPING) {
                this.chaosSpawner.setSpeed((float) (this.speedModifier * this.chaosSpawner.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                if (this.chaosSpawner.onGround()) {
                    this.operation = MoveControl.Operation.WAIT;
                }
            }
        }
    }

    public class ChaosSpawnerAbilityA extends Goal {

        @Override
        public boolean canUse() {
            return ChaosSpawnerEntity.this.entityData.get(DATA_STATE) == State.ABILITY_A && ChaosSpawnerEntity.this.getTarget() != null;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void start() {
            super.start();
            ChaosSpawnerEntity.this.attackTickCount = 100;
        }

        @Override
        public void stop() {
            super.stop();
            ChaosSpawnerEntity.this.attackTickCount = 0;
            ChaosSpawnerEntity.this.entityData.set(DATA_STATE, State.IDLE);
        }

        @Override
        public void tick() {
            if (ChaosSpawnerEntity.this.attackTickCount == 100) {
                summonRandomMob();
            }
            if (ChaosSpawnerEntity.this.attackTickCount == 100) {
                for (int i = 0; i < 50; ++i) {
                    ((ServerLevel) ChaosSpawnerEntity.this.level()).sendParticles(ParticleTypes.CLOUD, ChaosSpawnerEntity.this.getRandomX(1.0D), ChaosSpawnerEntity.this.getRandomY(), ChaosSpawnerEntity.this.getRandomZ(1.0D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }

        protected void summonRandomMob() {
            ServerLevel serverLevel = (ServerLevel)ChaosSpawnerEntity.this.level();
            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos = ChaosSpawnerEntity.this.blockPosition();
                if (i == 0) {
                    blockPos = ChaosSpawnerEntity.this.blockPosition().offset(5, 1, 0);
                }
                if (i == 1) {
                    blockPos = ChaosSpawnerEntity.this.blockPosition().offset(-5, 1, 0);
                }
                if (i == 2) {
                    blockPos = ChaosSpawnerEntity.this.blockPosition().offset(0, 1, 5);
                }
                if (i == 3) {
                    blockPos = ChaosSpawnerEntity.this.blockPosition().offset(0, 1, -5);
                }
                int x = ChaosSpawnerEntity.this.random.nextInt(3);
                if (x == 0) {
                    Zombie zombie = EntityType.ZOMBIE.create(ChaosSpawnerEntity.this.level());
                    if (zombie != null) {
                        zombie.moveTo(blockPos, 0.0F, 0.0F);
                        zombie.finalizeSpawn(serverLevel, ChaosSpawnerEntity.this.level().getCurrentDifficultyAt(blockPos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                        serverLevel.addFreshEntity(zombie);
                    }
                }
                if (x == 1) {
                    Spider spider = EntityType.SPIDER.create(ChaosSpawnerEntity.this.level());
                    if (spider != null) {
                        spider.moveTo(blockPos, 0.0F, 0.0F);
                        spider.finalizeSpawn(serverLevel, ChaosSpawnerEntity.this.level().getCurrentDifficultyAt(blockPos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                        serverLevel.addFreshEntity(spider);
                    }
                }
                if (x == 2) {
                    Skeleton skeleton = EntityType.SKELETON.create(ChaosSpawnerEntity.this.level());
                    if (skeleton != null) {
                        skeleton.moveTo(blockPos, 0.0F, 0.0F);
                        skeleton.finalizeSpawn(serverLevel, ChaosSpawnerEntity.this.level().getCurrentDifficultyAt(blockPos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                        serverLevel.addFreshEntity(skeleton);
                    }
                }
            }
        }
    }

    public class ChaosSpawnerAbilityB extends Goal {

        @Override
        public boolean canUse() {
            return ChaosSpawnerEntity.this.entityData.get(DATA_STATE) == State.ABILITY_B && ChaosSpawnerEntity.this.getTarget() != null;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void start() {
            super.start();
            ChaosSpawnerEntity.this.attackTickCount = 100;
        }

        @Override
        public void stop() {
            super.stop();
            ChaosSpawnerEntity.this.attackTickCount = 0;
            ChaosSpawnerEntity.this.entityData.set(DATA_STATE, State.IDLE);
        }

        @Override
        public void tick() {
            if (ChaosSpawnerEntity.this.attackTickCount == 100) {
                summonTnt();
            }
        }

        private void summonTnt() {
            ServerLevel serverLevel = (ServerLevel) ChaosSpawnerEntity.this.level();
            for (int i = 0; i < 4; ++i) {
                int x = ChaosSpawnerEntity.this.blockPosition().getX() + ChaosSpawnerEntity.this.random.nextInt(5, 10) * (ChaosSpawnerEntity.this.random.nextBoolean() ? 1 : -1);
                int y = ChaosSpawnerEntity.this.blockPosition().getY() + 5;
                int z = ChaosSpawnerEntity.this.blockPosition().getZ() + ChaosSpawnerEntity.this.random.nextInt(5, 10) * (ChaosSpawnerEntity.this.random.nextBoolean() ? 1 : -1);
                // BlockPos blockPos = ChaosSpawnerEntity.this.blockPosition().offset(x, 4, x);
                PrimedTnt primedTnt = new PrimedTnt(ChaosSpawnerEntity.this.level(), x, y, z, (LivingEntity) null);
                ChaosSpawnerEntity.this.level().addFreshEntity(primedTnt);
            }
        }
    }

    public class ChaosSpawnerAbilityC extends Goal {

        @Override
        public boolean canUse() {
            return ChaosSpawnerEntity.this.entityData.get(DATA_STATE) == State.ABILITY_C && ChaosSpawnerEntity.this.getTarget() != null;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void start() {
            super.start();
            ChaosSpawnerEntity.this.attackTickCount = 100;
        }

        @Override
        public void stop() {
            super.stop();
            ChaosSpawnerEntity.this.attackTickCount = 0;
            ChaosSpawnerEntity.this.entityData.set(DATA_STATE, State.IDLE);
        }

        @Override
        public void tick() {
            if (ChaosSpawnerEntity.this.attackTickCount == 100) { summonSpawner(); }
            if (ChaosSpawnerEntity.this.attackTickCount == 80) { summonSpawner(); }
            if (ChaosSpawnerEntity.this.attackTickCount == 60) { summonSpawner(); }
            if (ChaosSpawnerEntity.this.attackTickCount == 40) { summonSpawner(); }

        }

        private void summonSpawner() {
            ServerLevel serverLevel = (ServerLevel) ChaosSpawnerEntity.this.level();
            for (int i = 0; i < 20; ++i) {
                int x = ChaosSpawnerEntity.this.blockPosition().getX() + ChaosSpawnerEntity.this.random.nextInt(5, 10) * (ChaosSpawnerEntity.this.random.nextBoolean() ? 1 : -1);
                int y = ChaosSpawnerEntity.this.blockPosition().getY() + ChaosSpawnerEntity.this.random.nextInt(-2, 3);
                int z = ChaosSpawnerEntity.this.blockPosition().getZ() + ChaosSpawnerEntity.this.random.nextInt(5, 10) * (ChaosSpawnerEntity.this.random.nextBoolean() ? 1 : -1);
                BlockPos blockPosBelowSpawner = new BlockPos(x, y-1, z);
                BlockPos blockPosAtSpawner = new BlockPos(x, y, z);
                BlockState blockStateBelowSpawner = serverLevel.getBlockState(blockPosBelowSpawner);
                BlockState blockStateAtSpawner = serverLevel.getBlockState(blockPosAtSpawner);
                if (!blockStateBelowSpawner.isAir() && blockStateAtSpawner.isAir()) {
                    serverLevel.setBlock(blockPosAtSpawner, Blocks.SPAWNER.defaultBlockState(), 3);
                    BlockEntity blockEntity = serverLevel.getBlockEntity(blockPosAtSpawner);
                    if (blockEntity instanceof SpawnerBlockEntity) {
                        SpawnerBlockEntity spawnerBlockEntity = (SpawnerBlockEntity) blockEntity;
                        spawnerBlockEntity.setEntityId(randomEntityId(ChaosSpawnerEntity.this.random), ChaosSpawnerEntity.this.random);
                    }
                    break;
                }
            }
        }

        private EntityType<?> randomEntityId(RandomSource randomSource) {
            List<EntityType> list = Arrays.asList(EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER, EntityType.CREEPER);
            return list.get(randomSource.nextInt(list.size()));
        }
    }

    public class ChaosSpawnerAbilityD extends Goal {
        int range = 3;

        @Override
        public boolean canUse() {
            if (ChaosSpawnerEntity.this.entityData.get(DATA_STATE) != State.SLEEPING) {
                AABB aabb = (new AABB(ChaosSpawnerEntity.this.blockPosition())).inflate(range);
                List<Player> list = ChaosSpawnerEntity.this.level().getEntitiesOfClass(Player.class, aabb);
                return list.isEmpty();
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void start() {
            super.start();
            ChaosSpawnerEntity.this.attackTickCount = 100;
        }

        @Override
        public void stop() {
            super.stop();
            ChaosSpawnerEntity.this.attackTickCount = 0;
            ChaosSpawnerEntity.this.entityData.set(DATA_STATE, State.IDLE);
        }

        @Override
        public void tick() {
            if (ChaosSpawnerEntity.this.attackTickCount == 20) {
                for (int i = 0; i < 50; ++i) {
                    ((ServerLevel) ChaosSpawnerEntity.this.level()).sendParticles(ParticleTypes.DRAGON_BREATH, ChaosSpawnerEntity.this.getRandomX(1.0D), ChaosSpawnerEntity.this.getRandomY(), ChaosSpawnerEntity.this.getRandomZ(1.0D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    public class ChaosSpawnerAbilityE extends Goal {

        @Override
        public boolean canUse() {
            return ChaosSpawnerEntity.this.entityData.get(DATA_STATE) == State.ABILITY_E && ChaosSpawnerEntity.this.getTarget() != null;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void start() {
            super.start();
            ChaosSpawnerEntity.this.attackTickCount = 100;
        }

        @Override
        public void stop() {
            super.stop();
            ChaosSpawnerEntity.this.attackTickCount = 0;
            ChaosSpawnerEntity.this.entityData.set(DATA_STATE, State.IDLE);
        }

        @Override
        public void tick() {
            if (ChaosSpawnerEntity.this.attackTickCount == 100) { performRangedAttack(); }
            if (ChaosSpawnerEntity.this.attackTickCount == 80) { performRangedAttack(); }
            if (ChaosSpawnerEntity.this.attackTickCount == 60) { performRangedAttack(); }
            if (ChaosSpawnerEntity.this.attackTickCount == 40) { performRangedAttack(); }
            if (ChaosSpawnerEntity.this.attackTickCount == 20) { performRangedAttack(); }
        }

        private void performRangedAttack() {
            LivingEntity targetEntity = ChaosSpawnerEntity.this.getTarget();
            if (targetEntity != null) {
                double maxTargetDistance = 30.0;
                if(targetEntity.distanceTo(ChaosSpawnerEntity.this) < maxTargetDistance) {
                    vecFromCenterToFrontOfFace(targetEntity);
                }
            }
        }

        private void vecFromCenterToFrontOfFace(LivingEntity targetEntity) {
            double viewDistance = 2.0;
            Vec3 viewVector = ChaosSpawnerEntity.this.getViewVector(1.0F);
            double d0 = viewVector.x * viewDistance;
            double d1 = viewVector.y * viewDistance;
            double d2 = viewVector.z * viewDistance;
            ChaosSpawnerProjectileEntity chaosSpawnerProjectile = new ChaosSpawnerProjectileEntity(ChaosSpawnerEntity.this, d0, d1, d2, ChaosSpawnerEntity.this.level());
            chaosSpawnerProjectile.setPos(ChaosSpawnerEntity.this.getX() + d0, ChaosSpawnerEntity.this.getY(0.5) + d1, ChaosSpawnerEntity.this.getZ() + d2);
            ChaosSpawnerEntity.this.level().addFreshEntity(chaosSpawnerProjectile);
        }

        private void vecFromFrontOfFaceToTarget(LivingEntity targetEntity) {
            double viewDistance = 2.0;
            Vec3 viewVector = ChaosSpawnerEntity.this.getViewVector(1.0F);
            double d0 = ChaosSpawnerEntity.this.getX() + viewVector.x * viewDistance;
            double d1 = ChaosSpawnerEntity.this.getY(0.5) + viewVector.y * viewDistance;
            double d2 = ChaosSpawnerEntity.this.getZ() + viewVector.z * viewDistance;
            double d3 = targetEntity.getX() - d0;
            double d4 = targetEntity.getY(0.5) - d1;
            double d5 = targetEntity.getZ() - (d2);
            ChaosSpawnerProjectileEntity chaosSpawnerProjectile = new ChaosSpawnerProjectileEntity(ChaosSpawnerEntity.this, d3, d4, d5, ChaosSpawnerEntity.this.level());
            chaosSpawnerProjectile.setPos(d0, d1, d2);
            ChaosSpawnerEntity.this.level().addFreshEntity(chaosSpawnerProjectile);
        }

    }

    public class ChaosSpawnerAbilityF extends JumpGoal {

        @Override
        public boolean canUse() {
            return ChaosSpawnerEntity.this.entityData.get(DATA_STATE) == State.ABILITY_F && ChaosSpawnerEntity.this.getTarget() != null;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void start() {
            ChaosSpawnerEntity.this.setNoGravity(true);
            Vec3 vec3 = (new Vec3(1, 0, 0).normalize());
            Entity target = ChaosSpawnerEntity.this.getTarget();
            ChaosSpawnerEntity.this.moveTo(Math.round(target.getX()) + 0.5, Math.round(target.getY() + 5.0), Math.round(target.getZ()) + 0.5);
            ChaosSpawnerEntity.this.attackTickCount = 100;
        }

        @Override
        public void stop() {
            super.stop();
            ChaosSpawnerEntity.this.setNoGravity(true);
            ChaosSpawnerEntity.this.attackTickCount = 0;
            ChaosSpawnerEntity.this.entityData.set(DATA_STATE, State.IDLE);
            //ChaosSpawnerEntity.this.moveControl.setWantedPosition(ChaosSpawnerEntity.this.getX(), ChaosSpawnerEntity.this.getY(), ChaosSpawnerEntity.this.getZ(), 0.0D);
        }

        @Override
        public void tick() {
            if (ChaosSpawnerEntity.this.attackTickCount == 80) {
                ChaosSpawnerEntity.this.setNoGravity(false);
            }
            if (ChaosSpawnerEntity.this.attackTickCount <= 80 ) {
                if (ChaosSpawnerEntity.this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && ChaosSpawnerEntity.this.getY() > ChaosSpawnerEntity.this.entityData.get(SPAWN_POINT).getY()) {
                    int y = Mth.floor(ChaosSpawnerEntity.this.getY());
                    int x = Mth.floor(ChaosSpawnerEntity.this.getX());
                    int z = Mth.floor(ChaosSpawnerEntity.this.getZ());
                    boolean destroyBlock = false;

                    for(int i = -1; i <= 1; ++i) {
                        for(int j = -1; j <= 1; ++j) {
                            for(int k = -1; k <= 3; ++k) {
                                int x1 = x + i;
                                int y2 = y + k;
                                int z2 = z + j;
                                BlockPos blockPos = new BlockPos(x1, y2, z2);
                                BlockState blockState = ChaosSpawnerEntity.this.level().getBlockState(blockPos);
                                if (canDestroy(blockState)) {
                                    ((ServerLevel) ChaosSpawnerEntity.this.level()).sendParticles(ParticleTypes.EXPLOSION, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                                    destroyBlock = ChaosSpawnerEntity.this.level().destroyBlock(blockPos, true, ChaosSpawnerEntity.this) || destroyBlock;
                                }
                            }
                        }
                    }
                } else {

                }
                //ChaosSpawnerEntity.this.moveControl.setWantedPosition(ChaosSpawnerEntity.this.getX() + 3.0F, ChaosSpawnerEntity.this.getY(), ChaosSpawnerEntity.this.getZ(), 1.0D);
                //ChaosSpawnerEntity.this.moveTo(ChaosSpawnerEntity.this.getX() + 3.0F, ChaosSpawnerEntity.this.getY(), ChaosSpawnerEntity.this.getZ());
            }
        }
    }

    public class ChaosSpawnerAbilityG extends Goal {
        int x;
        int z;
        int y;
        @Override
        public boolean canUse() {
            return ChaosSpawnerEntity.this.entityData.get(DATA_STATE) == State.ABILITY_G && ChaosSpawnerEntity.this.getTarget() != null;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void start() {
            super.start();
            x = 0;
            z = 0;
            y = 0;
            ChaosSpawnerEntity.this.attackTickCount = 60;
        }

        @Override
        public void stop() {
            super.stop();
            ChaosSpawnerEntity.this.attackTickCount = 0;
            ChaosSpawnerEntity.this.entityData.set(DATA_STATE, State.IDLE);
        }

        @Override
        public void tick() {
            if (ChaosSpawnerEntity.this.attackTickCount == 60) {
                int i = ChaosSpawnerEntity.this.random.nextInt(0,4);
                if (i == 0) {
                    x = 3;
                } else if (i == 1) {
                    x = -3;
                } else if (i == 2) {
                    z = 3;
                } else if (i == 3) {
                    z = -3;
                }
                System.out.println(i);
                //ChaosSpawnerEntity.this.getDeltaMovement().add(x, y, z).normalize().scale(0.1D);
                //BlockPos blockPos = ChaosSpawnerEntity.this.blockPosition().offset(3, 0, 0);
//                BlockState blockState = ChaosSpawnerEntity.this.level().getBlockState(blockPos);
//                for (int j = 0; j < 3; j++) {
//                    if (!blockState.isAir()) {
//                        blockPos.offset(0, 1, 0);
//                    } else {
//                        break;
//                    }
//                }
                //ChaosSpawnerEntity.this.getNavigation().createPath(blockPos, 10);
                ChaosSpawnerEntity.this.getMoveControl().setWantedPosition(ChaosSpawnerEntity.this.getX() + x, ChaosSpawnerEntity.this.getY(), ChaosSpawnerEntity.this.getZ() + z, 1.0D);
                //ChaosSpawnerEntity.this.getNavigation().moveTo(ChaosSpawnerEntity.this.getX() + x, ChaosSpawnerEntity.this.getY(), ChaosSpawnerEntity.this.getZ() + z, 0.1D);
            }
        }
    }

    public class ChaosSpawnerAbilityH extends Goal {

        @Override
        public boolean canUse() {
            return ChaosSpawnerEntity.this.entityData.get(DATA_STATE) == State.ABILITY_H && ChaosSpawnerEntity.this.getTarget() != null;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void start() {
            super.start();
            ChaosSpawnerEntity.this.attackTickCount = 100;
        }

        @Override
        public void stop() {
            super.stop();
            ChaosSpawnerEntity.this.attackTickCount = 0;
            ChaosSpawnerEntity.this.entityData.set(DATA_STATE, State.IDLE);
        }

        @Override
        public void tick() {
            if (ChaosSpawnerEntity.this.attackTickCount == 60) {
                for (int i = 0; i < 50; ++i) {
                    ((ServerLevel) ChaosSpawnerEntity.this.level()).sendParticles(ParticleTypes.DRAGON_BREATH, ChaosSpawnerEntity.this.getRandomX(1.0D), ChaosSpawnerEntity.this.getRandomY(), ChaosSpawnerEntity.this.getRandomZ(1.0D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    public class ChaosSpawnerSummonMobAttackAI extends Goal {
        ChaosSpawnerEntity chaosSpawnerEntity;
        private int attackTime;
        private int phase;

        public ChaosSpawnerSummonMobAttackAI(ChaosSpawnerEntity chaosSpawnerEntity, int phase) {
            this.chaosSpawnerEntity = chaosSpawnerEntity;
            this.phase = phase;
        }

        @Override
        public boolean canUse() {
            return this.chaosSpawnerEntity.entityData.get(PHASE) == phase;

        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingEntity = this.chaosSpawnerEntity.getTarget();
            return livingEntity != null && livingEntity.isAlive();
        }

        public void start() {
            super.start();
            this.attackTime = 100;
        }

        public void stop() {
            super.stop();
            this.attackTime = 0;
        }

        public void tick() {
            --this.attackTime;
            ServerLevel serverLevel = (ServerLevel) this.chaosSpawnerEntity.level();
            if (attackTime == 99) {
                for (int i = 0; i < 50; ++i) {
                    ((ServerLevel) this.chaosSpawnerEntity.level()).sendParticles(ParticleTypes.CLOUD, this.chaosSpawnerEntity.getRandomX(1.0D), this.chaosSpawnerEntity.getRandomY(), this.chaosSpawnerEntity.getRandomZ(1.0D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
            if (attackTime == 0) {
                for (int i = 0; i < 50; ++i) {
                    ((ServerLevel) this.chaosSpawnerEntity.level()).sendParticles(ParticleTypes.FLAME, this.chaosSpawnerEntity.getRandomX(1.0D), this.chaosSpawnerEntity.getRandomY(), this.chaosSpawnerEntity.getRandomZ(1.0D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
                summonZombie();
            }
        }

        protected void summonZombie() {
            ServerLevel serverLevel = (ServerLevel)this.chaosSpawnerEntity.level();
            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos = this.chaosSpawnerEntity.blockPosition();
                if (i == 0) {
                    blockPos = this.chaosSpawnerEntity.blockPosition().offset(5, 1, 0);
                }
                if (i == 1) {
                    blockPos = this.chaosSpawnerEntity.blockPosition().offset(-5, 1, 0);
                }
                if (i == 2) {
                    blockPos = this.chaosSpawnerEntity.blockPosition().offset(0, 1, 5);
                }
                if (i == 3) {
                    blockPos = this.chaosSpawnerEntity.blockPosition().offset(0, 1, -5);
                }
                int x = this.chaosSpawnerEntity.random.nextInt(3);
                if (x == 0) {
                    Zombie zombie = EntityType.ZOMBIE.create(this.chaosSpawnerEntity.level());
                    if (zombie != null) {
                        zombie.moveTo(blockPos, 0.0F, 0.0F);
                        zombie.finalizeSpawn(serverLevel, this.chaosSpawnerEntity.level().getCurrentDifficultyAt(blockPos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                        serverLevel.addFreshEntity(zombie);
                    }
                }
                if (x == 1) {
                    Spider spider = EntityType.SPIDER.create(this.chaosSpawnerEntity.level());
                    if (spider != null) {
                        spider.moveTo(blockPos, 0.0F, 0.0F);
                        spider.finalizeSpawn(serverLevel, this.chaosSpawnerEntity.level().getCurrentDifficultyAt(blockPos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                        serverLevel.addFreshEntity(spider);
                    }
                }
                if (x == 2) {
                    Skeleton skeleton = EntityType.SKELETON.create(this.chaosSpawnerEntity.level());
                    if (skeleton != null) {
                        skeleton.moveTo(blockPos, 0.0F, 0.0F);
                        skeleton.finalizeSpawn(serverLevel, this.chaosSpawnerEntity.level().getCurrentDifficultyAt(blockPos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                        serverLevel.addFreshEntity(skeleton);
                    }
                }
            }
        }
    }

    static class ChaosSpawnerBodyRotationControl extends BodyRotationControl {
        public ChaosSpawnerBodyRotationControl(Mob mob) {
            super(mob);
        }

        public void clientTick() {
        }
    }

    class ChaosSpawnerPlayerTargetGoal extends Goal {
        private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D).ignoreLineOfSight().ignoreInvisibilityTesting();
        private int nextScanTick = reducedTickDelay(20);

        @Override
        public boolean canUse() {
            if (this.nextScanTick > 0) {
                --this.nextScanTick;
            } else {
                this.nextScanTick = reducedTickDelay(60);
                List<Player> list = ChaosSpawnerEntity.this.level().getNearbyPlayers(this.attackTargeting, ChaosSpawnerEntity.this, ChaosSpawnerEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    ChaosSpawnerEntity.this.setTarget(list.get(ChaosSpawnerEntity.this.random.nextInt(list.size())));
                    System.out.println(ChaosSpawnerEntity.this.getTarget());
                    return true;
                }
            }
            return false;
        }

        public boolean canContinueToUse() {
            LivingEntity livingEntity = ChaosSpawnerEntity.this.getTarget();
            return livingEntity != null && ChaosSpawnerEntity.this.canAttack(livingEntity, TargetingConditions.DEFAULT);
        }
    }
}
