package dev.hexnowloading.dungeonnowloading.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import dev.hexnowloading.dungeonnowloading.entity.util.WeightedRandomBag;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.*;

public class ChaosSpawnerSummonMobGoal extends Goal {

    private final ChaosSpawnerEntity chaosSpawnerEntity;
    private int summonCount;
    private int maxSummonLimit;

    private static final ImmutableList<BlockPos> MOB_SUMMON_POS = ImmutableList.of(
            new BlockPos(5, 1, 0),
            new BlockPos(-5, 1, 0),
            new BlockPos(0, 1, 5),
            new BlockPos(0, 1, -5),
            new BlockPos(5, 1, 2),
            new BlockPos(-5, 1, 2),
            new BlockPos(2, 1, 5),
            new BlockPos(2, 1, -5),
            new BlockPos(5, 1, -2),
            new BlockPos(-5, 1, -2),
            new BlockPos(-2, 1, 5),
            new BlockPos(-2, 1, -5)
    );

    public ChaosSpawnerSummonMobGoal(ChaosSpawnerEntity chaosSpawnerEntity) {
        this.chaosSpawnerEntity = chaosSpawnerEntity;
    }

    @Override
    public boolean canUse() {
        return chaosSpawnerEntity.isAttacking(ChaosSpawnerEntity.State.SUMMON_MOB) && chaosSpawnerEntity.getTarget() != null; //&& withinSummonLimit();
    }

    @Override
    public void start() {
        super.start();
        chaosSpawnerEntity.setAttackTick(100);
    }

    @Override
    public void tick() {
        if (chaosSpawnerEntity.getAttackTick() == 100) {
            if (withinSummonLimit()) {
                summonCount = Math.max(0, maxSummonLimit);
                double d = chaosSpawnerEntity.getX();
                double e = chaosSpawnerEntity.getY();
                double f = chaosSpawnerEntity.getZ();
                ((ServerLevel) chaosSpawnerEntity.level()).sendParticles(ParticleTypes.FLAME, d, e, f, 20, 3.0D, 3.0D, 3.0D, 0.0D);
                for (int i = 0; i < summonCount; i++) {
                    WeightedRandomBag<String> mobWeightBag = new WeightedRandomBag<>();
                    if (chaosSpawnerEntity.getPhase() == 1) {
                        mobWeightBag.addEntry("Zombie", 3);
                        mobWeightBag.addEntry("Skeleton", 2);
                        mobWeightBag.addEntry("Spider", 2);
                        mobWeightBag.addEntry("Hollow", 2);
                    } else if (chaosSpawnerEntity.getPhase() == 2) {
                        mobWeightBag.addEntry("Zombie", 9);
                        mobWeightBag.addEntry("Skeleton", 6);
                        mobWeightBag.addEntry("Spider", 6);
                        mobWeightBag.addEntry("Hollow", 6);
                        mobWeightBag.addEntry("Diamond Zombie", 2);
                        mobWeightBag.addEntry("Diamond Skeleton", 2);
                        mobWeightBag.addEntry("Invisible Spider", 2);
                        mobWeightBag.addEntry("Spider Jokey", 1);
                        mobWeightBag.addEntry("Baby Zombie", 1);
                    }
                    summonMob(mobWeightBag.getRandom(), chaosSpawnerEntity.blockPosition().offset(MOB_SUMMON_POS.get(Math.min(i, MOB_SUMMON_POS.size() - 1))));
                }
            } else {
                chaosSpawnerEntity.stopAttacking();
            }
        }
        if (chaosSpawnerEntity.getAttackTick() == 0) {
            chaosSpawnerEntity.stopAttacking();
        }
    }

    private boolean withinSummonLimit() {
        List<Monster> mobList = chaosSpawnerEntity.level().getEntitiesOfClass(Monster.class, chaosSpawnerEntity.getBoundingBox().inflate(10));
        maxSummonLimit = Math.min(2 + chaosSpawnerEntity.getParticipatingPlayerCount() * 2, 12);
        return mobList.size() < maxSummonLimit;
    }

