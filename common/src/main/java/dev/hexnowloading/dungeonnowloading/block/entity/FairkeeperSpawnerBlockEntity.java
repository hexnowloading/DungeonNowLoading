package dev.hexnowloading.dungeonnowloading.block.entity;

import dev.hexnowloading.dungeonnowloading.block.FairkeeperSpawnerBlock;
import dev.hexnowloading.dungeonnowloading.entity.util.SlumberingEntity;
import dev.hexnowloading.dungeonnowloading.entity.util.SpawnMobUtil;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlockEntityTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import dev.hexnowloading.dungeonnowloading.util.ArmorTrimMaterial;
import dev.hexnowloading.dungeonnowloading.util.ArmorTrimPattern;
import dev.hexnowloading.dungeonnowloading.util.WeightedRandomBag;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FairkeeperSpawnerBlockEntity extends BlockEntity {

    private static final int SPAWN_RANGE = 4;
    private static final int SPAWN_POS_TRIES = 10;
    private int spawnerLevel;
    private int remainingStoredMobs;
    private int spawnDelay;
    private int startUpTick;
    private int destroyTick;
    private boolean disabled;

    public FairkeeperSpawnerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(DNLBlockEntityTypes.FAIRKEEPER_SPAWNER.get(), blockPos, blockState);
        this.spawnerLevel = 0;
        this.spawnDelay = 0;
        this.startUpTick = 40;
        this.destroyTick = -1;
        this.remainingStoredMobs = 0;
        this.disabled = false;
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        compoundTag.putInt("SpawnerLevel", this.spawnerLevel);
        compoundTag.putInt("RemainingStoredMobs", this.remainingStoredMobs);
        compoundTag.putInt("StartUpTick", this.startUpTick);
        compoundTag.putInt("SpawnDelay", this.spawnDelay);
        compoundTag.putBoolean("Disabled", this.disabled);
        super.saveAdditional(compoundTag);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        this.spawnerLevel = compoundTag.getInt("SpawnerLevel");
        this.remainingStoredMobs = compoundTag.getInt("RemainingStoredMobs");
        this.startUpTick = compoundTag.getInt("StartUpTick");
        this.spawnDelay = compoundTag.getInt("SpawnDelay");
        this.disabled = compoundTag.getBoolean("Disabled");
        super.load(compoundTag);
    }

    public void setDisabled(boolean b) {
        this.disabled = b;
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, FairkeeperSpawnerBlockEntity blockEntity) {
        if (state.getValue(DNLProperties.FAIRKEEPER_ALERT) == Boolean.TRUE) {
            RandomSource randomSource = level.getRandom();
            double d = (double)pos.getX() + randomSource.nextDouble();
            double e = (double)pos.getY() + randomSource.nextDouble();
            double f = (double)pos.getZ() + randomSource.nextDouble();
            level.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0, 0.0, 0.0);
            level.addParticle(ParticleTypes.FLAME, d, e, f, 0.0, 0.0, 0.0);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, FairkeeperSpawnerBlockEntity blockEntity) {
        if (blockEntity.disabled) {
            if (blockEntity.destroyTick < 0) {
                blockEntity.destroyTick = 20 + level.random.nextInt(40);
            }
            blockEntity.destroyTick--;
            if (blockEntity.destroyTick == 0) {
                level.destroyBlock(pos, false);
                double d = (double) pos.getX() + 0.5D;
                double e = (double) pos.getY() + 0.5D;
                double f = (double) pos.getZ() + 0.5D;
                ((ServerLevel) level).sendParticles(DustParticleOptions.REDSTONE, d, e, f, 10, 0.0D, 0.5, 0.5, 0.5);
            }
        }
        if (state.getValue(DNLProperties.FAIRKEEPER_ALERT)) {
            if (blockEntity.startUpTick > 0) {
                if (blockEntity.startUpTick == 40) {
                    level.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.WITHER_SHOOT, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.2F + 0.8F);
                }
                blockEntity.startUpTick--;
            } else {
                if(blockEntity.remainingStoredMobs > 0) {
                    if (blockEntity.spawnDelay > 0) {
                        blockEntity.spawnDelay--;
                    } else {
                        blockEntity.spawnDelay = 20;
                        blockEntity.remainingStoredMobs--;
                        blockEntity.randomMobSummon((ServerLevel) level);
                        level.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.EVOKER_CAST_SPELL, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.2F + 0.8F);
                    }
                } else {
                    level.destroyBlock(pos, false);
                }
            }
        }
    }

    public void alert(int playerCount, BlockPos blockPos, FairkeeperSpawnerBlockEntity blockEntity) {
        if (blockEntity.getBlockState().getValue(DNLProperties.FAIRKEEPER_ALERT) == Boolean.FALSE) {
            FairkeeperSpawnerBlock.setFairkeeperAlert(blockEntity.level, blockPos, Boolean.TRUE);
            blockEntity.remainingStoredMobs = Math.min(playerCount + 1, 4);
        }
    }

    public void randomMobSummon(ServerLevel level) {
        WeightedRandomBag<SpawnerMob> mobWeightBag = new WeightedRandomBag<>();
        if (this.spawnerLevel == 0) {
            mobWeightBag.addEntry(SpawnerMob.ZOMBIE, 2);
            mobWeightBag.addEntry(SpawnerMob.SKELETON, 1);
            mobWeightBag.addEntry(SpawnerMob.SPIDER, 1);
            // Add Nanite
        } else if (this.spawnerLevel == 1) {
            mobWeightBag.addEntry(SpawnerMob.IRON_ZOMBIE, 2);
            mobWeightBag.addEntry(SpawnerMob.IRON_SKELETON, 1);
            mobWeightBag.addEntry(SpawnerMob.CAVE_SPIDER, 1);
            // Nanite
            // Torch Golem
        } else if (this.spawnerLevel == 2) {
            mobWeightBag.addEntry(SpawnerMob.DIAMOND_ZOMBIE, 2);
            mobWeightBag.addEntry(SpawnerMob.DIAMOND_SKELETON, 1);
            mobWeightBag.addEntry(SpawnerMob.CAVE_SPIDER_JOKEY, 1);
            // Nanite
            // Torch Golem
            // Ballista Golem
        }
        SpawnerMob spawnerMob = mobWeightBag.getRandom();
        EntityType<?> entityType = spawnerMob.entityType;
        Mob mob = (Mob) spawnerMob.entityType.create(level);
        if (level.getDifficulty() == Difficulty.PEACEFUL) return;
        double x = 0, y = 0, z = 0;
        for (int i = 0; i < SPAWN_POS_TRIES; i++) {
            x = (double)this.getBlockPos().getX() + (level.random.nextDouble() - level.random.nextDouble()) * (double)this.SPAWN_RANGE + 0.5;
            y = (double)(this.getBlockPos().getY() + level.random.nextInt(3) - 1);
            z = (double)this.getBlockPos().getZ() + (level.random.nextDouble() - level.random.nextDouble()) * (double)this.SPAWN_RANGE + 0.5;
            mob.moveTo(x, y, z, level.random.nextFloat() * 360.0f, 0.0f);
            //if (level.noCollision(mob, entityType.getAABB(mob.getX(), mob.getY(), mob.getZ())) && mob.checkSpawnRules(level, MobSpawnType.SPAWNER) && mob.checkSpawnObstruction(level)) break;
            if (level.noCollision(mob, entityType.getAABB(mob.getX(), mob.getY(), mob.getZ())) && mob.checkSpawnObstruction(level)) break;

        }
        summonMob(spawnerMob, mob, x, y, z, level);
        level.sendParticles(ParticleTypes.CLOUD, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), 10, 0.5D, 0.5D, 0.5D, 0.0D);
        level.sendParticles(ParticleTypes.FLAME, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), 10, 0.5D, 0.5D, 0.5D, 0.0D);
    }

    public void summonMob(SpawnerMob entity, Mob mob, double x, double y, double z, ServerLevel level) {
        BlockPos blockPos = BlockPos.containing(x, y, z);
        switch (entity) {
            case ZOMBIE -> spawnZombie(mob, x, y, z, level);
            case SKELETON -> spawnSkeleton(mob, x, y, z, level);
            case SPIDER -> spawnSpider(mob, x, y, z, level);
            case IRON_ZOMBIE -> spawnIronZombie(mob, x, y, z, level);
        }
        level.sendParticles(ParticleTypes.CLOUD, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 10, 0.5D, 0.5D, 0.5D, 0.0D);
    }

    private void spawnZombie(Mob mob, double x, double y, double z, Level level) {
        mob = SpawnMobUtil.spawnEntityWithoutMove(mob, x, y, z, level);
        ItemStack sword = new ItemStack(Items.IRON_SWORD);
        sword.enchant(Enchantments.SHARPNESS, 0);
        mob.setItemSlot(EquipmentSlot.MAINHAND, sword);
        SpawnMobUtil.equipArmor(mob, EquipmentSlot.HEAD, Items.IRON_HELMET, ArmorTrimMaterial.REDSTONE, ArmorTrimPattern.WILD);
        level.addFreshEntity(mob);
    }

    private void spawnSkeleton(Mob mob, double x, double y, double z, Level level) {
        mob = SpawnMobUtil.spawnEntityWithoutMove(mob, x, y, z, level);
        ItemStack bow = new ItemStack(Items.BOW);
        bow.enchant(Enchantments.POWER_ARROWS, 0);
        mob.setItemSlot(EquipmentSlot.MAINHAND, bow);
        SpawnMobUtil.equipArmor(mob, EquipmentSlot.HEAD, Items.IRON_HELMET, ArmorTrimMaterial.REDSTONE, ArmorTrimPattern.WILD);
        level.addFreshEntity(mob);
    }

    private void spawnSpider(Mob mob, double x, double y, double z, Level level) {
        mob = SpawnMobUtil.spawnEntityWithoutMove(mob, x, y, z, level);
        MobEffectInstance speed = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, -1, 2);
        MobEffectInstance strength = new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 0);
        mob.addEffect(speed);
        mob.addEffect(strength);
        level.addFreshEntity(mob);
    }

    private void spawnIronZombie(Mob mob, double x, double y, double z, Level level) {
        mob = SpawnMobUtil.spawnEntityWithoutMove(mob, x, y, z, level);
        SpawnMobUtil.equipArmor(mob, EquipmentSlot.HEAD, Items.IRON_HELMET, ArmorTrimMaterial.GOLD, ArmorTrimPattern.WILD);
        SpawnMobUtil.equipArmor(mob, EquipmentSlot.CHEST, Items.IRON_CHESTPLATE, ArmorTrimMaterial.GOLD, ArmorTrimPattern.WILD);
        SpawnMobUtil.equipArmor(mob, EquipmentSlot.LEGS, Items.IRON_LEGGINGS, ArmorTrimMaterial.GOLD, ArmorTrimPattern.WILD);
        SpawnMobUtil.equipArmor(mob, EquipmentSlot.FEET, Items.IRON_BOOTS, ArmorTrimMaterial.GOLD, ArmorTrimPattern.WILD);
        level.addFreshEntity(mob);
    }

    private void spawnFlameZombie(BlockPos blockPos, Level level) {
    }

    private enum SpawnerMob {
        ZOMBIE(EntityType.ZOMBIE),
        SKELETON(EntityType.SKELETON),
        SPIDER(EntityType.SPIDER),
        IRON_ZOMBIE(EntityType.ZOMBIE),
        IRON_FLAME_ZOMBIE(EntityType.ZOMBIE),
        IRON_SKELETON(EntityType.SKELETON),
        IRON_POISON_SKELETON(EntityType.SKELETON),
        IRON_FLAME_SKELETON(EntityType.SKELETON),
        CAVE_SPIDER(EntityType.CAVE_SPIDER),
        DIAMOND_ZOMBIE(EntityType.ZOMBIE),
        DIAMOND_SKELETON(EntityType.SKELETON),
        CAVE_SPIDER_JOKEY(EntityType.SPIDER);

        final EntityType<?> entityType;
        SpawnerMob(EntityType<?> entityType) {
            this.entityType = entityType;
        }
    }
}
