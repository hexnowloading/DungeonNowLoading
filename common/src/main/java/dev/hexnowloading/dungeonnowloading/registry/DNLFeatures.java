package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.registration.RegistrationProvider;
import dev.hexnowloading.dungeonnowloading.registration.RegistryObject;
import dev.hexnowloading.dungeonnowloading.world.features.BrewingStandFeature;
import dev.hexnowloading.dungeonnowloading.world.features.SpawnerFeature;
import dev.hexnowloading.dungeonnowloading.world.features.configs.ArmorStandConfig;
import dev.hexnowloading.dungeonnowloading.world.features.configs.PotionConfig;
import dev.hexnowloading.dungeonnowloading.world.features.configs.EntityTypeConfig;
import dev.hexnowloading.dungeonnowloading.world.features.entities.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DNLFeatures {
    public static final RegistrationProvider<Feature<?>> FEATURE = RegistrationProvider.get(Registries.FEATURE, DungeonNowLoading.MOD_ID);

    public static final RegistryObject<Feature<EntityTypeConfig>> GENERIC_MOB = FEATURE.register("generic_mob", () -> new GenericMobFeature(EntityTypeConfig.CODEC));
    public static final RegistryObject<Feature<ArmorStandConfig>> GENERIC_ARMOR_STAND = FEATURE.register("generic_armor_stand", () -> new GenericArmorStandFeature(ArmorStandConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ARMOR_STAND_WITH_RANDOM_EQUIPMENT = FEATURE.register("armor_stand_with_random_equipments", ArmorStandWithRandomEquipmentsFeature::new);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SKELETON_CAVE_SPIDER_JOKEY = FEATURE.register("skeleton_cave_spider_jockey", SkeletonCaveSpiderJokeyFeature::new);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SKELETON_SPIDER_JOKEY = FEATURE.register("skeleton_spider_jockey", SkeletonSpiderJokeyFeature::new);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SKELETON_WITH_STRONG_FLAME_BOW = FEATURE.register("skeleton_with_strong_flame_bow", SkeletonWithStrongFlameBowFeature::new);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SKELETON_WITH_WEAK_FLAME_BOW = FEATURE.register("skeleton_with_weak_flame_bow", SkeletonWithWeakFlameBowFeature::new);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SKELETON_WITH_STRONG_PUNCH_BOW = FEATURE.register("skeleton_with_strong_punch_bow", SkeletonWithStrongPunchBowFeature::new);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SKELETON_WITH_WEAK_PUNCH_BOW = FEATURE.register("skeleton_with_weak_punch_bow", SkeletonWithWeakPunchBowFeature::new);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ZOMBIE_WITH_IRON_AXE = FEATURE.register("zombie_with_iron_axe", ZombieWithIronAxeFeature::new);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ZOMBIE_WITH_DIAMOND_AXE = FEATURE.register("zombie_with_diamond_axe", ZombieWithDiamondAxeFeature::new);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ZOMBIE_WITH_GOLD_SWORD = FEATURE.register("zombie_with_gold_sword", ZombieWithGoldSwordFeature::new);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ZOMBIE_WITH_GOLD_AXE = FEATURE.register("zombie_with_gold_axe", ZombieWithGoldAxeFeature::new);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SKELETON_HORSE = FEATURE.register("skeleton_horse", SkeletonHorseFeature::new);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ZOMBIE_HORSE = FEATURE.register("zombie_horse", ZombieHorseFeature::new);

    public static final RegistryObject<Feature<EntityTypeConfig>> SPAWNER_CARRIER = FEATURE.register("spawner_carrier", () -> new SpawnerCarrierFeature(EntityTypeConfig.CODEC));

    public static final RegistryObject<Feature<EntityTypeConfig>> SPAWNER = FEATURE.register("spawner", () -> new SpawnerFeature(EntityTypeConfig.CODEC));
    public static final RegistryObject<Feature<PotionConfig>> BREWING_STAND = FEATURE.register("brewing_stand", () -> new BrewingStandFeature(PotionConfig.CODEC));

    public static void init() {}
}
