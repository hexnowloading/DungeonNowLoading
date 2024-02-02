package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.registration.RegistrationProvider;
import dev.hexnowloading.dungeonnowloading.registration.RegistryObject;
import dev.hexnowloading.dungeonnowloading.world.processors.WaterloggingFixProcessor;
import dev.hexnowloading.dungeonnowloading.world.processors.WeightedListProcessor;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.random.Weight;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class DNLProcessors {
    public static final RegistrationProvider<StructureProcessorType<?>> STRUCTURE_PROCESSOR = RegistrationProvider.get(Registries.STRUCTURE_PROCESSOR, DungeonNowLoading.MOD_ID);

    public static final RegistryObject<StructureProcessorType<WaterloggingFixProcessor>> WATERLOGGING_FIX_PROCESSOR = STRUCTURE_PROCESSOR.register("waterlogging_fix_processor", () -> () -> WaterloggingFixProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<WeightedListProcessor>> WEIGHTED_LIST_PROCESSOR = STRUCTURE_PROCESSOR.register("weighted_list_processor", () -> () -> WeightedListProcessor.CODEC);

    public static void init() {}
}
