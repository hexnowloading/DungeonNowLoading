package dev.hexnowloading.dungeonnowloading.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class EntityBodyRotationControl extends BodyRotationControl {
    public EntityBodyRotationControl(Mob mob) {
        super(mob);
    }

    @Override
    public void clientTick() {
    }
}
