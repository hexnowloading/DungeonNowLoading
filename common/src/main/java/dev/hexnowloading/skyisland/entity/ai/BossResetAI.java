package dev.hexnowloading.skyisland.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class BossResetAI extends Goal {
    private final double range;
    LivingEntity livingEntity;
    Level level;


    public BossResetAI(LivingEntity livingEntity, double range) {
        this.livingEntity = livingEntity;
        this.range = 10.0D;
        this.level = livingEntity.level();
    }

    @Override
    public boolean canUse() {
        AABB aabb = (new AABB(this.livingEntity.blockPosition())).inflate(range);
        List<Player> list = level.getEntitiesOfClass(Player.class, aabb);
        return list.isEmpty();
    }
}