    private void summonMob(String summoningMob, BlockPos summonPos) {
        Level level = chaosSpawnerEntity.level();
        double d = (double) summonPos.getX();
        double e = (double) summonPos.getY() + 1;
        double f = (double) summonPos.getZ();
        ((ServerLevel) chaosSpawnerEntity.level()).sendParticles(ParticleTypes.CLOUD, d, e, f, 10, 0.5D, 0.5D, 0.5D, 0.0D);
        switch (summoningMob) {
            case "Zombie" -> {
                Zombie zombie = EntityType.ZOMBIE.create(level);
                if (zombie != null) {
                    zombie.moveTo(summonPos, 0.0F, 0.0F);
                    zombie.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(summonPos), MobSpawnType.MOB_SUMMONED, null, null);
                    noDropChance(zombie);
                    level.addFreshEntity(zombie);
                }
            }
            case "Skeleton" -> {
                Skeleton skeleton = EntityType.SKELETON.create(level);
                if (skeleton != null) {
                    skeleton.moveTo(summonPos, 0.0F, 0.0F);
                    skeleton.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(summonPos), MobSpawnType.MOB_SUMMONED, null, null);
                    noDropChance(skeleton);
                    level.addFreshEntity(skeleton);
                }
            }
            case "Spider" -> {
                Spider spider = EntityType.SPIDER.create(level);
                if (spider != null) {
                    spider.moveTo(summonPos, 0.0F, 0.0f);
                    spider.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(summonPos), MobSpawnType.MOB_SUMMONED, null, null);
                    level.addFreshEntity(spider);
                }
            }
            case "Hollow" -> {
                HollowEntity hollowEntity = DNLEntityTypes.HOLLOW.get().create(level);
                if (hollowEntity != null) {
                    hollowEntity.moveTo(summonPos, 0.0F, 0.0F);
                    hollowEntity.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(summonPos), MobSpawnType.MOB_SUMMONED, null, null);
                    level.addFreshEntity(hollowEntity);
                }
            }
            case "Diamond Zombie" -> {
                Zombie zombie = EntityType.ZOMBIE.create(level);
                if (zombie != null) {
                    zombie.moveTo(summonPos, 0.0F, 0.0F);
                    zombie.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(summonPos), MobSpawnType.MOB_SUMMONED, null, null);
                    zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
                    zombie.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
                    zombie.getItemBySlot(EquipmentSlot.MAINHAND).enchant(Enchantments.SHARPNESS, 2);
                    noDropChance(zombie);
                    level.addFreshEntity(zombie);
                }
            }
            case "Diamond Skeleton" -> {
                Skeleton skeleton = EntityType.SKELETON.create(level);
                if (skeleton != null) {
                    skeleton.moveTo(summonPos, 0.0F, 0.0F);
                    skeleton.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(summonPos), MobSpawnType.MOB_SUMMONED, null, null);
                    skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
                    skeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                    skeleton.getItemBySlot(EquipmentSlot.MAINHAND).enchant(Enchantments.POWER_ARROWS, 2);
                    noDropChance(skeleton);
                    level.addFreshEntity(skeleton);
                }
            }
            case "Invisible Spider" -> {
                Spider spider = EntityType.SPIDER.create(level);
                if (spider != null) {
                    spider.moveTo(summonPos, 0.0F, 0.0F);
                    spider.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(summonPos), MobSpawnType.MOB_SUMMONED, null, null);
                    spider.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, -1));
                    level.addFreshEntity(spider);
                }
            }
            case "Spider Jokey" -> {
                Spider spider = EntityType.SPIDER.create(level);
                if (spider != null) {
                    spider.moveTo(summonPos, 0.0F, 0.0F);
                    spider.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(summonPos), MobSpawnType.MOB_SUMMONED, null, null);
                    Skeleton skeleton = EntityType.SKELETON.create(level);
                    if (skeleton != null) {
                        skeleton.moveTo(summonPos, 0.0F, 0.0F);
                        skeleton.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(summonPos), MobSpawnType.MOB_SUMMONED, null, null);
                        skeleton.startRiding(spider);
                        noDropChance(skeleton);
                        level.addFreshEntity(spider);
                        level.addFreshEntity(skeleton);
                    }
                }
            }
            case "Baby Zombie" -> {
                Zombie zombie = EntityType.ZOMBIE.create(level);
                if (zombie != null) {
                    zombie.moveTo(summonPos, 0.0F, 0.0F);
                    zombie.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(summonPos), MobSpawnType.MOB_SUMMONED, null, null);
                    zombie.setBaby(true);
                    zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
                    zombie.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
                    zombie.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
                    zombie.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
                    zombie.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_AXE));
                    zombie.getItemBySlot(EquipmentSlot.MAINHAND).enchant(Enchantments.SHARPNESS, 4);
                    noDropChance(zombie);
                    level.addFreshEntity(zombie);
                }
            }
        }
    }

    private void noDropChance(Monster monster) {
        monster.setDropChance(EquipmentSlot.HEAD, 0.0F);
        monster.setDropChance(EquipmentSlot.CHEST, 0.0F);
        monster.setDropChance(EquipmentSlot.LEGS, 0.0F);
        monster.setDropChance(EquipmentSlot.FEET, 0.0F);
        monster.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        monster.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
    }
}
