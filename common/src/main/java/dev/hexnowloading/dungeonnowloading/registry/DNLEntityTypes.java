package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.dungeonnowloading.entity.misc.SpecialItemEntity;
import dev.hexnowloading.dungeonnowloading.entity.misc.GreatExperienceBottleEntity;
import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import dev.hexnowloading.dungeonnowloading.entity.monster.SpawnerCarrierEntity;
import dev.hexnowloading.dungeonnowloading.entity.passive.SealedChaosEntity;
import dev.hexnowloading.dungeonnowloading.entity.passive.WhimperEntity;
import dev.hexnowloading.dungeonnowloading.entity.projectile.ChaosSpawnerProjectileEntity;
import dev.hexnowloading.dungeonnowloading.registration.RegistrationProvider;
import dev.hexnowloading.dungeonnowloading.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.HashMap;
import java.util.Map;

public class DNLEntityTypes {
    public static final RegistrationProvider<EntityType<?>> ENTITY_TYPE = RegistrationProvider.get(Registries.ENTITY_TYPE, DungeonNowLoading.MOD_ID);

    //public static final RegistryObject<EntityType<Entity>> WINDSTONE = ENTITY_TYPE.register("windstone", () -> EntityType.Builder.of(WindstoneEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).build(new ResourceLocation(Skyisland.MOD_ID, "windstone").toString()));
    // Bosses
    public static final RegistryObject<EntityType<ChaosSpawnerEntity>> CHAOS_SPAWNER = ENTITY_TYPE.register("chaos_spawner", () -> EntityType.Builder.<ChaosSpawnerEntity>of(ChaosSpawnerEntity::new, MobCategory.MONSTER).sized(2.0F, 2.0F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "chaos_spawner").toString()));

    // Monsters
    public static final RegistryObject<EntityType<HollowEntity>> HOLLOW = ENTITY_TYPE.register("hollow", () -> EntityType.Builder.of(HollowEntity::new, MobCategory.MONSTER).sized(0.95F, 0.95F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "hollow").toString()));
    public static final RegistryObject<EntityType<SpawnerCarrierEntity>> SPAWNER_CARRIER = ENTITY_TYPE.register("spawner_carrier", () -> EntityType.Builder.of(SpawnerCarrierEntity::new, MobCategory.MONSTER).sized(1.95F, 1.95F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "spawner_carrier").toString()));

    // Passive
    public static final RegistryObject<EntityType<SealedChaosEntity>> SEALED_CHAOS = ENTITY_TYPE.register("sealed_chaos", () -> EntityType.Builder.of(SealedChaosEntity::new, MobCategory.CREATURE).sized(1F, 1F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "sealed_chaos").toString()));
    public static final RegistryObject<EntityType<WhimperEntity>> WHIMPER = ENTITY_TYPE.register("whimper", () -> EntityType.Builder.of(WhimperEntity::new, MobCategory.CREATURE).sized(0.75F, 0.75F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "whimper").toString()));

    // Projectiles
    public static final RegistryObject<EntityType<ChaosSpawnerProjectileEntity>> CHAOS_SPAWNER_PROJECTILE = ENTITY_TYPE.register("chaos_spawner_projectile", () -> EntityType.Builder.<ChaosSpawnerProjectileEntity>of(ChaosSpawnerProjectileEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "chaos_spawner_projectile").toString()));

    // Misc
    public static final RegistryObject<EntityType<SpecialItemEntity>> SPECIAL_ITEM_ENTITY = ENTITY_TYPE.register("special_item_entity", () -> EntityType.Builder.<SpecialItemEntity>of(SpecialItemEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "special_item_entity").toString()));
    public static final RegistryObject<EntityType<GreatExperienceBottleEntity>> GREAT_EXPERIENCE_BOTTLE = ENTITY_TYPE.register("great_experience_bottle", () -> EntityType.Builder.<GreatExperienceBottleEntity>of(GreatExperienceBottleEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(new ResourceLocation(DungeonNowLoading.MOD_ID, "great_experience_bottle").toString()));

    public static Map<EntityType<? extends LivingEntity>, AttributeSupplier> getAllAttributes() {
        Map<EntityType<? extends LivingEntity>, AttributeSupplier> map = new HashMap<>();

        map.put(CHAOS_SPAWNER.get(), ChaosSpawnerEntity.createAttributes().build());
        map.put(HOLLOW.get(), HollowEntity.createAttributes().build());
        map.put(SPAWNER_CARRIER.get(), SpawnerCarrierEntity.createAttributes().build());
        map.put(SEALED_CHAOS.get(), SealedChaosEntity.createAttributes().build());
        map.put(WHIMPER.get(), WhimperEntity.createAttributes().build());

        return map;
    }

    public static void init() {}
}
