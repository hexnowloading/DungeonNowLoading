package dev.hexnowloading.dungeonnowloading.entity.projectile;

import dev.hexnowloading.dungeonnowloading.entity.util.ModelledProjectileEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import dev.hexnowloading.dungeonnowloading.util.NbtHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.*;

import java.util.List;

public class StonePillarProjectileEntity extends ModelledProjectileEntity {

    protected Vec3 targetPos;
    protected double hoverX;
    protected double hoverY;
    protected double hoverZ;
    protected double maxSpeed;
    protected double minSpeed;
    protected double stopAccuracy;
    protected double dropSpeed;
    protected boolean triggerRequiredForDrop;
    protected int hoverToDropInterval;
    protected int dropToDespawnInterval;
    protected double stonePillarImpactRange;
    protected double horizontalKnockbackStrength;
    protected double verticalKnockbackStrength;
    protected boolean shieldPenetration;
    protected float shieldDamageReduction;

    protected float damage;
    protected int phase;
    protected int tickCount;
    protected double distanceToTarget;
    protected boolean triggered;

    public StonePillarProjectileEntity(EntityType<? extends StonePillarProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public StonePillarProjectileEntity(LivingEntity livingEntity, Level level, float damagePercentage, double x, double y, double z, Vec3 hoverPos, double hoverMaxSpeed, double hoverMinSpeed, double hoverStopAccuracy, boolean triggerRequiredForDrop, int hoverToDropInterval, double dropSpeed, int dropToDespawnInterval, double stonePillarImpactRange, double horizontalKnockbackStrength, double verticalKnockbackStrength, boolean shieldPenetration, float shieldDamageReduction) {
        this(DNLEntityTypes.STONE_PILLAR_PROJECTILE.get(), level);
        this.setOwner(livingEntity);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
        this.setNoGravity(true);
        this.damage = 0;
        if (this.getOwner() instanceof LivingEntity entity) {
            this.damage = (float) (damagePercentage * entity.getAttributeValue(Attributes.ATTACK_DAMAGE));
        }
        /*this.hoverX = hoverPos.x;
        this.hoverY = hoverPos.y;
        this.hoverZ = hoverPos.z;*/
        this.targetPos = hoverPos;
        this.maxSpeed = hoverMaxSpeed;
        this.minSpeed = hoverMinSpeed;
        this.stopAccuracy = hoverStopAccuracy;
        this.dropSpeed = dropSpeed;
        this.distanceToTarget = this.position().distanceTo(hoverPos);
        this.phase = 0;
        this.tickCount = 0;
        this.triggerRequiredForDrop = triggerRequiredForDrop;
        this.triggered = !triggerRequiredForDrop;
        this.dropToDespawnInterval = dropToDespawnInterval;
        this.hoverToDropInterval = hoverToDropInterval;
        this.stonePillarImpactRange = stonePillarImpactRange;
        this.horizontalKnockbackStrength = horizontalKnockbackStrength;
        this.verticalKnockbackStrength = verticalKnockbackStrength;
        this.shieldPenetration = shieldPenetration;
        this.shieldDamageReduction = shieldDamageReduction;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.phase == 0) {
                this.phase++;
            } else if (this.phase == 2) {
                if (!this.triggered) {
                    return;
                }
                if (this.tickCount > 0) {
                    this.tickCount--;
                    return;
                }
                this.tickCount = this.dropToDespawnInterval;
                this.phase++;
            } else if (this.phase == 3) {
                //this.setDeltaMovement(0, -this.dropSpeed, 0);
                if (this.tickCount > 0) {
                    this.tickCount--;
                    return;
                }
                this.phase = 5;
            } else if (this.phase == 4) {
                if (this.tickCount > 0) {
                    this.tickCount--;
                    return;
                }
                this.phase++;
            } else if (this.phase == 5) {
                this.discard();
            }
        }
    }

    @Override
    protected void tickProjectile() {
        this.checkInsideBlocks();
        if (this.phase == 1) {
            Vec3 direction = targetPos.subtract(this.position()).normalize();
            double t = 1 - this.position().distanceTo(this.targetPos) / this.distanceToTarget;
            if (t >= this.stopAccuracy || t < 0 || Double.isNaN(t)) {
                t = 1;
                this.setDeltaMovement(Vec3.ZERO);
                this.setPos(targetPos.x, targetPos.y, targetPos.z);
                this.phase++;
                this.tickCount = this.hoverToDropInterval;
            }
            double speed = this.minSpeed + (this.maxSpeed - this.minSpeed) * (1 - t * t);
            Vec3 velocity = direction.scale(speed);
            this.setDeltaMovement(velocity);
        } else if (this.phase == 3) {
            this.setDeltaMovement(0, -this.dropSpeed, 0);
        }

        Vec3 delta = this.getDeltaMovement();
        double d0 = this.getX() + delta.x;
        double d1 = this.getY() + delta.y;
        double d2 = this.getZ() + delta.z;
        this.setPos(d0, d1, d2);

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        this.targetPos = new Vec3(compoundTag.getList("HoverPos", CompoundTag.TAG_DOUBLE).getDouble(0), compoundTag.getList("HoverPos", CompoundTag.TAG_DOUBLE).getDouble(1), compoundTag.getList("HoverPos", CompoundTag.TAG_DOUBLE).getDouble(2));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.put("HoverPos", NbtHelper.newDoubleList(this.targetPos.x, this.targetPos.y, this.targetPos.z));
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (this.level().isClientSide)  {
            return;
        }
        Entity target = entityHitResult.getEntity();
        target.hurt(this.damageSources().mobProjectile(this, (LivingEntity) this.getOwner()), this.damage);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (this.level().isClientSide) {
            return;
        }
        AABB aabb = this.getBoundingBox().inflate(this.stonePillarImpactRange);
        List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, aabb);
        for (LivingEntity mob : targets) {
            this.pushNearbyMobs(mob);
        }
        if (this.level().getBlockState(this.blockPosition().below()).is(this.getPillarBlock())) {
            this.level().destroyBlock(this.blockPosition().below(), false);
            this.discard();
        } else {
            this.placePillarBlock();
        }
        this.setDeltaMovement(Vec3.ZERO);
        this.phase++;
        this.tickCount = 1;
    }

    private void pushNearbyMobs(LivingEntity mob) {
        float actualDamage = this.damage;
        if (mob instanceof Player player && this.shieldPenetration && player.isBlocking()) {
            player.disableShield(true);
            actualDamage *= 1.0F - this.shieldDamageReduction;
        }
        double x = mob.getX() - this.getX();
        double z = mob.getZ() - this.getZ();
        double a = x * x + z * z;

        mob.push(x / a * this.horizontalKnockbackStrength, this.verticalKnockbackStrength, z / a * this.horizontalKnockbackStrength);
        mob.hurt(this.damageSources().mobProjectile(this, (LivingEntity) this.getOwner()), actualDamage);
    }

    public void triggerDrop() {
        this.triggered = true;
        this.phase = 2;
    }

    protected Block getPillarBlock() {
        return DNLBlocks.STONE_PILLAR.get();
    }

    protected void placePillarBlock() {
        this.level().setBlock(this.blockPosition(), DNLBlocks.STONE_PILLAR.get().defaultBlockState(), Block.UPDATE_ALL);
        this.level().setBlock(this.blockPosition().above(), DNLBlocks.STONE_PILLAR.get().defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF ,DoubleBlockHalf.UPPER), Block.UPDATE_ALL);
    }

}
