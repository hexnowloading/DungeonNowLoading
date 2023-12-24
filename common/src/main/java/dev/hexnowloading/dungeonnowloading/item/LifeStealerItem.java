package dev.hexnowloading.dungeonnowloading.item;

import dev.hexnowloading.dungeonnowloading.config.GeneralConfig;
import dev.hexnowloading.dungeonnowloading.registry.DNLItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class LifeStealerItem extends SwordItem {

    public LifeStealerItem(Tier $$0, int $$1, float $$2, Properties $$3) {
        super($$0, $$1, $$2, $$3);
    }

    /*@Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity hurtedEntity, LivingEntity userEntity) {
        if (super.hurtEnemy(itemStack, hurtedEntity, userEntity)) {
            RandomSource randomSource = userEntity.getRandom();
            if (randomSource.nextFloat() < 1.0F) {
                float healAmount = 1.0F;
                userEntity.heal(healAmount);
                Level level = userEntity.level();
                if (!level.isClientSide) {
                    ((ServerLevel) level).sendParticles(ParticleTypes.ANGRY_VILLAGER, hurtedEntity.getX(), hurtedEntity.getY() + 2.0, hurtedEntity.getZ(), 1, 0.1D, 0.1D, 0.1D, 0.0D);
                }
            }
            return true;
        } else {
            return false;
        }
    }*/

    public static void healthDrain(LivingEntity hurtingEntity, float damage) {
        int healAmount = (int) Math.floor(damage * 0.2F);
        if (healAmount > 0) {
            hurtingEntity.heal(healAmount);
            Level level = hurtingEntity.level();
            level.playSound((Player) null, hurtingEntity.blockPosition(), SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 1.0F, 2.0F);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HEART, hurtingEntity.getX(), hurtingEntity.getY() +2.0, hurtingEntity.getZ(), 1, 0.1D, 0.1D, 0.1D, 0.0D);
            }
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairItem) {
        return repairItem.is(DNLItems.SPAWNER_BLADE.get());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        if (GeneralConfig.TOGGLE_HELPFUL_ITEM_TOOLTIP.get()) {
            components.add(Component.translatable("item.dungeonnowloading.life_stealer.tooltip.ability_name").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("item.dungeonnowloading.life_stealer.tooltip.ability_description").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
