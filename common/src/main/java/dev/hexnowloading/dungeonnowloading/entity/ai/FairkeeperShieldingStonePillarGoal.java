package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.ai.control.FairkeeperFlyingMoveControl;
import dev.hexnowloading.dungeonnowloading.entity.boss.FairkeeperEntity;
import dev.hexnowloading.dungeonnowloading.entity.projectile.ShieldingStonePillarProjectileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class FairkeeperShieldingStonePillarGoal extends Goal {

    private final FairkeeperEntity fairkeeperEntity;
    private final FairkeeperEntity.FairkeeperState state;
    private final double altitude;
    private final double strafeMaxSpeed;
    private final double strafeMinSpeed;
    private final double strafeStopAccuracy;
    private final int strafeToAttackInterval;
    private final int stonePillarCount;
    private final int stonePillarSummonInterval;
    private final double hoverMinimumDistance;
    private final int hoverRegionGridLength;
    private final int hoverRegionGridSpacing;
    private final double hoverRegionVarity;
    private final float stonePillarDamagePercentage;
    private final double hoverMaxSpeed;
    private final double hoverMinSpeed;
    private final double hoverStopAccuracy;
    private final boolean triggerRequiredForDrop;
    private final int lastPillarSummonToDropInterval;
    private final int stonePillarHoverToDropInterval;
    private final double stonePillarDropSpeed;

    List<BlockPos> hoverPos = new ArrayList<>();
    private int tickCount;
    private int phase;

    public FairkeeperShieldingStonePillarGoal(FairkeeperEntity fairkeeperEntity, FairkeeperEntity.FairkeeperState state, double altitude, double strafeMaxSpeed, double strafeMinSpeed, double strafeStopAccuracy, int strafeToAttackInterval, int stonePillarCount, int stonePillarSummonInterval, double hoverMinimumDistance, int hoverRegionGridLength, int hoverRegionGridSpacing, double hoverRegionVarity, float stonePillarDamagePercentage, double hoverMaxSpeed, double hoverMinSpeed, double hoverStopAccuracy, boolean triggerRequiredForDrop, int lastPillarSummonToDropInterval, int stonePillarHoverToDropInterval, double stonePillarDropSpeed) {
        this.fairkeeperEntity = fairkeeperEntity;
        this.state = state;
        this.altitude = altitude;
        this.strafeMaxSpeed = strafeMaxSpeed;
        this.strafeMinSpeed = strafeMinSpeed;
        this.strafeStopAccuracy = strafeStopAccuracy;
        this.strafeToAttackInterval = strafeToAttackInterval / 2;
        this.stonePillarCount = stonePillarCount;
        this.stonePillarSummonInterval = stonePillarSummonInterval / 2;
        this.hoverMinimumDistance = hoverMinimumDistance;
        this.hoverRegionGridLength = hoverRegionGridLength;
        this.hoverRegionGridSpacing = hoverRegionGridSpacing;
        this.hoverRegionVarity = hoverRegionVarity;
        this.stonePillarDamagePercentage = stonePillarDamagePercentage;
        this.hoverMaxSpeed = hoverMaxSpeed;
        this.hoverMinSpeed = hoverMinSpeed;
        this.hoverStopAccuracy = hoverStopAccuracy;
        this.triggerRequiredForDrop = triggerRequiredForDrop;
        this.lastPillarSummonToDropInterval = lastPillarSummonToDropInterval / 2;
        this.stonePillarHoverToDropInterval = stonePillarHoverToDropInterval / 2;
        this.stonePillarDropSpeed = stonePillarDropSpeed;
    }

    @Override
    public boolean canUse() {
        return this.fairkeeperEntity.isState(this.state);
    }

    @Override
    public void start() {
        this.tickCount = 0;
        this.phase = 0;
        //this.summonedStonePillarCount = 0;
        this.hoverPos = new ArrayList<>();
        int actualSpacing = this.hoverRegionGridSpacing + 1;
        int halfGridLength = this.hoverRegionGridLength / 2;
        BlockPos startPos = new BlockPos(this.fairkeeperEntity.getSpawnPoint().getX() - actualSpacing * halfGridLength, this.fairkeeperEntity.blockPosition().getY(), this.fairkeeperEntity.getSpawnPoint().getZ() - actualSpacing * halfGridLength);
        List<BlockPos> possibleHoverPos = new ArrayList<>();
        for (int x = 0; x < this.hoverRegionGridLength; x++) {
            for (int z = 0; z < this.hoverRegionGridLength; z++) {
                possibleHoverPos.add(new BlockPos(startPos.getX() + x * actualSpacing, startPos.getY(), startPos.getZ() + z * actualSpacing));
            }
        }
        for (int i = 0; i < this.stonePillarCount; i++) {
            int randomElement = this.fairkeeperEntity.getRandom().nextInt(possibleHoverPos.size());
            if (this.fairkeeperEntity.position().distanceTo(new Vec3(possibleHoverPos.get(randomElement).getX(), possibleHoverPos.get(randomElement).getY(), possibleHoverPos.get(randomElement).getZ())) > this.hoverMinimumDistance) {
                this.hoverPos.add(possibleHoverPos.get(randomElement));
            }
            possibleHoverPos.remove(randomElement);
        }
    }

    @Override
    public void tick() {
        System.out.println(this.phase);
        if (this.phase == 0) {
            ((FairkeeperFlyingMoveControl) this.fairkeeperEntity.getMoveControl()).setWantedPositionWithSpeed(this.fairkeeperEntity.getSpawnPoint().getX() + 0.5d, this.fairkeeperEntity.getSpawnPoint().getY() + this.altitude, this.fairkeeperEntity.getSpawnPoint().getZ() + 0.5d, this.strafeMaxSpeed, this.strafeMinSpeed, this.strafeStopAccuracy);
            this.phase++;
        } else if (this.phase == 1) {
            if (!this.fairkeeperEntity.getMoveControl().hasWanted()) {
                this.phase++;
                this.tickCount = this.strafeToAttackInterval;
            }
        } else if (this.phase == 2) {
            if (this.tickCount > 0) {
                this.tickCount--;
                return;
            }
            this.phase++;
        } else if (this.phase == 3) {
            if (this.hoverPos.isEmpty()) {
                this.tickCount = this.lastPillarSummonToDropInterval;
                this.phase++;
                return;
            }
            if (this.tickCount > 0) {
                this.tickCount--;
                return;
            }
            int randomElement = this.fairkeeperEntity.getRandom().nextInt(this.hoverPos.size());
            this.tickCount = this.stonePillarSummonInterval;
            double x = this.fairkeeperEntity.getX();
            double y = this.fairkeeperEntity.getY();
            double z = this.fairkeeperEntity.getZ();
            double hoverX = this.hoverPos.get(randomElement).getX() + 0.5D + (this.fairkeeperEntity.getRandom().nextInt((int) this.hoverRegionVarity)) - (this.fairkeeperEntity.getRandom().nextInt((int) this.hoverRegionVarity));
            double hoverY = this.hoverPos.get(randomElement).getY();
            double hoverZ = this.hoverPos.get(randomElement).getZ() + 0.5D + (this.fairkeeperEntity.getRandom().nextInt((int) this.hoverRegionVarity)) - (this.fairkeeperEntity.getRandom().nextInt((int) this.hoverRegionVarity));
            Vec3 hoverVec = new Vec3(hoverX, hoverY, hoverZ);

            this.hoverPos.remove(randomElement);
            ShieldingStonePillarProjectileEntity stonePillar = new ShieldingStonePillarProjectileEntity(this.fairkeeperEntity, this.fairkeeperEntity.level(), this.stonePillarDamagePercentage, x, y, z, hoverVec, this.hoverMaxSpeed, this.hoverMinSpeed, this.hoverStopAccuracy, this.triggerRequiredForDrop, this.stonePillarHoverToDropInterval, this.stonePillarDropSpeed, 1000, 3.0, 6.0, 0.2, true, 0.55f);
            stonePillar.level().addFreshEntity(stonePillar);
        } else if (this.phase == 4) {
            if (this.tickCount > 0) {
                this.tickCount--;
                return;
            }
            int actualSpacing = this.hoverRegionGridSpacing + 1;
            int halfGridLength = this.hoverRegionGridLength / 2;
            double x = this.fairkeeperEntity.getX() - actualSpacing * halfGridLength - hoverRegionVarity;
            double y = this.fairkeeperEntity.getY() - 10.0;
            double z = this.fairkeeperEntity.getZ() - actualSpacing * halfGridLength - hoverRegionVarity;
            double dx = this.fairkeeperEntity.getX() + actualSpacing * halfGridLength + hoverRegionVarity;
            double dy = this.fairkeeperEntity.getY() + 10.0;
            double dz = this.fairkeeperEntity.getZ() + actualSpacing * halfGridLength + hoverRegionVarity;
            AABB aabb = new AABB(x, y, z, dx, dy, dz);
            List<ShieldingStonePillarProjectileEntity> stonePillars = this.fairkeeperEntity.level().getEntitiesOfClass(ShieldingStonePillarProjectileEntity.class, aabb);
            for (ShieldingStonePillarProjectileEntity stonePillarProjectileEntity : stonePillars) {
                stonePillarProjectileEntity.triggerDrop();
            }
            this.fairkeeperEntity.stopAttacking(60);
        }
    }
}
