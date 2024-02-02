package dev.hexnowloading.dungeonnowloading.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class PotionConfig implements FeatureConfiguration {
    public static final Codec<PotionConfig> CODEC = RecordCodecBuilder.create((configInstance) -> configInstance.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("type").forGetter(config -> config.type),
            BuiltInRegistries.POTION.byNameCodec().fieldOf("potion").forGetter(config -> config.potion)
    ).apply(configInstance, PotionConfig::new));

    public final Item type;
    public final Potion potion;

    public PotionConfig(Item type, Potion potion) {
        this.type = type;
        this.potion = potion;
    }
}
