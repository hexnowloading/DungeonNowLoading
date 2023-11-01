package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.registration.RegistrationProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class DNLBlockEntityTypes {
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, DungeonNowLoading.MOD_ID);

    //public static final RegistryObject<BlockEntityType<WindAlterBlockEntity>> WIND_ALTER = BLOCK_ENTITY_TYPES.register("wind_alter", () -> BlockEntityType.Builder.of(WindAlterBlockEntity::new, SkyislandBlocks.WIND_ALTER.get()).build(null));

    public static void init() {}
}
