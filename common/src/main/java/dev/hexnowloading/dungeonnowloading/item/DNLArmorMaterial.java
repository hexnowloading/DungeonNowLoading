package dev.hexnowloading.dungeonnowloading.item;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.registry.DNLItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum DNLArmorMaterial implements ArmorMaterial {
    SPAWNER("spawner", 26, new int[]{5, 7 , 5, 4}, 25, SoundEvents.ARMOR_EQUIP_IRON, 1f, 0f, () -> Ingredient.of(DNLItems.SPAWNER_FRAME.get()));

    private String name;
    private int durability;
    private int[] damageReduction;
    private int encantability;
    private SoundEvent sound;
    private float toughness;
    private float knockbackResistance = 0.0F;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};

    DNLArmorMaterial(String name, int durability, int[] damageReduction, int enchantability, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durability = durability;
        this.damageReduction = damageReduction;
        this.repairIngredient = repairIngredient;
        this.encantability = encantability;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = 0;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durability;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.damageReduction[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.encantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return DungeonNowLoading.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
