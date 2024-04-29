package dev.hexnowloading.dungeonnowloading.mixin.entities;

import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Debug
@Mixin(WalkNodeEvaluator.class)
public class EntityAvoidBlockMixin {

    @Inject(method = "Lnet/minecraft/world/level/pathfinder/WalkNodeEvaluator;getBlockPathTypeRaw(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/pathfinder/BlockPathTypes;", at = @At("HEAD"), cancellable = true)
    private static void dungeonnowloading_hasValidPathType(BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<BlockPathTypes> cir) {
        if (DNLBlocks.blocksRegistered) {
            BlockState blockState = blockGetter.getBlockState(blockPos);
            if (blockState.is(DNLBlocks.SPIKES.get())) {
                cir.setReturnValue(BlockPathTypes.LAVA);
            }
        }
    }

}
