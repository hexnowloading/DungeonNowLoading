package dev.hexnowloading.dungeonnowloading.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class ChaosSpawnerBodyRotationControl extends BodyRotationControl {
    public ChaosSpawnerBodyRotationControl(Mob mob) {
        super(mob);
    }

    @Override
    public void clientTick() {
    }
}
