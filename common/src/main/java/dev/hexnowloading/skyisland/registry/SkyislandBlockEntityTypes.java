package dev.hexnowloading.skyisland.registry;

import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.registration.RegistrationProvider;
import dev.hexnowloading.skyisland.registration.RegistryObject;
import dev.hexnowloading.skyisland.registry.SkyislandBlocks;
import dev.hexnowloading.skyisland.world.level.block.entity.WindAlterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class SkyislandBlockEntityTypes {
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, Skyisland.MOD_ID);

    //public static final RegistryObject<BlockEntityType<WindAlterBlockEntity>> WIND_ALTER = BLOCK_ENTITY_TYPES.register("wind_alter", () -> BlockEntityType.Builder.of(WindAlterBlockEntity::new, SkyislandBlocks.WIND_ALTER.get()).build(null));

    public static void init() {}
}
