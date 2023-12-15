package dev.hexnowloading.dungeonnowloading.entity.boss;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import dev.hexnowloading.dungeonnowloading.block.*;
import dev.hexnowloading.dungeonnowloading.config.BossConfig;
import dev.hexnowloading.dungeonnowloading.config.PvpConfig;
import dev.hexnowloading.dungeonnowloading.entity.ai.*;
import dev.hexnowloading.dungeonnowloading.entity.misc.SpecialItemEntity;
import dev.hexnowloading.dungeonnowloading.entity.util.EntityScale;
import dev.hexnowloading.dungeonnowloading.entity.util.EntityStates;
import dev.hexnowloading.dungeonnowloading.entity.util.PlayerSupporterEntity;
import dev.hexnowloading.dungeonnowloading.entity.util.WeightedRandomBag;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import net.minecraft.core.BlockPos;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class ChaosSpawnerEntity extends Monster implements Enemy {
    private static final EntityDataAccessor<Boolean> DATA_FLAGS_ID = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<State> DATA_STATE;
    private static final EntityDataAccessor<BlockPos> SPAWN_POINT = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AWAKENING_TICKS = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ACTIVE_RANGE = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PLAYER_COUNT = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BARRIER_NORTH_TICK = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BARRIER_EAST_TICK = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BARRIER_SOUTH_TICK = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BARRIER_WEST_TICK = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BARRIER_UP_TICK = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BARRIER_DOWN_TICK = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> PLAYER_UUID = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    protected int attackTickCount;
    private int barrierCheckTickCount;
    private Set<UUID> playerUUIDs;
    private UUID currentPlayerUUID;
    private List<Player> pushTargets;

    public final AnimationState awakeningAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState smashAnimationState = new AnimationState();

    private final ServerBossEvent bossEvent;

    public ChaosSpawnerEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPersistenceRequired();
        this.bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        this.xpReward = 500;
        this.playerUUIDs = Sets.newHashSet();
        this.currentPlayerUUID = UUID.randomUUID();
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.FOLLOW_RANGE, 30)
                .add(Attributes.FLYING_SPEED)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.ATTACK_DAMAGE, 20.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new ChaosSpawnerResetGoal(this, this.getFollowDistance()));
        this.goalSelector.addGoal(2, new ChaosSpawnerSummonMobGoal(this));
        this.goalSelector.addGoal(2, new ChaosSpawnerShootGhostBulletGoal(this));
        this.goalSelector.addGoal(2, new ChaosSpawnerPushGoal(this));
        this.goalSelector.addGoal(2, new ChaosSpawnerLookAtPlayerGoal(this, Player.class, 30.0F, 1.0F, false));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new ChaosSpawnerPlayerTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (c) -> {
            return c instanceof PlayerSupporterEntity;
        }));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return super.createNavigation(level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPAWN_POINT, BlockPos.ZERO);
        this.entityData.define(DATA_STATE, State.IDLE);
        this.entityData.define(PHASE, 0);
        this.entityData.define(DATA_FLAGS_ID, false);
        this.entityData.define(AWAKENING_TICKS, 0);
        this.entityData.define(ATTACK_TICK, 0);
        this.entityData.define(PLAYER_COUNT, 0);
        this.entityData.define(BARRIER_NORTH_TICK, -1);
        this.entityData.define(BARRIER_EAST_TICK, -1);
        this.entityData.define(BARRIER_SOUTH_TICK, -1);
        this.entityData.define(BARRIER_WEST_TICK, -1);
        this.entityData.define(BARRIER_UP_TICK, -1);
        this.entityData.define(BARRIER_DOWN_TICK, -1);
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
        compoundTag.putInt("PlayerCount", this.entityData.get(PLAYER_COUNT));
        compoundTag.putInt("BarrierNorthTicks", this.entityData.get(BARRIER_NORTH_TICK));
        compoundTag.putInt("BarrierEastTicks,", this.entityData.get(BARRIER_EAST_TICK));
        compoundTag.putInt("BarrierSouthTicks,", this.entityData.get(BARRIER_SOUTH_TICK));
        compoundTag.putInt("BarrierWestTicks,", this.entityData.get(BARRIER_WEST_TICK));
        compoundTag.putInt("BarrierUpTicks,", this.entityData.get(BARRIER_UP_TICK));
        compoundTag.putInt("BarrierDownTicks,", this.entityData.get(BARRIER_DOWN_TICK));
        ListTag listTag = new ListTag();
        CompoundTag uuidCompoundTag = new CompoundTag();
        Iterator<UUID> var = this.playerUUIDs.iterator();
        for (int i = 0; var.hasNext(); i++) {
            listTag.add(uuidCompoundTag);
            uuidCompoundTag.putUUID("PlayerUUID" + i, var.next());
        }
        /*for (var.hasNext(); listTag.add(uuidCompoundTag)) {
            UUID uuid1 = var.next();
            uuidCompoundTag.putUUID("PlayerUUID", uuid1);
        }*/
        compoundTag.put("PlayerUUIDs", listTag);
        System.out.println(this.playerUUIDs);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        int i = compoundTag.getInt("SpawnPointX");
        int j = compoundTag.getInt("SpawnPointY");
        int k = compoundTag.getInt("SpawnPointZ");
        this.entityData.set(SPAWN_POINT, new BlockPos(i, j, k));
        this.entityData.set(AWAKENING_TICKS, compoundTag.getInt("AwakeningTicks"));
        this.entityData.set(PLAYER_COUNT, compoundTag.getInt("PlayerCount"));
        this.entityData.set(BARRIER_NORTH_TICK, compoundTag.getInt("BarrierNorthTicks"));
        this.entityData.set(BARRIER_NORTH_TICK, compoundTag.getInt("BarrierEastTicks"));
        this.entityData.set(BARRIER_NORTH_TICK, compoundTag.getInt("BarrierSouthTicks"));
        this.entityData.set(BARRIER_NORTH_TICK, compoundTag.getInt("BarrierWestTicks"));
        this.entityData.set(BARRIER_NORTH_TICK, compoundTag.getInt("BarrierUpTicks"));
        this.entityData.set(BARRIER_NORTH_TICK, compoundTag.getInt("BarrierDownTicks"));
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
        if (compoundTag.contains("PlayerUUIDs", CompoundTag.TAG_LIST)) {
            ListTag listTag = compoundTag.getList("PlayerUUIDs", CompoundTag.TAG_COMPOUND);
            for (int a = 0; a < listTag.size(); ++a) {
                CompoundTag compoundTag1 = listTag.getCompound(a);
                this.playerUUIDs.add(compoundTag1.getUUID("PlayerUUID" + a));
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
        return new EntityBodyRotationControl(this);
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

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
            if (k1 <= 80 && k1 >= 40) {
                double t = (double) (80 - k1) / 40D;
                double easeOutOffsetY = t * (2 - t) * (2 - t) * (2 - t);
                this.moveTo(this.getX(), this.entityData.get(SPAWN_POINT).getY() + easeOutOffsetY * 1.5D, this.getZ());
            }
            if (k1 <= 0) {
                this.entityData.set(PHASE, 1);
                this.entityData.set(DATA_STATE, State.IDLE);
            }
            this.entityData.set(AWAKENING_TICKS, k1);
            if (k1 == 0) {
                for (int i = 0; i < 50; ++i) {
                    ((ServerLevel) this.level()).sendParticles(ParticleTypes.FLAME, this.getRandomX(0.9D), this.getRandomY(), this.getRandomZ(0.9D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
                this.fillFrames();
                enableBossBar();
            }
        }
        if (this.entityData.get(PHASE) > 0) {
            this.swayUpAndDownAnimation();
            this.abilitySelectionTick();
            this.checkBarrierTick();
            this.phaseUpdateTick();
        }
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    private void phaseUpdateTick() {
        if (this.getPhase() == 1 && this.getHealth() < this.getMaxHealth() * 0.5) {
            this.setPhase(2);
        }
    }

    private void swayUpAndDownAnimation() {
        if (this.entityData.get(DATA_STATE) == State.PUSH) {
            if (this.attackTickCount == 85) {
                //this.getMoveControl().setWantedPosition(this.getX(), this.getY() - 1.0D, this.getZ(), 1.0D);
                Vec3 vec3 = new Vec3(0.0F, -5.0F, 0.0F);
                this.setDeltaMovement(vec3);
            }
            if (this.attackTickCount == 45) {
                //this.getMoveControl().setWantedPosition(this.getX(), this.getY() + 1.0D, this.getZ(), 1.0D);
                //this.getMoveControl().setWantedPosition(this.entityData.get(SPAWN_POINT).getX(), this.entityData.get(SPAWN_POINT).getY() + 1.0D, this.entityData.get(SPAWN_POINT).getZ(), 1.0D);
                Vec3 vec3 = new Vec3(0.0F, 1.0F, 0.0F);
                this.setDeltaMovement(vec3);
            }
            if (this.attackTickCount < 65 && this.entityData.get(SPAWN_POINT).getY() + 1.5F < this.getY()) {
                this.setDeltaMovement(Vec3.ZERO);
            }
        } else {
            float f = tickCount * 0.05F;
            float f1 = Mth.cos(f);
            Vec3 vec3 = new Vec3(0.0F, f1 * 0.01F, 0.0F);
            this.setDeltaMovement(vec3);
        }
    }

    private void abilitySelectionTick() {
        if (this.getTarget() != null) {
            if (attackTickCount > 0) {
                --this.attackTickCount;
            } else {
                if (this.entityData.get(DATA_STATE) == State.IDLE) {
                    WeightedRandomBag<State> attackPool = new WeightedRandomBag<>();
                    AABB aabb = (new AABB(this.blockPosition())).inflate(3);
                    pushTargets = this.level().getEntitiesOfClass(Player.class, aabb);
                    if (!pushTargets.isEmpty()) {
                        attackPool.addEntry(State.PUSH, 8);
                        attackPool.addEntry(State.SHOOT_GHOST_BULLET, 3);
                        attackPool.addEntry(State.SUMMON_MOB, 1);
                    } else {
                        attackPool.addEntry(State.SHOOT_GHOST_BULLET, 3);
                        attackPool.addEntry(State.SUMMON_MOB, 1);
                    }
                    this.entityData.set(DATA_STATE, attackPool.getRandom());
                }
            }
        }
    }

    private void checkBarrierTick() {
        if (barrierCheckTickCount > 0) {
            barrierCheckTickCount--;
        } else {
            barrierCheckTickCount = 20;
            BlockPos cageCenterPos = this.entityData.get(SPAWN_POINT).above(2);
            checkBarrierIsBroken(cageCenterPos.offset(0, 0, -2), BARRIER_NORTH_TICK);
            checkBarrierIsBroken(cageCenterPos.offset(2, 0, 0), BARRIER_EAST_TICK);
            checkBarrierIsBroken(cageCenterPos.offset(0, 0, 2), BARRIER_SOUTH_TICK);
            checkBarrierIsBroken(cageCenterPos.offset(-2, 0, 0), BARRIER_WEST_TICK);
            checkBarrierIsBroken(cageCenterPos.offset(0, 2, 0), BARRIER_UP_TICK);
            checkBarrierIsBroken(cageCenterPos.offset(0, -2, 0), BARRIER_DOWN_TICK);
        }
        regenerateBarrierTick(FRAME_POS_NORTH, BARRIER_NORTH_TICK, this.entityData.get(SPAWN_POINT).above(2).offset(0, 0, -2), 0);
        regenerateBarrierTick(FRAME_POS_EAST, BARRIER_EAST_TICK, this.entityData.get(SPAWN_POINT).above(2).offset(2, 0, 0), 1);
        regenerateBarrierTick(FRAME_POS_SOUTH, BARRIER_SOUTH_TICK, this.entityData.get(SPAWN_POINT).above(2).offset(0, 0, 2), 2);
        regenerateBarrierTick(FRAME_POS_WEST, BARRIER_WEST_TICK, this.entityData.get(SPAWN_POINT).above(2).offset(-2, 0, 0), 3);
        regenerateBarrierTick(FRAME_POS_UP, BARRIER_UP_TICK, this.entityData.get(SPAWN_POINT).above(2).offset(0, 2, 0), 4);
        regenerateBarrierTick(FRAME_POS_DOWN, BARRIER_DOWN_TICK, this.entityData.get(SPAWN_POINT).above(2).offset(0, -2, 0), 5);
    }

    private void checkBarrierIsBroken(BlockPos blockPos, EntityDataAccessor<Integer> entityDataAccessor) {
        if (!this.level().getBlockState(blockPos).is(DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get()) && this.entityData.get(entityDataAccessor) < 0) {
            this.entityData.set(entityDataAccessor, 100);
        }
    }

    private void regenerateBarrierTick(ImmutableList<BlockPos> framePositions , EntityDataAccessor<Integer> entityDataAccessor, BlockPos barrierCenterPos, int barrierDirection) {
        if (this.entityData.get(entityDataAccessor) > 0) {
            int barrierRegenerateTickCount = this.entityData.get(entityDataAccessor) - 1;
            this.entityData.set(entityDataAccessor, barrierRegenerateTickCount);
        } else if (this.entityData.get(entityDataAccessor) != -1) {
            BlockPos cageCenterPos = this.entityData.get(SPAWN_POINT).above(2);
            Iterator<BlockPos> iterator = framePositions.iterator();
            int fixedFrame = 0;
            while(iterator.hasNext()) {
                BlockPos framePos = cageCenterPos.offset(iterator.next());
                BlockState frameState = this.level().getBlockState(framePos);
                if (frameState.is(DNLBlocks.CHAOS_SPAWNER_EDGE.get()) || frameState.is(DNLBlocks.CHAOS_SPAWNER_DIAMOND_EDGE.get()) || frameState.is(DNLBlocks.CHAOS_SPAWNER_DIAMOND_VERTEX.get())) {
                    fixedFrame++;
                } else {
                    if (frameState.getBlock() instanceof ChaosSpawnerEdgeBlock) {
                        this.level().playSound(null, framePos, SoundEvents.END_PORTAL_FRAME_FILL, this.getSoundSource(), 10.0F, 1.0F);
                        ChaosSpawnerEdgeBlock.fixFrame(this.level(), framePos, frameState);
                    } else if (frameState.getBlock() instanceof  ChaosSpawnerVertexBlock) {
                        this.level().playSound(null, framePos, SoundEvents.END_PORTAL_FRAME_FILL, this.getSoundSource(), 10.0F, 1.0F);
                        ChaosSpawnerVertexBlock.fixFrame(this.level(), framePos, frameState);
                    }
                    this.entityData.set(entityDataAccessor, 20);
                    break;
                }
            }
            if (fixedFrame == 16) {
                this.entityData.set(entityDataAccessor, -1);
                this.placeFullBarrier(barrierCenterPos, barrierDirection);
            }
        }
    }

    private void placeFullBarrier(BlockPos barrierCenterPos, int barrierDirection) {
        this.level().playSound(null, barrierCenterPos, SoundEvents.END_PORTAL_SPAWN, this.getSoundSource(), 10.0F, 2.0F);
        ChaosSpawnerBarrierCenterBlock.placeBarrier(this.level(), barrierCenterPos, barrierDirection);
        ChaosSpawnerBarrierEdgeBlock.placeBarrier(this.level(), barrierCenterPos, barrierDirection);
        ChaosSpawnerBarrierVertexBlock.placeBarrier(this.level(), barrierCenterPos, barrierDirection);
    }

    private void fillFrames() {
        Iterator<BlockPos> iterator = FRAME_POSITIONS.iterator();
        BlockPos cageCenterPos = this.entityData.get(SPAWN_POINT).above(2);
        while (iterator.hasNext()) {
            BlockPos framePos = cageCenterPos.offset((BlockPos) iterator.next());
            BlockState frameState = this.level().getBlockState(framePos);
            if (frameState.is(DNLBlocks.CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE.get()) || frameState.is(DNLBlocks.CHAOS_SPAWNER_BROKEN_EDGE.get())) {
                ChaosSpawnerEdgeBlock.fixFrame(this.level(), framePos, frameState);
            } else if (frameState.is(DNLBlocks.CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX.get())) {
                ChaosSpawnerVertexBlock.fixFrame(this.level(), framePos, frameState);
            }
        }
    }



    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_STATE.equals(entityDataAccessor)) {
            State state = this.entityData.get(DATA_STATE);
            //resetAnimations();
            if (state == State.PUSH) {
                this.smashAnimationState.start(this.tickCount);
            } else {
                this.smashAnimationState.stop();
            }
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
        this.smashAnimationState.stop();
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
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(Items.DEBUG_STICK)) {
            this.setXRot(getYRot() + (float) Math.toRadians(45));
            System.out.println("Hi");
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (this.isAlive() && this.entityData.get(PHASE) < 1) {
            AABB bossArena = new AABB(this.blockPosition()).inflate(getFollowDistance());
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
        if (BossConfig.TOGGLE_MULTIPLAYER_LOOT.get() && !this.playerUUIDs.isEmpty()) {
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

    /*@Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }*/

    @Override
    public void push(Entity entity) {
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
        return 1.0F;
    }

    @Override
    protected boolean updateInWaterStateAndDoFluidPushing() {
        return false;
    }

    public boolean isAwakening() { return this.entityData.get(DATA_STATE) == State.AWAKENING; }

    public boolean isSleeping() { return this.entityData.get(DATA_STATE) == State.SLEEPING; }

    public boolean isAttacking(State attackState) {
        return this.entityData.get(DATA_STATE) == attackState;
    }

    public int getAwakeningTick() { return this.entityData.get(AWAKENING_TICKS); }

    public int getAttackTick() { return this.attackTickCount; }

    public int getPhase() { return this.entityData.get(PHASE); }

    public BlockPos getSpawnPointPos() {
        return this.entityData.get(SPAWN_POINT);
    }

    public Set<UUID> getParticipatingPlayerUUIDs() { return this.playerUUIDs; }

    public int getParticipatingPlayerCount() { return this.playerUUIDs.size(); }

    public List<Player> getPushTargets() { return this.pushTargets; }

    public double getAttackDamage() { return this.getAttributeValue(Attributes.ATTACK_DAMAGE); }

    public double getFollowDistance() { return this.getAttributeValue(Attributes.FOLLOW_RANGE); }

    public void setDataState(State state) { this.entityData.set(DATA_STATE, state); }

    public void setAwakeningTick(int tick) { this.entityData.set(AWAKENING_TICKS, tick); }

    public void setAttackTick(int tick) { this.attackTickCount = tick; }

    public void setPhase(int phase) { this.entityData.set(PHASE, phase); }

    public void setBarrierNorthTick(int tick) { this.entityData.set(BARRIER_NORTH_TICK, tick); }

    public void setBarrierEastTick(int tick) { this.entityData.set(BARRIER_EAST_TICK, tick); }

    public void setBarrierSouthTick(int tick) { this.entityData.set(BARRIER_SOUTH_TICK, tick); }

    public void setBarrierWestTick(int tick) { this.entityData.set(BARRIER_WEST_TICK, tick); }

    public void setBarrierUpTick(int tick) { this.entityData.set(BARRIER_UP_TICK, tick); }

    public void setBarrierDownTick(int tick) { this.entityData.set(BARRIER_DOWN_TICK, tick); }

    public void enableBossBar() { this.bossEvent.setVisible(true); }

    public void disableBossBar() { this.bossEvent.setVisible(false); }

    public void stopAttacking() {
        this.entityData.set(DATA_STATE, State.IDLE);
        this.setAttackTick(0);
    }

    static {
        DATA_STATE = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityStates.CHAOS_SPAWNER_STATE);
    }

    public enum State {
        SLEEPING,
        AWAKENING,
        IDLE,
        SUMMON_MOB,
        SHOOT_GHOST_BULLET,
        PUSH;

        private State() {}
    }

    private static final ImmutableList<BlockPos> FRAME_POSITIONS = ImmutableList.of(
            new BlockPos(2, 2, 2),
            new BlockPos(2, 2, 1),
            new BlockPos(2, 2, 0),
            new BlockPos(2, 2, -1),
            new BlockPos(2, 2, -2),
            new BlockPos(-2, 2, 2),
            new BlockPos(-2, 2, 1),
            new BlockPos(-2, 2, 0),
            new BlockPos(-2, 2, -1),
            new BlockPos(-2, 2, -2),
            new BlockPos(1, 2, 2),
            new BlockPos(0, 2, 2),
            new BlockPos(-1, 2, 2),
            new BlockPos(1, 2, -2),
            new BlockPos(0, 2, -2),
            new BlockPos(-1, 2, -2),
            new BlockPos(2, -2, 2),
            new BlockPos(2, -2, 1),
            new BlockPos(2, -2, 0),
            new BlockPos(2, -2, -1),
            new BlockPos(2, -2, -2),
            new BlockPos(-2, -2, 2),
            new BlockPos(-2, -2, 1),
            new BlockPos(-2, -2, 0),
            new BlockPos(-2, -2, -1),
            new BlockPos(-2, -2, -2),
            new BlockPos(1, -2, 2),
            new BlockPos(0, -2, 2),
            new BlockPos(-1, -2, 2),
            new BlockPos(1, -2, -2),
            new BlockPos(0, -2, -2),
            new BlockPos(-1, -2, -2),
            new BlockPos(2, 1, 2),
            new BlockPos(2, 0, 2),
            new BlockPos(2, -1, 2),
            new BlockPos(2, 1, -2),
            new BlockPos(2, 0, -2),
            new BlockPos(2, -1, -2),
            new BlockPos(-2, 1, -2),
            new BlockPos(-2, 0, -2),
            new BlockPos(-2, -1, -2),
            new BlockPos(-2, -1, 2),
            new BlockPos(-2, 0, 2),
            new BlockPos(-2, -1, 2)
    );

    private ImmutableList<BlockPos> FRAME_POS_NORTH = ImmutableList.of(
            new BlockPos(0, 2, -2),
            new BlockPos(-1, 2, -2),
            new BlockPos(-2, 2, -2),
            new BlockPos(-2, 1, -2),
            new BlockPos(-2, 0, -2),
            new BlockPos(-2, -1, -2),
            new BlockPos(-2, -2, -2),
            new BlockPos(-1, -2, -2),
            new BlockPos(0, -2, -2),
            new BlockPos(1, -2, -2),
            new BlockPos(2, -2, -2),
            new BlockPos(2, -1, -2),
            new BlockPos(2, 0, -2),
            new BlockPos(2, 1, -2),
            new BlockPos(2, 2, -2),
            new BlockPos(1, 2, -2)
    );

    private ImmutableList<BlockPos> FRAME_POS_EAST = ImmutableList.of(
            new BlockPos(2, 2, 0),
            new BlockPos(2, 2, -1),
            new BlockPos(2, 2, -2),
            new BlockPos(2, 1, -2),
            new BlockPos(2, 0, -2),
            new BlockPos(2, -1, -2),
            new BlockPos(2, -2, -2),
            new BlockPos(2, -2, -1),
            new BlockPos(2, -2, 0),
            new BlockPos(2, -2, 1),
            new BlockPos(2, -2, 2),
            new BlockPos(2, -1, 2),
            new BlockPos(2, 0, 2),
            new BlockPos(2, 1, 2),
            new BlockPos(2, 2, 2),
            new BlockPos(2, 2, 1)
    );

    private ImmutableList<BlockPos> FRAME_POS_SOUTH = ImmutableList.of(
            new BlockPos(0, 2, 2),
            new BlockPos(1, 2, 2),
            new BlockPos(2, 2, 2),
            new BlockPos(2, 1, 2),
            new BlockPos(2, 0, 2),
            new BlockPos(2, -1, 2),
            new BlockPos(2, -2, 2),
            new BlockPos(1, -2, 2),
            new BlockPos(0, -2, 2),
            new BlockPos(-1, -2, 2),
            new BlockPos(-2, -2, 2),
            new BlockPos(-2, -1, 2),
            new BlockPos(-2, 0, 2),
            new BlockPos(-2, 1, 2),
            new BlockPos(-2, 2, 2),
            new BlockPos(-1, 2, 2)
    );

    private ImmutableList<BlockPos> FRAME_POS_WEST = ImmutableList.of(
            new BlockPos(-2, 2, 0),
            new BlockPos(-2, 2, 1),
            new BlockPos(-2, 2, 2),
            new BlockPos(-2, 1, 2),
            new BlockPos(-2, 0, 2),
            new BlockPos(-2, -1, 2),
            new BlockPos(-2, -2, 2),
            new BlockPos(-2, -2, 1),
            new BlockPos(-2, -2, 0),
            new BlockPos(-2, -2, -1),
            new BlockPos(-2, -2, -2),
            new BlockPos(-2, -1, -2),
            new BlockPos(-2, 0, -2),
            new BlockPos(-2, 1, -2),
            new BlockPos(-2, 2, -2),
            new BlockPos(-2, 2, -1)
    );

    private ImmutableList<BlockPos> FRAME_POS_UP = ImmutableList.of(
            new BlockPos(0, 2, -2),
            new BlockPos(1, 2, -2),
            new BlockPos(2, 2, -2),
            new BlockPos(2, 2, -1),
            new BlockPos(2, 2, 0),
            new BlockPos(2, 2, 1),
            new BlockPos(2, 2, 2),
            new BlockPos(1, 2, 2),
            new BlockPos(0, 2, 2),
            new BlockPos(-1, 2, 2),
            new BlockPos(-2, 2, 2),
            new BlockPos(-2, 2, 1),
            new BlockPos(-2, 2, 0),
            new BlockPos(-2, 2, -1),
            new BlockPos(-2, 2, -2),
            new BlockPos(-1, 2, -2)
    );

    private ImmutableList<BlockPos> FRAME_POS_DOWN = ImmutableList.of(
            new BlockPos(0, -2, -2),
            new BlockPos(1, -2, -2),
            new BlockPos(2, -2, -2),
            new BlockPos(2, -2, -1),
            new BlockPos(2, -2, 0),
            new BlockPos(2, -2, 1),
            new BlockPos(2, -2, 2),
            new BlockPos(1, -2, 2),
            new BlockPos(0, -2, 2),
            new BlockPos(-1, -2, 2),
            new BlockPos(-2, -2, 2),
            new BlockPos(-2, -2, 1),
            new BlockPos(-2, -2, 0),
            new BlockPos(-2, -2, -1),
            new BlockPos(-2, -2, -2),
            new BlockPos(-1, -2, -2)
    );
}
