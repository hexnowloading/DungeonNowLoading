package dev.hexnowloading.dungeonnowloading.block;

import dev.hexnowloading.dungeonnowloading.block.property.RedstoneLaneMode;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RedstoneLaneBlock extends DirectionalBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<RedstoneLaneMode> REDSTONE_LANE_MODE = DNLProperties.REDSTONE_LANE_MODE;
    public static final IntegerProperty REDSTONE_LANE_POWER = DNLProperties.REDSTONE_LANE_POWER;

    public RedstoneLaneBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).setValue(REDSTONE_LANE_POWER, 0));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite()).setValue(REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).setValue(REDSTONE_LANE_POWER, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
        stateBuilder.add(REDSTONE_LANE_MODE);
        stateBuilder.add(REDSTONE_LANE_POWER);
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (level.getBlockState(blockPos).getValue(DNLProperties.REDSTONE_LANE_MODE) == RedstoneLaneMode.OVERPOWERED) {
            if (entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.hurt(level.damageSources().hotFloor(), 6.0F);
                if (!entity.fireImmune()) {
                    entity.setSecondsOnFire(5);
                }
            }
        }
        super.stepOn(level, blockPos, blockState, entity);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState oldBlockState, boolean b) {
        updatePowerStrength(blockState, level, blockPos);
        super.onPlace(blockState, level, blockPos, oldBlockState, b);
    }

    /*@Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        serverLevel.setBlock(blockPos, blockState.setValue(DNLProperties.REDSTONE_LANE_MODE, RedstoneLaneMode.POWERED), 2);
    }*/

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos neighbourBlock, boolean b) {

        if (blockState.getValue(REDSTONE_LANE_MODE) == RedstoneLaneMode.UNPOWERED) {
            if (level.getBlockState(neighbourBlock).is(Blocks.REDSTONE_BLOCK)) {
                neighbourChangedRedstoneBlock(blockState, level, blockPos, block, neighbourBlock);
            } else if (level.getBlockState(neighbourBlock).is(Blocks.MAGMA_BLOCK)) {
                neighbourChangedOverpoweredBlock(blockState, level, blockPos, block, neighbourBlock);
            } else if (
                    (
                            level.getBlockState(neighbourBlock).is(DNLBlocks.REDSTONE_LANE_I.get())
                            || level.getBlockState(neighbourBlock).is(DNLBlocks.REDSTONE_LANE_L.get())
                            || level.getBlockState(neighbourBlock).is(DNLBlocks.REDSTONE_LANE_T.get())
                    )
                    && (
                            level.getBlockState(neighbourBlock).getValue(REDSTONE_LANE_MODE) == RedstoneLaneMode.POWERED
                            || level.getBlockState(neighbourBlock).getValue(REDSTONE_LANE_MODE) == RedstoneLaneMode.OVERPOWERED
                    )
            ) {
                neighbourChangedLane(blockState, level, blockPos, block, neighbourBlock);
            }
        } else {
            updatePowerStrength(blockState, level, blockPos);
        }

    }

    private void updatePowerStrength(BlockState blockState, Level level, BlockPos blockPos) {
        List<BlockPos> neighborLaneBlockPosList = getConnectionBlockPos(blockPos, blockState);

        int power;

        boolean overpowered = false;

        boolean hasRedstoneBlock = !neighborLaneBlockPosList.stream().filter(b -> level.getBlockState(b).is(Blocks.REDSTONE_BLOCK)).toList().isEmpty();

        boolean hasMagmaBlock = !neighborLaneBlockPosList.stream().filter(b -> level.getBlockState(b).is(Blocks.MAGMA_BLOCK)).toList().isEmpty();

        if (hasRedstoneBlock || level.getBlockState(blockPos.above()).is(Blocks.REDSTONE_BLOCK)) {

            power = 15;

        } else if (hasMagmaBlock || level.getBlockState(blockPos.above()).is(Blocks.MAGMA_BLOCK)) {

            power = 15;
            overpowered = true;

        } else {

            List<BlockPos> redstoneLanePosList = neighborLaneBlockPosList.stream()
                    .filter(b -> level.getBlockState(b).getBlock() instanceof RedstoneLaneBlock)
                    .filter(b -> isLaneConnected(level, blockState, blockPos, b))
                    .toList();

            int originalPower = blockState.getValue(DNLProperties.REDSTONE_LANE_POWER);

            int highestPower = redstoneLanePosList.stream().mapToInt(b -> level.getBlockState(b).getValue(DNLProperties.REDSTONE_LANE_POWER)).max().orElse(0);

            overpowered = !redstoneLanePosList.stream().filter(b -> level.getBlockState(b).getValue(DNLProperties.REDSTONE_LANE_MODE) == RedstoneLaneMode.OVERPOWERED).toList().isEmpty();

            power = Math.max(highestPower - 1, 0);

            if (originalPower == power) return;
        }

        if (power == 0) {
            level.setBlock(blockPos, blockState.setValue(DNLProperties.REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).setValue(DNLProperties.REDSTONE_LANE_POWER, 0), 2);
        } else {
            if (overpowered) {
                level.setBlock(blockPos, blockState.setValue(DNLProperties.REDSTONE_LANE_MODE, RedstoneLaneMode.OVERPOWERED).setValue(DNLProperties.REDSTONE_LANE_POWER, power), 2);
            } else {
                level.setBlock(blockPos, blockState.setValue(DNLProperties.REDSTONE_LANE_MODE, RedstoneLaneMode.POWERED).setValue(DNLProperties.REDSTONE_LANE_POWER, power), 2);
            }
        }

        updateConnectedNegihbors(neighborLaneBlockPosList, level, blockPos);
    }

    private void neighbourChangedRedstoneBlock(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos neighbourBlock) {

        List<BlockPos> neighborBlockPosList = getConnectionBlockPos(blockPos, blockState);

        neighborBlockPosList.add(blockPos.above());

        boolean isRedstoneBlock = false;

        for (BlockPos pos : neighborBlockPosList) {
            isRedstoneBlock = pos.equals(neighbourBlock);
            if (isRedstoneBlock) break;
        }

        if (!isRedstoneBlock) return;

        level.setBlock(blockPos, blockState.setValue(DNLProperties.REDSTONE_LANE_MODE, RedstoneLaneMode.POWERED).setValue(DNLProperties.REDSTONE_LANE_POWER, 15), 2);

        poweredParticle(level, blockState, blockPos);

        updateConnectedNeighborsWithExcluded(level, blockState, blockPos, neighbourBlock);
    }

    private void neighbourChangedOverpoweredBlock(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos neighbourBlock) {

        List<BlockPos> neighborBlockPosList = getConnectionBlockPos(blockPos, blockState);

        neighborBlockPosList.add(blockPos.above());

        boolean isOverpoweredBlock = false;

        for (BlockPos pos : neighborBlockPosList) {
            isOverpoweredBlock = pos.equals(neighbourBlock);
            if (isOverpoweredBlock) break;
        }

        if (!isOverpoweredBlock) return;

        level.setBlock(blockPos, blockState.setValue(DNLProperties.REDSTONE_LANE_MODE, RedstoneLaneMode.OVERPOWERED).setValue(DNLProperties.REDSTONE_LANE_POWER, 15), 2);

        poweredParticle(level, blockState, blockPos);

        updateConnectedNeighborsWithExcluded(level, blockState, blockPos, neighbourBlock);
    }

    private void neighbourChangedLane(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos neighbourBlock) {

        if (!isLaneConnected(level, blockState, blockPos, neighbourBlock)) return;

        int power = level.getBlockState(neighbourBlock).getValue(DNLProperties.REDSTONE_LANE_POWER) - 1;

        boolean overpowered = level.getBlockState(neighbourBlock).getValue(DNLProperties.REDSTONE_LANE_MODE) == RedstoneLaneMode.OVERPOWERED;

        if (power == 0) {
            level.setBlock(blockPos, blockState.setValue(DNLProperties.REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).setValue(DNLProperties.REDSTONE_LANE_POWER, 0), 2);
        } else {
            if (overpowered) {
                level.setBlock(blockPos, blockState.setValue(DNLProperties.REDSTONE_LANE_MODE, RedstoneLaneMode.OVERPOWERED).setValue(DNLProperties.REDSTONE_LANE_POWER, power), 2);
            } else {
                level.setBlock(blockPos, blockState.setValue(DNLProperties.REDSTONE_LANE_MODE, RedstoneLaneMode.POWERED).setValue(DNLProperties.REDSTONE_LANE_POWER, power), 2);
            }
            poweredParticle(level, blockState, blockPos);
        }

        updateConnectedNeighborsWithExcluded(level, blockState, blockPos, neighbourBlock);

    }

    private void updateConnectedNegihbors(List<BlockPos> blockPosList, Level level, BlockPos blockPos) {
        for (BlockPos pos : blockPosList) {
            level.neighborChanged(pos, this, blockPos);
        }
    }

    private void updateConnectedNeighborsWithExcluded(Level level, BlockState blockState, BlockPos blockPos, BlockPos excludedBlockPos) {
        List<BlockPos> updateTargets = getConnectionBlockPos(blockPos, blockState);
        for (BlockPos pos : updateTargets) {
            if (pos.equals(excludedBlockPos)) continue;
            level.neighborChanged(pos, this, blockPos);
        }
    }

    private boolean isLaneConnected(Level level, BlockState blockState, BlockPos originalBlockPos, BlockPos neighborBlockPos) {

        List<BlockPos> originalBlockPosList = getConnectionBlockPos(originalBlockPos, blockState);

        boolean isRedstoneBlock = false;

        for (BlockPos pos : originalBlockPosList) {
            isRedstoneBlock = pos.equals(neighborBlockPos);
            if (isRedstoneBlock) break;
        }

        if (!isRedstoneBlock) return false;

        BlockState neighborBlockState = level.getBlockState(neighborBlockPos);

        List<BlockPos> neighborBlockPosList = getConnectionBlockPos(neighborBlockPos, neighborBlockState);

        for (BlockPos pos : neighborBlockPosList) {
            isRedstoneBlock = pos.equals(originalBlockPos);
            if (isRedstoneBlock) break;
        }

        return isRedstoneBlock;
    }

    private List<BlockPos> getConnectionBlockPos(BlockPos blockPos, BlockState blockState) {
        List<BlockPos> neighborBlockPosList = new ArrayList<>();
        Direction direction = blockState.getValue(FACING);

        if (blockState.is(DNLBlocks.REDSTONE_LANE_I.get())) {
            neighborBlockPosList.add(blockPos.relative(direction));
            neighborBlockPosList.add(blockPos.relative(direction.getOpposite()));
        }

        if (blockState.is(DNLBlocks.REDSTONE_LANE_L.get())) {
            neighborBlockPosList.add(blockPos.relative(direction.getCounterClockWise()));
            neighborBlockPosList.add(blockPos.relative(direction));
        }

        if (blockState.is(DNLBlocks.REDSTONE_LANE_T.get())) {
            neighborBlockPosList.add(blockPos.relative(direction.getCounterClockWise()));
            neighborBlockPosList.add(blockPos.relative(direction.getClockWise()));
            neighborBlockPosList.add(blockPos.relative(direction));
        }

        return neighborBlockPosList;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        switch (blockState.getValue(DNLProperties.REDSTONE_LANE_MODE)) {
            default:
            case UNPOWERED:
                break;
            case POWERED:
                if (randomSource.nextFloat() > 0.7F) {
                    float r = randomSource.nextFloat() - randomSource.nextFloat();
                    double x = (double)blockPos.getX() + randomSource.nextFloat();
                    double y = (double)blockPos.getY() + 1.1 + 0.1 * r;
                    double z = (double)blockPos.getZ() + randomSource.nextFloat();
                    level.addParticle(DustParticleOptions.REDSTONE, x, y, z, 0.0D, 0.0D, 0.0D);
                }
                break;
            case OVERPOWERED:
                if (randomSource.nextFloat() > 0.7F) {
                    float r = randomSource.nextFloat() - randomSource.nextFloat();
                    double x = (double)blockPos.getX() + randomSource.nextFloat();
                    double y = (double)blockPos.getY() + 1.1 + 0.1 * r;
                    double z = (double)blockPos.getZ() + randomSource.nextFloat();
                    level.addParticle(DustParticleOptions.REDSTONE, x, y, z, 0.0D, 0.0D, 0.0D);
                }
                if (randomSource.nextFloat() > 0.9F) {
                    float r = randomSource.nextFloat() - randomSource.nextFloat();
                    double x = (double)blockPos.getX() + randomSource.nextFloat();
                    double y = (double)blockPos.getY() + 1.1 + 0.1 * r;
                    double z = (double)blockPos.getZ() + randomSource.nextFloat();
                    level.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
                }
                break;
        }
        if (blockState.getValue(DNLProperties.REDSTONE_LANE_MODE) == RedstoneLaneMode.POWERED && randomSource.nextFloat() > 0.5F) {
            float r = randomSource.nextFloat() - randomSource.nextFloat();
            double x = (double)blockPos.getX() + randomSource.nextFloat();
            double y = (double)blockPos.getY() + 1.1 + 0.1 * r;
            double z = (double)blockPos.getZ() + randomSource.nextFloat();
            level.addParticle(DustParticleOptions.REDSTONE, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    private void poweredParticle(Level level, BlockState blockState, BlockPos blockPos) {
        double x = (double)blockPos.getX() + 0.5;
        double y = (double)blockPos.getY() + 1.1;
        double z = (double)blockPos.getZ() + 0.5;
        ((ServerLevel) level).sendParticles(DustParticleOptions.REDSTONE, x, y, z, 5, 0.25D, 0.1D, 0.25D, 0.0);
    }
}
