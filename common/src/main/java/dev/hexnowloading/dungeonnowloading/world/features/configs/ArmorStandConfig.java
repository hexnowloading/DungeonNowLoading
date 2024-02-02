package dev.hexnowloading.dungeonnowloading.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.Optional;

public class ArmorStandConfig implements FeatureConfiguration {

    public static final Codec<ArmorStandConfig> CODEC = RecordCodecBuilder.create((configInstance) -> configInstance.group(
            BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("held_item").forGetter(config -> config.heldItem),
            BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("helmet").forGetter(config -> config.helmet),
            BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("chestplate").forGetter(config -> config.chestplate),
            BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("leggings").forGetter(config -> config.leggings),
            BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("boots").forGetter(config -> config.boots)
            ).apply(configInstance, ArmorStandConfig::new));

    public final Optional<Item> heldItem;
    public final Optional<Item> helmet;
    public final Optional<Item> chestplate;
    public final Optional<Item> leggings;
    public final Optional<Item> boots;

    public ArmorStandConfig(Optional<Item> heldItem, Optional<Item> helmet, Optional<Item> chestplate, Optional<Item> leggings,
                            Optional<Item> boots) {
        this.heldItem = heldItem;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }
}
