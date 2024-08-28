package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.config.BossConfig;
import dev.hexnowloading.dungeonnowloading.entity.util.Boss;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class BossResetGoal extends Goal {

    private final LivingEntity livingEntity;
    private final double range;

    public BossResetGoal(LivingEntity livingEntity, double range) {
        this.livingEntity = livingEntity;
        this.range = range;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.TARGET, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.livingEntity instanceof Boss boss && boss.resetCondition() && BossConfig.TOGGLE_BOSS_RESET.get()) {
            AABB aabb = new AABB(boss.resetRegionCenter()).inflate(range);
            List<Player> list = this.livingEntity.level().getEntitiesOfClass(Player.class, aabb);
            list.removeIf(player -> !player.isAlive());
            return list.isEmpty();
        }
        return false;
    }

    @Override
    public void start() {
        for (int i = 0; i < 50; ++i) {
            ((ServerLevel) this.livingEntity.level()).sendParticles(ParticleTypes.POOF, this.livingEntity.getRandomX(1.0D), this.livingEntity.getRandomY(), this.livingEntity.getRandomZ(1.0D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        if (this.livingEntity instanceof Boss boss) boss.resetBoss();
    }
}
