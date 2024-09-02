package dev.hexnowloading.dungeonnowloading.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RotatorPressurePlate extends PressurePlateBlock {
    private static final VoxelShape PRESSED_AABB = Block.box(3.0, 0.0, 3.0, 13.0, 0.5, 13.0);
    private static final VoxelShape UNPRESSED_AABB = Block.box(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
    private static final net.minecraft.world.phys.AABB ROTATOR_TOUCH_AABB = new AABB(0.1875, 0.0, 0.1875, 0.8125, 0.25, 0.8125);
    private boolean hasArrow;
    private final BlockSetType type;

    public RotatorPressurePlate(Properties properties, BlockSetType blockSetType) {
        super(Sensitivity.EVERYTHING, properties, blockSetType);
        this.type = blockSetType;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return this.getSignalForState(blockState) > 0 ? PRESSED_AABB : UNPRESSED_AABB;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (blockState.getValue(POWERED)) {
            return InteractionResult.CONSUME;
        } else {
            if (!level.isClientSide) {
                this.rotateBlock((ServerLevel) level, blockPos);
            }
            BlockState poweredState = this.setSignalForState(blockState, 15);
            level.setBlock(blockPos, poweredState, Block.UPDATE_CLIENTS);
            this.updateNeighbours(level, blockPos);
            level.setBlocksDirty(blockPos, blockState, poweredState);
            level.scheduleTick(new BlockPos(blockPos), this, this.getPressedTime());
            level.playSound(null, blockPos, this.type.pressurePlateClickOn(), SoundSource.BLOCKS);
            level.gameEvent(player, GameEvent.BLOCK_ACTIVATE, blockPos);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        List<Arrow> arrows = serverLevel.getEntitiesOfClass(Arrow.class, ROTATOR_TOUCH_AABB.move(blockPos)).stream().toList();
        if (!arrows.isEmpty()) {
            arrows.forEach(arrow -> bounceArrows(serverLevel, arrow));
        }
        super.tick(blockState, serverLevel, blockPos, randomSource);
    }

    private void bounceArrows(ServerLevel level, Arrow arrow) {
        Vec3 moveVec = new Vec3(0.0D, 0.3D, 0.0D);
        arrow.move(MoverType.SELF, moveVec);

        Vec3 viewVector = arrow.getDeltaMovement();

        viewVector = viewVector.normalize().scale(5.0F);

        double d0 = viewVector.x;
        double d1 = 3.5D;
        double d2 = viewVector.z;

        Vec3 arrowVec = new Vec3(d0, d1, d2);

        arrow.setDeltaMovement(arrowVec);

        double v = arrowVec.horizontalDistance();
        arrow.setYRot((float)(Mth.atan2(arrowVec.x, arrowVec.z) * 57.2957763671875));
        arrow.setXRot((float)(Mth.atan2(arrowVec.y, v) * 57.2957763671875));
        arrow.yRotO = arrow.getYRot();
        arrow.xRotO = arrow.getXRot();
    }

    /*private void checkPressed(@Nullable Entity entity, Level level, BlockPos blockPos, BlockState blockState, int i) {
        boolean bl2;
        int j = this.getSignalStrength(level, blockPos);
        boolean bl = i > 0;
        boolean bl3 = bl2 = j > 0;
        if (i != j) {
            BlockState blockState2 = this.setSignalForState(blockState, j);
            level.setBlock(blockPos, blockState2, 2);
            this.updateNeighbours(level, blockPos);
            level.setBlocksDirty(blockPos, blockState, blockState2);
        }
        if (!bl2 && bl) {
            level.playSound(null, blockPos, this.type.pressurePlateClickOff(), SoundSource.BLOCKS);
            level.gameEvent(entity, GameEvent.BLOCK_DEACTIVATE, blockPos);
        } else if (bl2 && !bl) {
            level.playSound(null, blockPos, this.type.pressurePlateClickOn(), SoundSource.BLOCKS);
            level.gameEvent(entity, GameEvent.BLOCK_ACTIVATE, blockPos);
        }
        if (bl2) {
            level.scheduleTick(new BlockPos(blockPos), this, this.getPressedTime());
        }
    }*/


    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (!level.isClientSide) {
            boolean signalStrengthState = this.getSignalForState(blockState) > 0;
            boolean signalStrength = this.getSignalStrength(level, blockPos) > 0;
            if (signalStrength && !signalStrengthState) {
                rotateBlock((ServerLevel) level, blockPos);
            }
        }
        super.entityInside(blockState, level, blockPos, entity);
    }

    private void rotateBlock(ServerLevel serverLevel, BlockPos blockPos) {
        BlockPos rotateTargetPos = blockPos.below();
        BlockState targetBlockState = serverLevel.getBlockState(blockPos.below());
        if (serverLevel.getBlockState(rotateTargetPos).hasProperty(DirectionalBlock.FACING)) {
            Direction direction = serverLevel.getBlockState(rotateTargetPos).getValue(DirectionalBlock.FACING);
            if (direction == Direction.UP || direction == Direction.DOWN) return;
            serverLevel.setBlock(rotateTargetPos, targetBlockState.setValue(DirectionalBlock.FACING, direction.getClockWise()), 2);
        } else if (serverLevel.getBlockState(rotateTargetPos).hasProperty(HorizontalDirectionalBlock.FACING)) {
            Direction direction = serverLevel.getBlockState(rotateTargetPos).getValue(HorizontalDirectionalBlock.FACING);
            serverLevel.setBlock(rotateTargetPos, targetBlockState.setValue(HorizontalDirectionalBlock.FACING, direction.getClockWise()), 2);
        }
    }

    @Override
    protected int getSignalStrength(Level level, BlockPos blockPos) {
        return getEntityCount(level, ROTATOR_TOUCH_AABB.move(blockPos), Entity.class) > 0 ? 15 : 0;
    }

    @Override
    protected int getPressedTime() {
        return 10;
    }
}
