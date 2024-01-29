package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.registration.RegistrationProvider;
import dev.hexnowloading.dungeonnowloading.registration.RegistryObject;
import dev.hexnowloading.dungeonnowloading.world.structures.GenericJigsawStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class DNLStructures {
    public static final RegistrationProvider<StructureType<?>> STRUCTURE_TYPE = RegistrationProvider.get(Registries.STRUCTURE_TYPE, DungeonNowLoading.MOD_ID);

    public static final RegistryObject<StructureType<GenericJigsawStructure>> GENERIC_JIGSAW_STRUCTURE = STRUCTURE_TYPE.register("generic_jigsaw_structure", () -> () -> GenericJigsawStructure.CODEC);

    public static void init() {}
}
