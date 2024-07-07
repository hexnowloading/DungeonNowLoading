package dev.hexnowloading.dungeonnowloading.world.features.entities;

import com.mojang.serialization.Codec;
import dev.hexnowloading.dungeonnowloading.entity.monster.SpawnerCarrierEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import dev.hexnowloading.dungeonnowloading.entity.util.EntityScale;
import dev.hexnowloading.dungeonnowloading.world.features.configs.EntityTypeConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SpawnerCarrierFeature extends Feature<EntityTypeConfig> {

    public SpawnerCarrierFeature(Codec<EntityTypeConfig> codec) { super(codec); }

    @Override
    public boolean place(FeaturePlaceContext<EntityTypeConfig> context) {

        SpawnerCarrierEntity spawnerCarrier = DNLEntityTypes.SPAWNER_CARRIER.get().create(context.level().getLevel());

        spawnerCarrier.setPersistenceRequired();
        spawnerCarrier.moveTo((double)context.origin().getX() + 0.5D, context.origin().getY(), (double)context.origin().getZ() + 0.5D, 0.0F, 0.0F);
        spawnerCarrier.finalizeSpawn(context.level(), context.level().getCurrentDifficultyAt(context.origin()), MobSpawnType.STRUCTURE, null, null);
        EntityScale.scaleMobAttributes(spawnerCarrier);
        EntityType<?> entityType = context.config().entityType;
        if (EntityType.ZOMBIE.equals(entityType)) {
            spawnerCarrier.setSummonMobType("Zombie");
        } else if (EntityType.SKELETON.equals(entityType)) {
            spawnerCarrier.setSummonMobType("Skeleton");
        } else if (EntityType.SPIDER.equals(entityType)) {
            spawnerCarrier.setSummonMobType("Spider");
        }

        context.level().addFreshEntity(spawnerCarrier);
        return true;
    }
}
