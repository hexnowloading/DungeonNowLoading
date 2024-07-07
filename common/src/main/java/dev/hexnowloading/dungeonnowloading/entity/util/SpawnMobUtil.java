package dev.hexnowloading.dungeonnowloading.entity.util;

import dev.hexnowloading.dungeonnowloading.util.ArmorTrimMaterial;
import dev.hexnowloading.dungeonnowloading.util.ArmorTrimPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class SpawnMobUtil {
    public static Mob createAndSpawnEntity(EntityType<?> entityType, double x, double y, double z, Level level) {
        Mob mob = (Mob) entityType.create(level);
        spawnEntity(mob, x, y, z, level);
        return mob;
    }

    public static Mob spawnEntity(Mob mob, double x, double y, double z, Level level) {
        if (mob != null) {
            mob.moveTo(x, y, z, level.random.nextFloat() * 360.0f, 0.0F);
            EntityScale.scaleMobAttributes(mob);
            mob.setPersistenceRequired();
            BlockPos blockPos = BlockPos.containing(x, y, z);
            mob.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(blockPos), MobSpawnType.SPAWNER, null, null);
        }
        return mob;
    }

    public static Mob spawnEntityWithoutMove(Mob mob, double x, double y, double z, Level level) {
        if (mob != null) {
            EntityScale.scaleMobAttributes(mob);
            mob.setPersistenceRequired();
            BlockPos blockPos = BlockPos.containing(x, y, z);
            mob.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(blockPos), MobSpawnType.SPAWNER, null, null);
        }
        return mob;
    }

    public static void equipArmor(Mob mob, EquipmentSlot equipmentSlot, Item item, ArmorTrimMaterial trimMaterial, ArmorTrimPattern trimPattern) {
        equipArmor(mob, equipmentSlot, item, trimMaterial, trimPattern, 0.0F);
    }

    public static void equipArmor(Mob mob, EquipmentSlot equipmentSlot, Item item, ArmorTrimMaterial trimMaterial, ArmorTrimPattern trimPattern, float dropChance) {
        mob.setItemSlot(equipmentSlot, trimArmor(item, trimMaterial, trimPattern));
        mob.setDropChance(equipmentSlot, dropChance);
    }

    private static ItemStack trimArmor(Item item, ArmorTrimMaterial trimMaterial, ArmorTrimPattern trimPattern) {
        ItemStack itemStack = new ItemStack(item);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("material", "minecraft:" + trimMaterial.material);
        compoundTag.putString("pattern", "minecraft:" + trimPattern.pattern);
        itemStack.getOrCreateTag().put("Trim", compoundTag);
        return itemStack;
    }

}
