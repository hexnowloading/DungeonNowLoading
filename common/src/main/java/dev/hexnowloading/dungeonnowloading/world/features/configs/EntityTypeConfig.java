package dev.hexnowloading.dungeonnowloading.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class EntityTypeConfig implements FeatureConfiguration {

    public static final Codec<EntityTypeConfig> CODEC = RecordCodecBuilder.create((configInstance) -> configInstance.group(
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(config -> config.entityType)
            ).apply(configInstance, EntityTypeConfig::new));

    public final EntityType entityType;

    public EntityTypeConfig(EntityType entityType) {
        this.entityType = entityType;
    }
}
