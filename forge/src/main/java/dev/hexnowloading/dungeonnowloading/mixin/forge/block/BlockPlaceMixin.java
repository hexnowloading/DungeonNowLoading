package dev.hexnowloading.dungeonnowloading.mixin.forge.block;

import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperChestBlockEntity;
import dev.hexnowloading.dungeonnowloading.platform.Services;
import dev.hexnowloading.dungeonnowloading.registry.DNLTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Uses the mixin instead of Forge's Place Block Event, since Forge's Place Block Event triggers even on changing the state of a block, such as stripping wood logs.

@Mixin(BlockItem.class)
public abstract class BlockPlaceMixin {
    @Inject(method = "Lnet/minecraft/world/item/BlockItem;place(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/InteractionResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/world/level/gameevent/GameEvent;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/gameevent/GameEvent$Context;)V"), cancellable = true)
    private void onBlockAttemptedPlace(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir){
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos placedBlockPos = context.getClickedPos();

        if (level.isClientSide) return;

        if (player.isCreative() || player.isSpectator()) return;

        if (level.getBlockState(placedBlockPos).is(DNLTags.FAIRKEEPER_CHEST_IGNORE)) return;

        Optional<List<BlockPos>> blockPosList = Services.DATA.getFairkeeperChestPositionList(player);
        blockPosList.ifPresent(pos -> Services.DATA.copyFairkeeperChestPositionList(player, pos.stream().filter(blockPos -> FairkeeperChestBlockEntity.scanFairkeeperChestPositions(level, blockPos, placedBlockPos)).collect(Collectors.toList())));

    }
}