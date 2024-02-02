package dev.hexnowloading.dungeonnowloading.world.features.entities;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.util.EntityScale;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ZombieWithGoldAxeFeature extends Feature<NoneFeatureConfiguration> {

    public ZombieWithGoldAxeFeature() { super(NoneFeatureConfiguration.CODEC); }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        Zombie zombie = EntityType.ZOMBIE.create(context.level().getLevel());
        zombie.setPersistenceRequired();
        zombie.moveTo((double)context.origin().getX() + 0.5D, context.origin().getY(), (double)context.origin().getZ() + 0.5D, 0.0F, 0.0F);
        zombie.finalizeSpawn(context.level(), context.level().getCurrentDifficultyAt(context.origin()), MobSpawnType.STRUCTURE, null, null);
        EntityScale.scaleMobAttributes(zombie);
        zombie.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3F);
        zombie.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0F);
        zombie.setItemSlot(EquipmentSlot.MAINHAND, goldenAxe());
        zombie.setItemSlot(EquipmentSlot.HEAD, trimArmor(Items.DIAMOND_HELMET));
        zombie.setItemSlot(EquipmentSlot.CHEST, trimArmor(Items.DIAMOND_CHESTPLATE));
        zombie.setItemSlot(EquipmentSlot.LEGS, trimArmor(Items.DIAMOND_LEGGINGS));
        zombie.setItemSlot(EquipmentSlot.FEET, trimArmor(Items.DIAMOND_BOOTS));
        zombie.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        zombie.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
        zombie.setDropChance(EquipmentSlot.HEAD, 0.0F);
        zombie.setDropChance(EquipmentSlot.CHEST, 0.0F);
        zombie.setDropChance(EquipmentSlot.LEGS, 0.0F);
        zombie.setDropChance(EquipmentSlot.FEET, 0.0F);
        zombie.setLeftHanded(context.level().getRandom().nextFloat() < 0.05F);
        zombie.lootTable = new ResourceLocation(DungeonNowLoading.MOD_ID, "entities/modified/diamond_zombie");

        context.level().addFreshEntityWithPassengers(zombie);
        return true;
    }

    private static ItemStack goldenAxe() {
        ItemStack itemStack = new ItemStack(Items.GOLDEN_AXE);
        itemStack.enchant(Enchantments.SHARPNESS, 5);
        itemStack.enchant(Enchantments.FIRE_ASPECT, 2);
        itemStack.enchant(Enchantments.KNOCKBACK, 5);
        return itemStack;
    }

    private static ItemStack trimArmor(Item item) {
        ItemStack itemStack = new ItemStack(item);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("material", "minecraft:gold");
        compoundTag.putString("pattern", "minecraft:wild");
        itemStack.getOrCreateTag().put("Trim", compoundTag);
        return itemStack;
    }
}
