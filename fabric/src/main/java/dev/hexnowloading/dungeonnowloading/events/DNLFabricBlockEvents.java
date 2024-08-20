package dev.hexnowloading.dungeonnowloading.events;

import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperChestBlockEntity;
import dev.hexnowloading.dungeonnowloading.events.callbacks.AttemptedBlockPlaceCallback;
import dev.hexnowloading.dungeonnowloading.platform.Services;
import dev.hexnowloading.dungeonnowloading.registry.DNLTags;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.intellij.lang.annotations.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DNLFabricBlockEvents {
    public static void init() {
        PlayerBlockBreakEvents.AFTER.register(DNLFabricBlockEvents::onPlayerBreakBlock);
        registerBlockPlacedCallback();
    }


    public static void onPlayerBreakBlock(Level level, Player player, BlockPos brokenBlockPos, BlockState state, BlockEntity blockEntity) {

        if (level.isClientSide) return;

        if (player.isCreative() || player.isSpectator()) return;

        Optional<List<BlockPos>> blockPosList = Services.DATA.getFairkeeperChestPositionList(player);
        blockPosList.ifPresent(pos -> Services.DATA.copyFairkeeperChestPositionList(player, pos.stream().filter(blockPos -> FairkeeperChestBlockEntity.scanFairkeeperChestPositions(level, blockPos, brokenBlockPos)).collect(Collectors.toList())));
    }

    private static void registerBlockPlacedCallback() {
        AttemptedBlockPlaceCallback.EVENT.register((context) -> {
            Level level = context.getLevel();
            Player player = context.getPlayer();
            BlockPos placeBlockPos = context.getClickedPos();
            ItemStack itemStack = context.getItemInHand();
            BlockState blockState = level.getBlockState(placeBlockPos);

            if (level.isClientSide) return InteractionResult.PASS;

            if (player.isCreative() || player.isSpectator()) return InteractionResult.PASS;

            if (level.getBlockState(placeBlockPos).is(DNLTags.FAIRKEEPER_CHEST_IGNORE)) return InteractionResult.PASS;

            Optional<List<BlockPos>> blockPosList = Services.DATA.getFairkeeperChestPositionList(player);
            blockPosList.ifPresent(pos -> Services.DATA.copyFairkeeperChestPositionList(player, pos.stream().filter(blockPos -> FairkeeperChestBlockEntity.scanFairkeeperChestPositions(level, blockPos, placeBlockPos)).collect(Collectors.toList())));

            return InteractionResult.PASS;
        });
    }
}
