package dev.hexnowloading.dungeonnowloading.block.entity;

import dev.hexnowloading.dungeonnowloading.block.FairkeeperChestBlock;
import dev.hexnowloading.dungeonnowloading.block.FairkeeperSpawnerBlock;
import dev.hexnowloading.dungeonnowloading.platform.Services;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlockEntityTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.util.DNLMath;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.util.perf.Profiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FairkeeperChestBlockEntity extends BlockEntity {
    public static final String LOOT_TABLE_TAG = "LootTable";
    public static final String LOOT_TABLE_SEED_TAG = "LootTableSeed";
    protected ResourceLocation lootTable;
    protected long lootTableSeed;
    private List<BlockPos> spawnerLocationList;
    private BlockPos oldBlockPos;
    private int actualRegion1X;
    private int actualRegion1Y;
    private int actualRegion1Z;
    private int actualRegion2X;
    private int actualRegion2Y;
    private int actualRegion2Z;
    private BlockPos maxRegion;
    private BlockPos minRegion;
    private static final double PLAYER_RANGE = 32.0D;

    public FairkeeperChestBlockEntity(BlockPos pos, BlockState state) {
        super(DNLBlockEntityTypes.FAIRKEEPER_CHEST.get(), pos, state);
        this.maxRegion = new BlockPos(28, -1, 17);
        this.minRegion = new BlockPos(-1, 1, -17);
    }

    // Saves the nbt when player leaves the world.
    @Override
    protected void saveAdditional(CompoundTag nbt) {
        this.trySaveLootTable(nbt);
        if (this.spawnerLocationList != null) {
            ListTag listTag = new ListTag();
            this.spawnerLocationList.forEach(blockPos -> listTag.add(this.newIntList(blockPos.getX(), blockPos.getY(), blockPos.getZ())));
            nbt.put("SpawnerLocations", listTag);
        }
        nbt.put("OldBlockPos", this.newIntList(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ()));
        nbt.putInt("Region1X", this.maxRegion.getX());
        nbt.putInt("Region1Y", this.maxRegion.getY());
        nbt.putInt("Region1Z", this.maxRegion.getZ());
        nbt.putInt("Region2X", this.minRegion.getX());
        nbt.putInt("Region2Y", this.minRegion.getY());
        nbt.putInt("Region2Z", this.minRegion.getZ());
        super.saveAdditional(nbt);
    }

    private ListTag newIntList(int ... ints) {
        ListTag listTag = new ListTag();
        for (int i : ints) {
            listTag.add(IntTag.valueOf(i));
        }
        return listTag;
    }

    // Loads the nbt when player joins the world.
    @Override
    public void load(CompoundTag nbt) {
        this.tryLoadLootTable(nbt);
        if (nbt.contains("SpawnerLocations", CompoundTag.TAG_LIST)) {
            ListTag listTag = nbt.getList("SpawnerLocations", CompoundTag.TAG_LIST);
            if (this.spawnerLocationList == null) {
                this.spawnerLocationList = new ArrayList<>();
            }
            for (int a = 0; a < listTag.size(); ++a) {
                this.spawnerLocationList.add(new BlockPos(listTag.getInt(0), listTag.getInt(1), listTag.getInt(2)));
            }
        }
        this.oldBlockPos = new BlockPos(nbt.getList("OldBlockPos", CompoundTag.TAG_LIST).getInt(0), nbt.getList("OldBlockPos", CompoundTag.TAG_LIST).getInt(1), nbt.getList("OldBlockPos", CompoundTag.TAG_LIST).getInt(2));
        this.maxRegion = new BlockPos(nbt.getInt("Region1X"), nbt.getInt("Region1Y"), nbt.getInt("Region1Z"));
        this.minRegion = new BlockPos(nbt.getInt("Region2X"), nbt.getInt("Region2Y"), nbt.getInt("Region2Z"));
        super.load(nbt);
    }

    protected boolean trySaveLootTable(CompoundTag nbt) {
        if (this.lootTable == null) {
            return false;
        } else {
            nbt.putString("LootTable", this.lootTable.toString());
            if (this.lootTableSeed != 0L) {
                nbt.putLong("LootTableSeed", this.lootTableSeed);
            }
            return true;
        }
    }

    protected boolean tryLoadLootTable(CompoundTag nbt) {
        if (nbt.contains("LootTable", 8)) {
            this.lootTable = new ResourceLocation(nbt.getString("LootTable"));
            this.lootTableSeed = nbt.getLong("LootTableSeed");
            return true;
        } else {
            return false;
        }
    }

    private static void setRegion(Level level, BlockPos pos, BlockState state, FairkeeperChestBlockEntity blockEntity) {
        BlockPos actualMax = new BlockPos(
                Math.max(blockEntity.maxRegion.getX(), blockEntity.minRegion.getX()) + 1,
                Math.max(blockEntity.maxRegion.getY(), blockEntity.minRegion.getY()) + 1,
                Math.max(blockEntity.maxRegion.getZ(), blockEntity.minRegion.getZ()) + 1
        );
        BlockPos actualMin = new BlockPos(
                Math.min(blockEntity.maxRegion.getX(), blockEntity.minRegion.getX()),
                Math.min(blockEntity.maxRegion.getY(), blockEntity.minRegion.getY()),
                Math.min(blockEntity.maxRegion.getZ(), blockEntity.minRegion.getZ())
        );
        BlockPos tempRegion1;
        BlockPos tempRegion2;
        switch (state.getValue(FairkeeperChestBlock.FACING)) {
            case NORTH:
            default:
                //tempRegion1 = blockEntity.region1.rotate(Rotation.COUNTERCLOCKWISE_90);
                //tempRegion2 = blockEntity.region2.rotate(Rotation.COUNTERCLOCKWISE_90);
                tempRegion1 = DNLMath.rotateVector(actualMax, Direction.Axis.Y, Math.toRadians(90)).south();
                tempRegion2 = DNLMath.rotateVector(actualMin, Direction.Axis.Y, Math.toRadians(90)).south();
                break;
            case EAST:
                tempRegion1 = actualMax;
                tempRegion2 = actualMin;
                break;
            case SOUTH:
                //tempRegion1 = blockEntity.region1.rotate(Rotation.CLOCKWISE_90);
                //tempRegion2 = blockEntity.region2.rotate(Rotation.CLOCKWISE_90);
                tempRegion1 = DNLMath.rotateVector(actualMax, Direction.Axis.Y, Math.toRadians(-90)).east();
                tempRegion2 = DNLMath.rotateVector(actualMin, Direction.Axis.Y, Math.toRadians(-90)).east();
                break;
            case WEST:
                //tempRegion1 = blockEntity.region1.rotate(Rotation.CLOCKWISE_180);
                //tempRegion2 = blockEntity.region2.rotate(Rotation.CLOCKWISE_180);
                tempRegion1 = DNLMath.rotateVector(actualMax, Direction.Axis.Y, Math.toRadians(180)).south().east();
                tempRegion2 = DNLMath.rotateVector(actualMin, Direction.Axis.Y, Math.toRadians(180)).south().east();
                break;
        }

        int tempActualRegion1X = pos.getX() + tempRegion1.getX();
        int tempActualRegion1Y = pos.getY() + tempRegion1.getY();
        int tempActualRegion1Z = pos.getZ() + tempRegion1.getZ();
        int tempActualRegion2X = pos.getX() + tempRegion2.getX();
        int tempActualRegion2Y = pos.getY() + tempRegion2.getY();
        int tempActualRegion2Z = pos.getZ() + tempRegion2.getZ();

        blockEntity.actualRegion1X = Math.max(tempActualRegion1X, tempActualRegion2X);
        blockEntity.actualRegion1Y = Math.max(tempActualRegion1Y, tempActualRegion2Y);
        blockEntity.actualRegion1Z = Math.max(tempActualRegion1Z, tempActualRegion2Z);
        blockEntity.actualRegion2X = Math.min(tempActualRegion1X, tempActualRegion2X);
        blockEntity.actualRegion2Y = Math.min(tempActualRegion1Y, tempActualRegion2Y);
        blockEntity.actualRegion2Z = Math.min(tempActualRegion1Z, tempActualRegion2Z);
    }

    private static void updateActualRegion(Level level, BlockPos pos, BlockState state, FairkeeperChestBlockEntity blockEntity) {
        if (pos != blockEntity.oldBlockPos) {
            setRegion(level, pos, state, blockEntity);
            blockEntity.oldBlockPos = pos;
        }
    }

    private static void trigger(Level level, FairkeeperChestBlockEntity blockEntity) {

        //if (blockEntity.actualRegion1X > brokenBlockPos.getX() && blockEntity.actualRegion1Y > brokenBlockPos.getY() && blockEntity.actualRegion1Z > brokenBlockPos.getZ() && blockEntity.actualRegion2X <= brokenBlockPos.getX() && blockEntity.actualRegion2Y <= brokenBlockPos.getY() && blockEntity.actualRegion2Z <= brokenBlockPos.getZ()) {

            AABB aabb = new AABB(blockEntity.actualRegion1X, blockEntity.actualRegion1Y, blockEntity.actualRegion1Z, blockEntity.actualRegion2X, blockEntity.actualRegion2Y, blockEntity.actualRegion2Z);
            List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class, aabb);

            Map<BlockPos, BlockEntity> map = new HashMap<>();
            int chunkMinX = SectionPos.blockToSectionCoord(blockEntity.actualRegion2X);
            int chunkMinZ = SectionPos.blockToSectionCoord(blockEntity.actualRegion2Z);
            int chunkMaxX = SectionPos.blockToSectionCoord(blockEntity.actualRegion1X);
            int chunkMaxZ = SectionPos.blockToSectionCoord(blockEntity.actualRegion1Z);
            for (int x = 0; chunkMinX + x <= chunkMaxX; x++) {
                for (int z = 0; chunkMinZ + z <= chunkMaxZ; z++) {
                    map.putAll(level.getChunk(chunkMinX + x, chunkMinZ + z).getBlockEntities());
                }
            }

            Map<BlockPos, BlockEntity> filtered = map.entrySet()
                    .stream()
                    .filter(e -> e.getValue() instanceof FairkeeperSpawnerBlockEntity)
                    .filter(e -> e.getKey().getX() < blockEntity.actualRegion1X && e.getKey().getX() >= blockEntity.actualRegion2X && e.getKey().getY() < blockEntity.actualRegion1Y && e.getKey().getY() >= blockEntity.actualRegion2Y && e.getKey().getZ() < blockEntity.actualRegion1Z && e.getKey().getZ() >= blockEntity.actualRegion2Z)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            filtered.forEach(((blockPosEntry, blockEntityEntry) -> ((FairkeeperSpawnerBlockEntity) blockEntityEntry).alert(nearbyPlayers.size() == 0 ? 1 : nearbyPlayers.size(), blockPosEntry, (FairkeeperSpawnerBlockEntity) blockEntityEntry)));
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, FairkeeperChestBlockEntity blockEntity) {
        if (level.getGameTime() % 20 == 0L) {
            if (level.hasNearbyAlivePlayer(pos.getX(), pos.getY(), pos.getZ(), 32.0D)) {
                updateActualRegion(level, pos, state, blockEntity);
                AABB aabb = new AABB(blockEntity.actualRegion1X, blockEntity.actualRegion1Y, blockEntity.actualRegion1Z, blockEntity.actualRegion2X, blockEntity.actualRegion2Y, blockEntity.actualRegion2Z);
                List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class, aabb);
                nearbyPlayers.forEach(player -> Services.DATA.addFairkeeperChestPositionList(player, blockEntity.getBlockPos()));
            }
        }
        double x = blockEntity.actualRegion2X + (blockEntity.actualRegion1X - blockEntity.actualRegion2X) * level.random.nextFloat();
        double y = blockEntity.actualRegion2Y + (blockEntity.actualRegion1Y - blockEntity.actualRegion2Y) * level.random.nextFloat();
        double z = blockEntity.actualRegion2Z + (blockEntity.actualRegion1Z - blockEntity.actualRegion2Z) * level.random.nextFloat();
        level.addParticle(DustParticleOptions.REDSTONE, x, blockEntity.actualRegion1Y, blockEntity.actualRegion1Z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, x, blockEntity.actualRegion2Y, blockEntity.actualRegion1Z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, x, blockEntity.actualRegion1Y, blockEntity.actualRegion2Z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, x, blockEntity.actualRegion2Y, blockEntity.actualRegion2Z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion1X, y, blockEntity.actualRegion1Z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion2X, y, blockEntity.actualRegion1Z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion1X, y, blockEntity.actualRegion2Z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion2X, y, blockEntity.actualRegion2Z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion1X, blockEntity.actualRegion1Y, z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion2X, blockEntity.actualRegion1Y, z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion1X, blockEntity.actualRegion2Y, z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion2X, blockEntity.actualRegion2Y, z, 0, 0, 0);
    }

    public static boolean scanFairkeeperChestPositions(Level level, BlockPos fairkeeperChestPos, BlockPos brokenBlockPos) {
        if (fairkeeperChestToBrokenBlockDistance(fairkeeperChestPos, brokenBlockPos) > 32.0F) {
            return false;
        }
        BlockState fairkeeperChestState = level.getBlockState(fairkeeperChestPos);
        if (!fairkeeperChestState.is(DNLBlocks.FAIRKEEPER_CHEST.get())) {
            return false;
        }
        if (level.getBlockEntity(fairkeeperChestPos) instanceof FairkeeperChestBlockEntity fairkeeperChestBlockEntity) {
            if (fairkeeperChestBlockEntity.actualRegion1X > brokenBlockPos.getX() && fairkeeperChestBlockEntity.actualRegion1Y > brokenBlockPos.getY() && fairkeeperChestBlockEntity.actualRegion1Z > brokenBlockPos.getZ() && fairkeeperChestBlockEntity.actualRegion2X <= brokenBlockPos.getX() && fairkeeperChestBlockEntity.actualRegion2Y <= brokenBlockPos.getY() && fairkeeperChestBlockEntity.actualRegion2Z <= brokenBlockPos.getZ()) {
                trigger(level, fairkeeperChestBlockEntity);
                return false;
            }
        }
        return true;
    }

    private static float fairkeeperChestToBrokenBlockDistance(BlockPos fairkeeperChestPos, BlockPos brokenBlockPos) {
        float f = (float)(fairkeeperChestPos.getX() - brokenBlockPos.getX());
        float g = (float)(fairkeeperChestPos.getY() - brokenBlockPos.getY());
        float h = (float)(fairkeeperChestPos.getZ() - brokenBlockPos.getZ());
        return Mth.sqrt(f * f + g * g + h * h);
    }
}
