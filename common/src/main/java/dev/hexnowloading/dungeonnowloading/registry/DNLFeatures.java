package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.platform.Services;
import dev.hexnowloading.dungeonnowloading.world.features.BrewingStandFeature;
import dev.hexnowloading.dungeonnowloading.world.features.SpawnerFeature;
import dev.hexnowloading.dungeonnowloading.world.features.configs.ArmorStandConfig;
import dev.hexnowloading.dungeonnowloading.world.features.configs.PotionConfig;
import dev.hexnowloading.dungeonnowloading.world.features.configs.EntityTypeConfig;
import dev.hexnowloading.dungeonnowloading.world.features.entities.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.function.Supplier;

public class DNLFeatures {

    public static final Supplier<Feature<EntityTypeConfig>> GENERIC_MOB = register("generic_mob", () -> new GenericMobFeature(EntityTypeConfig.CODEC));
    public static final Supplier<Feature<ArmorStandConfig>> GENERIC_ARMOR_STAND = register("generic_armor_stand", () -> new GenericArmorStandFeature(ArmorStandConfig.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> ARMOR_STAND_WITH_RANDOM_EQUIPMENT = register("armor_stand_with_random_equipments", ArmorStandWithRandomEquipmentsFeature::new);
    public static final Supplier<Feature<NoneFeatureConfiguration>> SKELETON_CAVE_SPIDER_JOKEY = register("skeleton_cave_spider_jockey", SkeletonCaveSpiderJokeyFeature::new);
    public static final Supplier<Feature<NoneFeatureConfiguration>> SKELETON_SPIDER_JOKEY = register("skeleton_spider_jockey", SkeletonSpiderJokeyFeature::new);
    public static final Supplier<Feature<NoneFeatureConfiguration>> SKELETON_WITH_STRONG_FLAME_BOW = register("skeleton_with_strong_flame_bow", SkeletonWithStrongFlameBowFeature::new);
    public static final Supplier<Feature<NoneFeatureConfiguration>> SKELETON_WITH_WEAK_FLAME_BOW = register("skeleton_with_weak_flame_bow", SkeletonWithWeakFlameBowFeature::new);
    public static final Supplier<Feature<NoneFeatureConfiguration>> SKELETON_WITH_STRONG_PUNCH_BOW = register("skeleton_with_strong_punch_bow", SkeletonWithStrongPunchBowFeature::new);
    public static final Supplier<Feature<NoneFeatureConfiguration>> SKELETON_WITH_WEAK_PUNCH_BOW = register("skeleton_with_weak_punch_bow", SkeletonWithWeakPunchBowFeature::new);
    public static final Supplier<Feature<NoneFeatureConfiguration>> ZOMBIE_WITH_IRON_AXE = register("zombie_with_iron_axe", ZombieWithIronAxeFeature::new);
    public static final Supplier<Feature<NoneFeatureConfiguration>> ZOMBIE_WITH_DIAMOND_AXE = register("zombie_with_diamond_axe", ZombieWithDiamondAxeFeature::new);
    public static final Supplier<Feature<NoneFeatureConfiguration>> ZOMBIE_WITH_GOLD_SWORD = register("zombie_with_gold_sword", ZombieWithGoldSwordFeature::new);
    public static final Supplier<Feature<NoneFeatureConfiguration>> ZOMBIE_WITH_GOLD_AXE = register("zombie_with_gold_axe", ZombieWithGoldAxeFeature::new);
    public static final Supplier<Feature<NoneFeatureConfiguration>> SKELETON_HORSE = register("skeleton_horse", SkeletonHorseFeature::new);
    public static final Supplier<Feature<NoneFeatureConfiguration>> ZOMBIE_HORSE = register("zombie_horse", ZombieHorseFeature::new);

    public static final Supplier<Feature<EntityTypeConfig>> SPAWNER_CARRIER = register("spawner_carrier", () -> new SpawnerCarrierFeature(EntityTypeConfig.CODEC));

    public static final Supplier<Feature<EntityTypeConfig>> SPAWNER = register("spawner", () -> new SpawnerFeature(EntityTypeConfig.CODEC));
    public static final Supplier<Feature<PotionConfig>> BREWING_STAND = register("brewing_stand", () -> new BrewingStandFeature(PotionConfig.CODEC));

    public static <T extends Feature<?>> Supplier<T> register(String name, Supplier<T> featureSupplier) {
        return Services.REGISTRY.register(BuiltInRegistries.FEATURE, name, featureSupplier);
    }

    public static void init() {}
}
