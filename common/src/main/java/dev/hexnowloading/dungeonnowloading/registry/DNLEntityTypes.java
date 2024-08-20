package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.dungeonnowloading.entity.misc.SpecialItemEntity;
import dev.hexnowloading.dungeonnowloading.entity.misc.GreatExperienceBottleEntity;
import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import dev.hexnowloading.dungeonnowloading.entity.monster.ScuttleEntity;
import dev.hexnowloading.dungeonnowloading.entity.monster.SpawnerCarrierEntity;
import dev.hexnowloading.dungeonnowloading.entity.passive.SealedChaosEntity;
import dev.hexnowloading.dungeonnowloading.entity.passive.WhimperEntity;
import dev.hexnowloading.dungeonnowloading.entity.projectile.ChaosSpawnerProjectileEntity;
import dev.hexnowloading.dungeonnowloading.entity.projectile.FlameProjectileEntity;
import dev.hexnowloading.dungeonnowloading.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DNLEntityTypes {

    //public static final Supplier<EntityType<Entity>> WINDSTONE = register("windstone", () -> EntityType.Builder.of(WindstoneEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).build(new ResourceLocation(Skyisland.MOD_ID, "windstone").toString()));
    // Bosses
    public static final Supplier<EntityType<ChaosSpawnerEntity>> CHAOS_SPAWNER = register("chaos_spawner", () -> EntityType.Builder.<ChaosSpawnerEntity>of(ChaosSpawnerEntity::new, MobCategory.MONSTER).fireImmune().sized(3.0F, 3.0F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "chaos_spawner").toString()));

    // Monsters
    public static final Supplier<EntityType<HollowEntity>> HOLLOW = register("hollow", () -> EntityType.Builder.of(HollowEntity::new, MobCategory.MONSTER).sized(0.95F, 0.95F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "hollow").toString()));
    public static final Supplier<EntityType<SpawnerCarrierEntity>> SPAWNER_CARRIER = register("spawner_carrier", () -> EntityType.Builder.of(SpawnerCarrierEntity::new, MobCategory.MONSTER).sized(1.95F, 1.95F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "spawner_carrier").toString()));
    public static final Supplier<EntityType<ScuttleEntity>> SCUTTLE = register("scuttle", () -> EntityType.Builder.of(ScuttleEntity::new, MobCategory.MONSTER).sized(0.97F, 1.95F).fireImmune().build(new ResourceLocation(DungeonNowLoading.MOD_ID, "scuttle").toString()));

    // Passive
    public static final Supplier<EntityType<SealedChaosEntity>> SEALED_CHAOS = register("sealed_chaos", () -> EntityType.Builder.of(SealedChaosEntity::new, MobCategory.CREATURE).sized(1F, 1F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "sealed_chaos").toString()));
    public static final Supplier<EntityType<WhimperEntity>> WHIMPER = register("whimper", () -> EntityType.Builder.of(WhimperEntity::new, MobCategory.CREATURE).sized(0.75F, 0.75F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "whimper").toString()));

    // Projectiles
    public static final Supplier<EntityType<ChaosSpawnerProjectileEntity>> CHAOS_SPAWNER_PROJECTILE = register("chaos_spawner_projectile", () -> EntityType.Builder.<ChaosSpawnerProjectileEntity>of(ChaosSpawnerProjectileEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(4).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "chaos_spawner_projectile").toString()));
    public static final Supplier<EntityType<FlameProjectileEntity>> FLAME_PROJECTILE = register("flame_projectile", () -> EntityType.Builder.<FlameProjectileEntity>of(FlameProjectileEntity::new, MobCategory.MISC).sized(1.0f, 1.0f).clientTrackingRange(4).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "flame_projectile").toString()));

    // Misc
    public static final Supplier<EntityType<SpecialItemEntity>> SPECIAL_ITEM_ENTITY = register("special_item_entity", () -> EntityType.Builder.<SpecialItemEntity>of(SpecialItemEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "special_item_entity").toString()));
    public static final Supplier<EntityType<GreatExperienceBottleEntity>> GREAT_EXPERIENCE_BOTTLE = register("great_experience_bottle", () -> EntityType.Builder.<GreatExperienceBottleEntity>of(GreatExperienceBottleEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "great_experience_bottle").toString()));

    private static <T extends EntityType<?>> Supplier<T> register(String name, Supplier<T> entityTypeSupplier) {
        return Services.REGISTRY.register(BuiltInRegistries.ENTITY_TYPE, name, entityTypeSupplier);
    }
    
    public static Map<EntityType<? extends LivingEntity>, AttributeSupplier> getAllAttributes() {
        Map<EntityType<? extends LivingEntity>, AttributeSupplier> map = new HashMap<>();

        map.put(CHAOS_SPAWNER.get(), ChaosSpawnerEntity.createAttributes().build());
        map.put(HOLLOW.get(), HollowEntity.createAttributes().build());
        map.put(SPAWNER_CARRIER.get(), SpawnerCarrierEntity.createAttributes().build());
        map.put(SCUTTLE.get(), ScuttleEntity.createAttributes().build());
        map.put(SEALED_CHAOS.get(), SealedChaosEntity.createAttributes().build());
        map.put(WHIMPER.get(), WhimperEntity.createAttributes().build());

        return map;
    }

    public static void init() {}
}
