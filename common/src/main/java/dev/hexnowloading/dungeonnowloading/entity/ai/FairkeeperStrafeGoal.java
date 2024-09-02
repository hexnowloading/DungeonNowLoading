package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.ai.control.FairkeeperFlyingMoveControl;
import dev.hexnowloading.dungeonnowloading.entity.boss.FairkeeperEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FairkeeperStrafeGoal extends Goal {

    private final FairkeeperEntity fairkeeperEntity;
    private final FairkeeperEntity.FairkeeperState state;
    private final int rangeXZ;
    private final int rangeY;
    private final int altitude;
    private final float maxDistance;
    private final float minDistance;
    private final int maxStrafeCount;
    private final int intervals;
    private final double maxSpeed;
    private final double minSpeed;
    private final double stopAccuracy;
    private final int maxStrafeTries;
    private final StrafeReference strafeReference;
    private final boolean boundWithinArena;

    private int strafeCount;
    private int tickCount;

    public FairkeeperStrafeGoal(FairkeeperEntity fairkeeperEntity, FairkeeperEntity.FairkeeperState state, int rangeXZ, int rangeY, int altitude, int maxDistance, int minDistance, int maxStrafeCount, int intervalInTick, double maxSpeed, double minSpeed, double stopAccuracy, int maxStrafeTries, StrafeReference strafeReference, boolean boundWithinArena) {
        this.fairkeeperEntity = fairkeeperEntity;
        this.state = state;
        this.rangeXZ = rangeXZ;
        this.rangeY = rangeY;
        this.altitude = altitude;
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
        this.maxStrafeCount = maxStrafeCount;
        this.intervals = intervalInTick / 2;
        this.maxSpeed = maxSpeed;
        this.minSpeed = minSpeed;
        this.stopAccuracy = stopAccuracy;
        this.maxStrafeTries = maxStrafeTries;
        this.strafeReference = strafeReference;
        this.boundWithinArena = boundWithinArena;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.fairkeeperEntity.isState(this.state);
    }

    @Override
    public void start() {
        this.strafeCount = 0;
        this.tickCount = 0;
    }

    @Override
    public void tick() {

        if (this.strafeCount == this.maxStrafeCount && !this.fairkeeperEntity.getMoveControl().hasWanted()) {
            this.fairkeeperEntity.stopAttacking(40);
            return;
        }

        if (this.tickCount > 0) {
            if (!this.fairkeeperEntity.getMoveControl().hasWanted()) {
                this.tickCount--;
            }
            return;
        }

        this.strafeCount++;
        this.tickCount = this.intervals;

        for (int i = 0; i < this.maxStrafeTries; i++) {
            RandomSource random = this.fairkeeperEntity.getRandom();
            BlockPos relativePos = this.strafeReference == StrafeReference.ARENA ? this.fairkeeperEntity.getSpawnPoint() : this.fairkeeperEntity.blockPosition();

            double yReference = this.strafeReference == StrafeReference.SELF_EXCEPT_Y ? this.fairkeeperEntity.getSpawnPoint().getY() : relativePos.getY();

            double x = relativePos.getX() + this.rangeXZ * (random.nextFloat() - random.nextFloat());
            double y = yReference + this.altitude + this.rangeY * (random.nextFloat() - random.nextFloat());
            double z = relativePos.getZ() + this.rangeXZ * (random.nextFloat() - random.nextFloat());

            Vec3 targetPos = new Vec3(x, y, z);

            double distanceFromMob = this.fairkeeperEntity.position().distanceTo(targetPos);
            double distanceFromSpawnPoint = targetPos.distanceTo(new Vec3(this.fairkeeperEntity.getSpawnPoint().getX(), this.fairkeeperEntity.getSpawnPoint().getY(), this.fairkeeperEntity.getSpawnPoint().getZ()));

            if (this.boundWithinArena && distanceFromSpawnPoint > this.rangeXZ) continue;

            if (distanceFromMob < this.minDistance || distanceFromMob > this.maxDistance) continue;

            ((FairkeeperFlyingMoveControl) this.fairkeeperEntity.getMoveControl()).setWantedPositionWithSpeed(x, y, z, this.maxSpeed, this.minSpeed, this.stopAccuracy);
            break;
        }
    }

    public enum StrafeReference {

        SELF,
        ARENA,
        SELF_EXCEPT_Y;

        private StrafeReference() {}
    }
}
