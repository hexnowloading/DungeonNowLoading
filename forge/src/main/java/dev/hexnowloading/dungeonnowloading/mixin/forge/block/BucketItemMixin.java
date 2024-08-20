package dev.hexnowloading.dungeonnowloading.mixin.forge.block;

import com.llamalad7.mixinextras.sugar.Local;
import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperChestBlockEntity;
import dev.hexnowloading.dungeonnowloading.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {
    @Inject(method = "Lnet/minecraft/world/item/BucketItem;use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemUtils;createFilledResult(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;", shift = At.Shift.AFTER))
    private void fluidPickUp(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, @Local(ordinal = 0) BlockPos placeBlockPos) {
        if (level.isClientSide) return;

        if (player.isCreative() || player.isSpectator()) return;

        Optional<List<BlockPos>> blockPosList = Services.DATA.getFairkeeperChestPositionList(player);
        blockPosList.ifPresent(pos -> Services.DATA.copyFairkeeperChestPositionList(player, pos.stream().filter(blockPos -> FairkeeperChestBlockEntity.scanFairkeeperChestPositions(level, blockPos, placeBlockPos)).collect(Collectors.toList())));
    }

    @Inject(method = "Lnet/minecraft/world/item/BucketItem;use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BucketItem;checkExtraContent(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/core/BlockPos;)V", shift = At.Shift.AFTER))
    private void fluidPlace(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, @Local(ordinal = 2) BlockPos placeBlockPos) {
        if (level.isClientSide) return;

        if (player.isCreative() || player.isSpectator()) return;

        Optional<List<BlockPos>> blockPosList = Services.DATA.getFairkeeperChestPositionList(player);
        blockPosList.ifPresent(pos -> Services.DATA.copyFairkeeperChestPositionList(player, pos.stream().filter(blockPos -> FairkeeperChestBlockEntity.scanFairkeeperChestPositions(level, blockPos, placeBlockPos)).collect(Collectors.toList())));
    }
}
