package dev.hexnowloading.dungeonnowloading.mixin;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RangedAttribute.class)
public class MaxHealthAttributeMixin {


    @Mutable
    @Shadow @Final private double maxValue;

    @Inject(method = "Lnet/minecraft/world/entity/ai/attributes/RangedAttribute;<init>(Ljava/lang/String;DDD)V", at = @At(value = "TAIL"))
    private void dungeonnowloading_RangedAttribute(String string, double d, double min, double max, CallbackInfo ci) {
        if (string.equals("attribute.name.generic.max_health")) {
            this.maxValue = 1000000.0D;
        }
    }
    /*@ModifyConstant(method = "Lnet/minecraft/world/entity/ai/attributes/Attributes;MAX_HEALTH:Lnet/minecraft/world/entity/ai/attributes/Attribute;", constant = @Constant(doubleValue = 1024.0), require = 0)
    private static double increaseMaxHealthLimit(double constant) { return 10000; }*/
}
