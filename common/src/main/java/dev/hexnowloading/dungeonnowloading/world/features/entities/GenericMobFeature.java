package dev.hexnowloading.dungeonnowloading.world.features.entities;

import com.mojang.serialization.Codec;
import dev.hexnowloading.dungeonnowloading.util.EntityScale;
import dev.hexnowloading.dungeonnowloading.world.features.configs.EntityTypeConfig;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class GenericMobFeature extends Feature<EntityTypeConfig> {

    public GenericMobFeature(Codec<EntityTypeConfig> codec) { super(codec); }

    @Override
    public boolean place(FeaturePlaceContext<EntityTypeConfig> context) {

        Mob mob = (Mob) context.config().entityType.create(context.level().getLevel());
        mob.setPersistenceRequired();
        mob.moveTo((double)context.origin().getX() + 0.5D, context.origin().getY(), (double)context.origin().getZ() + 0.5D, 0.0F, 0.0F);
        mob.finalizeSpawn(context.level(), context.level().getCurrentDifficultyAt(context.origin()), MobSpawnType.STRUCTURE, null, null);
        EntityScale.scaleMobAttributes(mob);

        context.level().addFreshEntity(mob);
        return true;
    }
}
