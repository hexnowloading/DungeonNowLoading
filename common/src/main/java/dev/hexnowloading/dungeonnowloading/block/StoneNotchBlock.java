package dev.hexnowloading.dungeonnowloading.block;

import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class StoneNotchBlock extends Block {

    private final StoneNotchMaterialSignalStrength notchMaterial;

    public StoneNotchBlock(Properties properties, StoneNotchMaterialSignalStrength notchMaterial) {
        super(properties);
        this.notchMaterial = notchMaterial;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (itemInHand.is(DNLTags.STONE_NOTCH_MATERIAL)) {
            if (blockState.is(DNLBlocks.STONE_NOTCH.get())) {
                playSound(level, blockPos, SoundEvents.ITEM_FRAME_ADD_ITEM);
                setNotchBlock(itemInHand.getItem(), level, blockPos);
                player.awardStat(Stats.ITEM_USED.get(itemInHand.getItem()));
                if (!player.getAbilities().instabuild) {
                    itemInHand.shrink(1);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return popOutMaterial(level, blockState, blockPos) ? InteractionResult.sidedSuccess(level.isClientSide) : InteractionResult.PASS;
            }
        }
        if (itemInHand.isEmpty()) {
            if (blockState.is(DNLBlocks.STONE_NOTCH.get())) {
                return InteractionResult.PASS;
            }
            return popOutMaterial(level, blockState, blockPos) ? InteractionResult.sidedSuccess(level.isClientSide) : InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    private void setNotchBlock(Item item, Level level, BlockPos blockPos) {
        if (item.equals(Items.COAL)) {
            level.setBlock(blockPos, DNLBlocks.COAL_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.COPPER_INGOT)) {
            level.setBlock(blockPos, DNLBlocks.COPPER_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.IRON_INGOT)) {
            level.setBlock(blockPos, DNLBlocks.IRON_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.GOLD_INGOT)) {
            level.setBlock(blockPos, DNLBlocks.GOLD_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.REDSTONE)) {
            level.setBlock(blockPos, DNLBlocks.REDSTONE_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.AMETHYST_SHARD)) {
            level.setBlock(blockPos, DNLBlocks.AMETHYST_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.LAPIS_LAZULI)) {
            level.setBlock(blockPos, DNLBlocks.LAPIS_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.EMERALD)) {
            level.setBlock(blockPos, DNLBlocks.EMERALD_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.QUARTZ)) {
            level.setBlock(blockPos, DNLBlocks.QUARTZ_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.GLOWSTONE_DUST)) {
            level.setBlock(blockPos, DNLBlocks.GLOWSTONE_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.PRISMARINE_SHARD)) {
            level.setBlock(blockPos, DNLBlocks.PRISMARINE_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.POPPED_CHORUS_FRUIT)) {
            level.setBlock(blockPos, DNLBlocks.CHORUS_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.ECHO_SHARD)) {
            level.setBlock(blockPos, DNLBlocks.ECHO_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.DIAMOND)) {
            level.setBlock(blockPos, DNLBlocks.DIAMOND_STONE_NOTCH.get().defaultBlockState(), 3);
        } else if (item.equals(Items.NETHERITE_INGOT)) {
            level.setBlock(blockPos, DNLBlocks.NETHERITE_STONE_NOTCH.get().defaultBlockState(), 3);
        }
    }

    private boolean popOutMaterial(Level level, BlockState blockState, BlockPos blockPos) {
            List<OffsetPos> offsetPos = new ArrayList<>();

            if (!level.getBlockState(blockPos.above()).isFaceSturdy(level, blockPos.above(), Direction.UP)) { offsetPos.add(OffsetPos.UP); }
            if (!level.getBlockState(blockPos.below()).isFaceSturdy(level, blockPos.below(), Direction.DOWN)) { offsetPos.add(OffsetPos.DOWN); }
            if (!level.getBlockState(blockPos.north()).isFaceSturdy(level, blockPos.north(), Direction.NORTH)) { offsetPos.add(OffsetPos.NORTH); }
            if (!level.getBlockState(blockPos.east()).isFaceSturdy(level, blockPos.east(), Direction.EAST)) { offsetPos.add(OffsetPos.EAST); }
            if (!level.getBlockState(blockPos.south()).isFaceSturdy(level, blockPos.south(), Direction.SOUTH)) { offsetPos.add(OffsetPos.SOUTH); }
            if (!level.getBlockState(blockPos.west()).isFaceSturdy(level, blockPos.west(), Direction.WEST)) { offsetPos.add(OffsetPos.WEST); }
            /*if (Block.isFaceFull(blockState.getCollisionShape(level, blockPos.above()), Direction.UP)) { offsetPos.add(OffsetPos.UP); System.out.println("UP"); }
            if (Block.isFaceFull(blockState.getCollisionShape(level, blockPos.below()), Direction.DOWN)) { offsetPos.add(OffsetPos.DOWN); System.out.println("DOWN"); }
            if (Block.isFaceFull(blockState.getCollisionShape(level, blockPos.north()), Direction.NORTH)) { offsetPos.add(OffsetPos.NORTH); System.out.println("NORTH"); }
            if (Block.isFaceFull(blockState.getCollisionShape(level, blockPos.east()), Direction.EAST)) { offsetPos.add(OffsetPos.EAST); System.out.println("EAST"); }
            if (Block.isFaceFull(blockState.getCollisionShape(level, blockPos.south()), Direction.SOUTH)) { offsetPos.add(OffsetPos.SOUTH); System.out.println("SOUTH"); }
            if (Block.isFaceFull(blockState.getCollisionShape(level, blockPos.west()), Direction.WEST)) { offsetPos.add(OffsetPos.WEST); System.out.println("WEST"); }*/
            /*if (level.getBlockState(blockPos).isFaceSturdy(level, blockPos, Direction.UP, SupportType.FULL)) { offsetPos.add(OffsetPos.UP); }
            if (level.getBlockState(blockPos).isFaceSturdy(level, blockPos, Direction.DOWN, SupportType.FULL)) { offsetPos.add(OffsetPos.DOWN); }
            if (level.getBlockState(blockPos).isFaceSturdy(level, blockPos, Direction.NORTH, SupportType.FULL)) { offsetPos.add(OffsetPos.NORTH); }
            if (level.getBlockState(blockPos).isFaceSturdy(level, blockPos, Direction.EAST, SupportType.FULL)) { offsetPos.add(OffsetPos.EAST); }
            if (level.getBlockState(blockPos).isFaceSturdy(level, blockPos, Direction.SOUTH, SupportType.FULL)) { offsetPos.add(OffsetPos.SOUTH); }
            if (level.getBlockState(blockPos).isFaceSturdy(level, blockPos, Direction.WEST, SupportType.FULL)) { offsetPos.add(OffsetPos.WEST); }
            */if (offsetPos.isEmpty()) {
                return false;
            }
            int randomPos = level.random.nextInt(offsetPos.size());
            OffsetPos chosenPos = offsetPos.get(randomPos);
            Vec3 popPos = Vec3.atLowerCornerWithOffset(blockPos, chosenPos.x, chosenPos.y, chosenPos.z);
            if (notchMaterial.item != null) {
                playSound(level, blockPos, SoundEvents.ITEM_FRAME_REMOVE_ITEM);
                ItemStack itemStack = new ItemStack(notchMaterial.item, 1);
                ItemEntity itemEntity = new ItemEntity(level, popPos.x(), popPos.y(), popPos.z(), itemStack, chosenPos.dx, chosenPos.dy, chosenPos.dz);
                itemEntity.setDefaultPickUpDelay();
                //itemEntity.setDeltaMovement(chosenPos.dx, chosenPos.dy, chosenPos.dz);
                level.addFreshEntity(itemEntity);
            }
            level.setBlock(blockPos, DNLBlocks.STONE_NOTCH.get().defaultBlockState(), 3);

            return true;
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos1, boolean b) {
        if (!level.isClientSide) {
            if (!blockState.is(DNLBlocks.STONE_NOTCH.get()) && level.hasNeighborSignal(blockPos)) {
                popOutMaterial(level, blockState, blockPos);
            }
        }
    }

    @Override
    public boolean isSignalSource(BlockState $$0) {
        return true;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
        return notchMaterial.signalStrength;
    }

    private static void playSound(Level level, BlockPos pos, SoundEvent soundEvent) {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.5D;
        double d2 = (double)pos.getZ() + 0.5D;

        level.playSound((Player)null, d0, d1, d2, soundEvent, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    public enum StoneNotchMaterialSignalStrength {
        NONE(null, 0),
        COAL(Items.COAL, 1),
        COPPER(Items.COPPER_INGOT, 2),
        IRON(Items.IRON_INGOT, 3),
        GOLD(Items.GOLD_INGOT, 4),
        REDSTONE(Items.REDSTONE, 5),
        AMETHYST(Items.AMETHYST_SHARD, 6),
        LAPIS(Items.LAPIS_LAZULI, 7),
        EMERALD(Items.EMERALD, 8),
        QUARTZ(Items.QUARTZ, 9),
        GLOWSTONE(Items.GLOWSTONE_DUST, 10),
        PRISMARINE(Items.PRISMARINE_SHARD, 11),
        CHORUS(Items.POPPED_CHORUS_FRUIT, 12),
        ECHO(Items.ECHO_SHARD, 13),
        DIAMOND(Items.DIAMOND, 14),
        NETHERITE(Items.NETHERITE_INGOT, 15);

        final Item item;
        final int signalStrength;

        StoneNotchMaterialSignalStrength(Item item, int signalStrength) {
            this.item = item;
            this.signalStrength = signalStrength;
        }
    }

    private enum OffsetPos {
        UP(0.5, 1.01, 0.5, 0, 0.5, 0),
        DOWN(0.5, -0.4, 0.5, 0, -0.5, 0),
        NORTH(0.5, 0.5, -0.1, 0, 0, -0.5),
        EAST(1.1, 0.5, 0.5, 0.5, 0, 0),
        SOUTH(0.5, 0.5, 1.1, 0, 0, 0.5),
        WEST(-0.1, 0.5, 0.5, -0.5, 0, 0);

        final double x;
        final double y;
        final double z;
        final double dx;
        final double dy;
        final double dz;

        OffsetPos(double x, double y, double z, double dx, double dy, double dz) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
        }
    }
}
