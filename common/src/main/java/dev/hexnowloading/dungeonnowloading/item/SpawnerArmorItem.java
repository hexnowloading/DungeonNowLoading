package dev.hexnowloading.dungeonnowloading.item;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.registry.DNLItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public class SpawnerArmorItem extends ArmorItem {

    private int summonTick = 100;
    private final int spawnRange = 4;

    public SpawnerArmorItem(ArmorMaterial armorMaterial, Type slot) {
        super(armorMaterial, slot, new Properties());
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide) {
            if (entity instanceof Player player && hasFullSuitOfArmorOn(player) && itemStack.is(DNLItems.SPAWNER_HELMET.get())) {
                if (summonTick > 0) {
                    summonTick--;
                } else {
                    BlockPos entityPos = player.getOnPos();
                    if (player.level().getNearestEntity(Monster.class, TargetingConditions.DEFAULT, player, entityPos.getX(), entityPos.getY(), entityPos.getZ(), player.getBoundingBox().inflate(5.0)) != null) {
                        if (hasCorrectArmorOn(player)) {
                            summonMob(level, entityPos);
                        }
                        summonTick = 100;
                    } else {
                        summonTick = 40;
                    }
                }
            }
        }
        super.inventoryTick(itemStack, level, entity, slot, selected);
    }

    /*@Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide) {
            if (entity instanceof Player player && hasFullSuitOfArmorOn(player)) {
                if (summonTick > 0) {
                    summonTick--;
                    if (summonTick % 20 == 0) {
                    }
                } else {
                    BlockPos entityPos = player.getOnPos();
                    if (player.level().getNearestEntity(Monster.class, TargetingConditions.DEFAULT, player, entityPos.getX(), entityPos.getY(), entityPos.getZ(), player.getBoundingBox().inflate(5.0)) != null) {
                        if (hasCorrectArmorOn(player)) {
                            summonMob(level, entityPos);
                        }
                        summonTick = 100;
                    } else {
                        summonTick = 40;
                    }
                }
            }
        }
        super.inventoryTick(itemStack, level, entity, slot, selected);
    }*/

    /*@Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide) {
            if (entity instanceof LivingEntity livingEntity && hasFullArmorSet(livingEntity)) {
                if (summonTick > 0) {
                    summonTick--;
                } else {
                    BlockPos entityPos = livingEntity.getOnPos();
                    if (livingEntity instanceof Player) {
                        if (livingEntity.level().getNearestEntity(Monster.class, TargetingConditions.DEFAULT, livingEntity, entityPos.getX(), entityPos.getY(), entityPos.getZ(), livingEntity.getBoundingBox().inflate(5.0)) != null) {
                            summonMob(level, entityPos);
                            summonTick = 100;
                        } else {
                            summonTick = 40;
                        }
                    } else {
                        if (livingEntity.level().getNearestPlayer(livingEntity, 5.0) != null) {
                            summonMob(level, entityPos);
                            summonTick = 100;
                        } else {
                            summonTick = 40;
                        }
                    }
                }
            }
        }
        super.inventoryTick(itemStack, level, entity, slot, selected);
    }*/

    private void summonMob(Level level, BlockPos entityPos) {
        RandomSource randomSource = level.getRandom();
        EntityType<Zombie> spawningEntity = EntityType.ZOMBIE;
        double x = entityPos.getX() + (randomSource.nextDouble() - randomSource.nextDouble()) * (double)this.spawnRange + 0.5;
        double y = entityPos.getY() + randomSource.nextInt(3) - 1;
        double z = entityPos.getZ() + (randomSource.nextDouble() - randomSource.nextDouble()) * (double)this.spawnRange + 0.5;
        if (level.noCollision(spawningEntity.getAABB(x, y, z))) {
            Zombie zombie = EntityType.ZOMBIE.create(level);
            if (zombie != null) {
                zombie.moveTo(x, y, z, 0.0F, 0.0F);
                //setOwner
                level.addFreshEntity(zombie);
            }
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !helmet.isEmpty() && !chestplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(Player player) {
        for (ItemStack armorStack: player.getInventory().armor) {
            if (!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = (ArmorItem) player.getInventory().getArmor(0).getItem();
        ArmorItem leggings = (ArmorItem) player.getInventory().getArmor(1).getItem();
        ArmorItem chestplate = (ArmorItem) player.getInventory().getArmor(2).getItem();
        ArmorItem helmet = (ArmorItem) player.getInventory().getArmor(3).getItem();

        return helmet.getMaterial() == material && chestplate.getMaterial() == material && leggings.getMaterial() == material && boots.getMaterial() == material;
    }
}
