package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.platform.Services;
import dev.hexnowloading.dungeonnowloading.world.processors.WaterloggingFixProcessor;
import dev.hexnowloading.dungeonnowloading.world.processors.WeightedListProcessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.function.Supplier;

public class DNLProcessors {

    public static final Supplier<StructureProcessorType<WaterloggingFixProcessor>> WATERLOGGING_FIX_PROCESSOR = register("waterlogging_fix_processor", () -> () -> WaterloggingFixProcessor.CODEC);
    public static final Supplier<StructureProcessorType<WeightedListProcessor>> WEIGHTED_LIST_PROCESSOR = register("weighted_list_processor", () -> () -> WeightedListProcessor.CODEC);

    public static <T extends StructureProcessorType<?>> Supplier<T> register(String name, Supplier<T> featureSupplier) {
        return Services.REGISTRY.register(BuiltInRegistries.STRUCTURE_PROCESSOR, name, featureSupplier);
    }

    public static void init() {}
}
