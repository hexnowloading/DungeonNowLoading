package dev.hexnowloading.dungeonnowloading.mixin.fabric.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.logging.Level;

@Mixin(ServerPlayerGameMode.class)
public class PlayerBreakMixin {
    /*@ModifyVariable(method = "Lnet/minecraft/server/level/ServerPlayerGameMode;destroyBlock(Lnet/minecraft/core/BlockPos;)Z", at = @At(value = "HEAD"), ordinal = 0)
    private int playerBreakEvent(Level level, GameType gameModeForPlayer, ServerPlayer serverPlayer, BlockPos blockPos) {

        return ;
    }
*/
}
