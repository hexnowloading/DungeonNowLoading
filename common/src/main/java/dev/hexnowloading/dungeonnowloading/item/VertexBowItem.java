package dev.hexnowloading.dungeonnowloading.item;

import dev.hexnowloading.dungeonnowloading.entity.projectile.VertexArrowEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

import static net.minecraft.world.item.BowItem.getPowerForTime;

public class VertexBowItem extends BowItem implements Vanishable {

    private static final float CHARGE_TIME = 20.0f;

    public VertexBowItem(Properties properties) {
        super(properties);
    }

    /*public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (livingEntity instanceof Player player) {
            boolean hasAmmo = player.getInventory().contains(new ItemStack(Items.ARROW));
            int chargeDuration = this.getUseDuration(itemStack) - remainingUseDuration;
            float power = getPowerForTime(chargeDuration);

            if (power >= 0.1 && hasAmmo) {
                Arrow arrow = new Arrow(level, player);
                arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 3.0F, 1.0F);

                level.addFreshEntity(arrow);

                // Handle item durability or consumption of arrows
                itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
                player.getInventory().removeItem(new ItemStack(Items.ARROW));
            }
        }
    }*/

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (livingEntity instanceof Player player) {
            boolean b = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStack) > 0;
            ItemStack projectile = player.getProjectile(itemStack);
            if (!projectile.isEmpty() || b) {
                if (projectile.isEmpty()) {
                    projectile = new ItemStack(Items.ARROW);
                }

                int currentUseDuration = this.getUseDuration(itemStack) - remainingUseDuration;
                float powerForTime = getPowerForTime(currentUseDuration);
                if (!((double)powerForTime < 0.1)) {
                    boolean b1 = b && projectile.is(Items.ARROW);
                    if (!level.isClientSide) {
                        ArrowItem arrowItem = (ArrowItem)(projectile.getItem() instanceof ArrowItem ? projectile.getItem() : Items.ARROW);
                        AbstractArrow arrow = arrowItem.createArrow(level, projectile, player);
                        arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, powerForTime * 3.0F, 1.0F);
                        if (powerForTime == 1.0F) {
                            arrow.setCritArrow(true);
                        }

                        int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemStack);
                        if (powerLevel > 0) {
                            arrow.setBaseDamage(arrow.getBaseDamage() + (double)powerLevel * 0.5 + 0.5);
                        }

                        int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, itemStack);
                        if (punchLevel > 0) {
                            arrow.setKnockback(punchLevel);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, itemStack) > 0) {
                            arrow.setSecondsOnFire(100);
                        }

                        itemStack.hurtAndBreak(1, player, $$1x -> $$1x.broadcastBreakEvent(player.getUsedItemHand()));
                        if (b1 || player.getAbilities().instabuild && (projectile.is(Items.SPECTRAL_ARROW) || projectile.is(Items.TIPPED_ARROW))) {
                            arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        level.addFreshEntity(arrow);
                    }

                    level.playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            SoundEvents.ARROW_SHOOT,
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + powerForTime * 0.5F
                    );
                    if (!b1 && !player.getAbilities().instabuild) {
                        projectile.shrink(1);
                        if (projectile.isEmpty()) {
                            player.getInventory().removeItem(projectile);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public static float getPowerForTime(int charge) {
        float power = (float) charge / CHARGE_TIME;
        power = (power * power + power * 2.0F) / 3.0F;
        return Math.min(power, 1.0F);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        boolean b = !player.getProjectile(itemInHand).isEmpty();
        if (!player.getAbilities().instabuild && !b) {
            return InteractionResultHolder.fail(itemInHand);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemInHand);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int remainingUseDuration) {
        if (livingEntity instanceof Player player) {
            int chargeDuration = this.getUseDuration(itemStack) - remainingUseDuration;
            if (!level.isClientSide && chargeDuration == 30) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.REDSTONE_TORCH_BURNOUT, SoundSource.PLAYERS, 1.0F, 1.0F);
                ((ServerLevel) level).sendParticles(DustParticleOptions.REDSTONE, player.getX(), player.getY(), player.getZ(), 20, 0.3D, 0.9D, 0.3D, 0);
            }
            /*if (level.isClientSide()) {
                Minecraft minecraft = Minecraft.getInstance();
                Options options = minecraft.options;

                float maxDrawTime = 20.0F; // Full draw time in ticks (similar to vanilla)
                float drawRatio = Math.min(chargeDuration / maxDrawTime, 1.0F); // Get a ratio between 0 and 1
                float targetFov = Minecraft.getInstance().options.fovEffectScale().get().floatValue() / 1.5F; // Target FOV when fully drawn
                options.fov = Mth.lerp(drawRatio, options.fov, targetFov); // Smoothly transition towards target FOV
            }*/
        }
    }

    private AbstractArrow chooseArrow(ArrowItem arrowItem, int chargeTime, Player player, Level level, ItemStack projectile) {
        if (chargeTime < 30.0f) {
            return arrowItem.createArrow(level, projectile, player);
        } else {
            return (SpectralArrow) arrowItem.createArrow(level, projectile, player);
        }
    }
}
