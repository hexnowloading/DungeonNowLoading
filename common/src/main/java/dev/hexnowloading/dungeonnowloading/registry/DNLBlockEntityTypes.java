package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.block.ScuttleStatueBlock;
import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperChestBlockEntity;
import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperSpawnerBlockEntity;
import dev.hexnowloading.dungeonnowloading.block.entity.ScuttleStatueBlockEntity;
import dev.hexnowloading.dungeonnowloading.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class DNLBlockEntityTypes {
    public static final Supplier<BlockEntityType<FairkeeperChestBlockEntity>> FAIRKEEPER_CHEST = register("fairkeeper_chest", () -> BlockEntityType.Builder.of(FairkeeperChestBlockEntity::new, DNLBlocks.FAIRKEEPER_CHEST.get()).build(null));
    public static final Supplier<BlockEntityType<FairkeeperSpawnerBlockEntity>> FAIRKEEPER_SPAWNER = register("fairkeeper_spawner", () -> BlockEntityType.Builder.of(FairkeeperSpawnerBlockEntity::new, DNLBlocks.FAIRKEEEPER_SPAWNER.get()).build(null));
    public static final Supplier<BlockEntityType<ScuttleStatueBlockEntity>> SCUTTLE_STATUE = register("scuttle_statue", () -> BlockEntityType.Builder.of(ScuttleStatueBlockEntity::new, DNLBlocks.SCUTTLE_STATUE.get()).build(null));

    //public static final RegistryObject<BlockEntityType<WindAlterBlockEntity>> WIND_ALTER = BLOCK_ENTITY_TYPES.register("wind_alter", () -> BlockEntityType.Builder.of(WindAlterBlockEntity::new, SkyislandBlocks.WIND_ALTER.get()).build(null));

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> type) {
        return Services.REGISTRY.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, name, type);
    }

    public static void init() {}
}
