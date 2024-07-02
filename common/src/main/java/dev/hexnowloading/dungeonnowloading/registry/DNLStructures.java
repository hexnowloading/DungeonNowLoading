package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.platform.Services;
import dev.hexnowloading.dungeonnowloading.world.structures.GenericJigsawStructure;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.function.Supplier;

public class DNLStructures {

    public static final Supplier<StructureType<GenericJigsawStructure>> GENERIC_JIGSAW_STRUCTURE = register("generic_jigsaw_structure", () -> () -> GenericJigsawStructure.CODEC);

    public static <T extends StructureType<?>> Supplier<T> register(String name, Supplier<T> featureSupplier) {
        return Services.REGISTRY.register(BuiltInRegistries.STRUCTURE_TYPE, name, featureSupplier);
    }

    public static void init() {}
}
