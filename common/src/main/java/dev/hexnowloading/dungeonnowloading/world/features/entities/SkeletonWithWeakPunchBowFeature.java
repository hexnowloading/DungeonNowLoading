package dev.hexnowloading.dungeonnowloading.world.features.entities;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.util.EntityScale;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SkeletonWithWeakPunchBowFeature extends Feature<NoneFeatureConfiguration> {
    
    public SkeletonWithWeakPunchBowFeature() { super(NoneFeatureConfiguration.CODEC); }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        Skeleton skeleton = EntityType.SKELETON.create(context.level().getLevel());
        skeleton.setPersistenceRequired();
        skeleton.moveTo((double)context.origin().getX() + 0.5D, context.origin().getY(), (double)context.origin().getZ() + 0.5D, 0.0F, 0.0F);
        skeleton.finalizeSpawn(context.level(), context.level().getCurrentDifficultyAt(context.origin()), MobSpawnType.STRUCTURE, null, null);
        EntityScale.scaleMobAttributes(skeleton);
        skeleton.setItemSlot(EquipmentSlot.MAINHAND, weakPunchBow());
        skeleton.setItemSlot(EquipmentSlot.HEAD, trimArmor(Items.IRON_HELMET));
        skeleton.setItemSlot(EquipmentSlot.CHEST, trimArmor(Items.IRON_CHESTPLATE));
        skeleton.setItemSlot(EquipmentSlot.LEGS, trimArmor(Items.IRON_LEGGINGS));
        skeleton.setItemSlot(EquipmentSlot.FEET, trimArmor(Items.IRON_BOOTS));
        skeleton.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        skeleton.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
        skeleton.setDropChance(EquipmentSlot.HEAD, 0.0F);
        skeleton.setDropChance(EquipmentSlot.CHEST, 0.0F);
        skeleton.setDropChance(EquipmentSlot.LEGS, 0.0F);
        skeleton.setDropChance(EquipmentSlot.FEET, 0.0F);
        skeleton.setLeftHanded(context.level().getRandom().nextFloat() < 0.05F);
        skeleton.lootTable = new ResourceLocation(DungeonNowLoading.MOD_ID, "entities/modified/iron_skeleton");

        context.level().addFreshEntity(skeleton);
        
        return true;
    }

    private static ItemStack weakPunchBow() {
        ItemStack itemStack = new ItemStack(Items.BOW);
        itemStack.enchant(Enchantments.POWER_ARROWS, 3);
        itemStack.enchant(Enchantments.PUNCH_ARROWS, 1);
        return itemStack;
    }

    private static ItemStack trimArmor(Item item) {
        ItemStack itemStack = new ItemStack(item);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("material", "minecraft:netherite");
        compoundTag.putString("pattern", "minecraft:wild");
        itemStack.getOrCreateTag().put("Trim", compoundTag);
        return itemStack;
    }
}
