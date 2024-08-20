package dev.hexnowloading.dungeonnowloading.mixin.fabric.block;

import dev.hexnowloading.dungeonnowloading.events.callbacks.AttemptedBlockPlaceCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockPlaceMixin {
    @Inject(method = "Lnet/minecraft/world/item/BlockItem;place(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/InteractionResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/world/level/gameevent/GameEvent;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/gameevent/GameEvent$Context;)V"), cancellable = true)
    private void onBlockAttemptedPlace(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir){
        InteractionResult result = AttemptedBlockPlaceCallback.EVENT.invoker().onBlockAttemptedPlace(context);
        if (result != InteractionResult.PASS) {
            cir.setReturnValue(result);
        }
    }
}
