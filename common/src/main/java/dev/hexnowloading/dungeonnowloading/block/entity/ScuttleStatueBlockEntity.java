package dev.hexnowloading.dungeonnowloading.block.entity;

import dev.hexnowloading.dungeonnowloading.entity.monster.ScuttleEntity;
import dev.hexnowloading.dungeonnowloading.entity.util.SpawnMobUtil;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlockEntityTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import java.util.Properties;

public class ScuttleStatueBlockEntity extends BlockEntity {
    public ScuttleStatueBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(DNLBlockEntityTypes.SCUTTLE_STATUE.get(), blockPos, blockState);
    }

    public void alert(BlockPos blockPos, ScuttleStatueBlockEntity scuttleStatueBlockEntity) {
        Level level = scuttleStatueBlockEntity.level;
        if (level.isClientSide) {
            return;
        }
        BlockState blockState = scuttleStatueBlockEntity.getBlockState();
        Direction direction = blockState.getValue(HorizontalDirectionalBlock.FACING);
        level.removeBlock(blockPos, false);
        level.removeBlock(blockPos.above(), false);
        float mobYRot;
        ScuttleEntity mob = DNLEntityTypes.SCUTTLE.get().create(level);
        mobYRot = switch (direction) {
            case EAST -> 270.0F;
            case SOUTH -> 0.0F;
            case WEST -> 90.0F;
            default -> 180.0F;
        };
        System.out.println(mobYRot);
        double x = blockPos.getX() + 0.5D, y = blockPos.getY(), z = blockPos.getZ() + 0.5D;
        mob = (ScuttleEntity) SpawnMobUtil.spawnEntityWithRot(mob, x, y, z, mobYRot, 0.0F, level);
        mob.setYBodyRot(mobYRot);
        mob.setYHeadRot(mobYRot);
        level.addFreshEntity(mob);

        level.playSound(null, x, y, z, SoundEvents.WITHER_SHOOT, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.2F + 0.8F);

    }
}
