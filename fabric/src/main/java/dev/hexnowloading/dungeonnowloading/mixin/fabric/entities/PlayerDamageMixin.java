package dev.hexnowloading.dungeonnowloading.mixin.fabric.entities;

import dev.hexnowloading.dungeonnowloading.entity.DNLEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public class PlayerDamageMixin {
    /*@Inject(method = "Lnet/minecraft/world/entity/player/Player;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setAbsorptionAmount(F)V", shift = At.Shift.AFTER))
    private void dungeonnowloading_actuallyHurt(DamageSource damageSource, float f, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        Entity attackingEntity = damageSource.getEntity();
        if (attackingEntity instanceof LivingEntity livingEntity1) {
            DNLEntityEvents.onLivingDamageEvent(livingEntity1, livingEntity, f);
        }
    }*/
    @ModifyVariable(method = "Lnet/minecraft/world/entity/player/Player;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V", shift = At.Shift.BEFORE), ordinal = 0)
    private float dungeonnowloading_onLivingDamage(float f, DamageSource damageSource) {
        LivingEntity target = (LivingEntity) (Object) this;
        Entity attackerEntity = damageSource.getEntity();
        if (attackerEntity instanceof LivingEntity attacker) {
            f = DNLEntityEvents.onLivingDamageEvent(attacker, target, f);
        }
        return f;
    }

    @ModifyVariable(method = "Lnet/minecraft/world/entity/player/Player;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInvulnerableTo(Lnet/minecraft/world/damagesource/DamageSource;)Z", shift = At.Shift.AFTER), ordinal = 0)
    private float dungeonnowloading_onLivingHurt(float f, DamageSource damageSource) {
        LivingEntity target = (LivingEntity) (Object) this;
        Entity attackerEntity = damageSource.getEntity();
        if (attackerEntity instanceof LivingEntity attacker) {
            f = DNLEntityEvents.onLivingHurtEvent(attacker, target, f);
        }
        return f;
    }
}
