package dev.hexnowloading.dungeonnowloading.mixin.client;

import dev.hexnowloading.dungeonnowloading.item.VertexBowItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {
    @Inject(method = "Lnet/minecraft/client/player/AbstractClientPlayer;getFieldOfViewModifier()F", at = @At("RETURN"), cancellable = true)
    private void modifyFov(CallbackInfoReturnable<Float> cir) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;

        // Check if the player is using the custom bow
        if (player.isUsingItem()) {
            ItemStack itemStack = player.getUseItem();
            if (itemStack.getItem() instanceof VertexBowItem) {
                // Calculate FOV adjustment
                int useDuration = player.getTicksUsingItem();
                float drawTime = (float) useDuration / 20.0F;
                if (drawTime > 1.0F) {
                    drawTime = 1.0F;// Calculate draw time ratio (0.0 to 1.0)
                } else {
                    drawTime *= drawTime;
                }
                float fovModifier = 1.0F - (drawTime * 0.15F); // Reduce FOV smoothly based on draw time

                // Apply FOV change
                cir.setReturnValue(cir.getReturnValue() * fovModifier);
            }
        }
    }
}
