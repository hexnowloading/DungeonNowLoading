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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ShieldingStonePillarProjectileEntity extends StonePillarProjectileEntity {

    public ShieldingStonePillarProjectileEntity(EntityType<? extends ShieldingStonePillarProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ShieldingStonePillarProjectileEntity(LivingEntity livingEntity, Level level, float damagePercentage, double x, double y, double z, Vec3 hoverPos, double hoverMaxSpeed, double hoverMinSpeed, double hoverStopAccuracy, boolean triggerRequiredForDrop, int hoverToDropInterval, double dropSpeed, int dropToDespawnInterval, double stonePillarImpactRange, double horizontalKnockbackStrength, double verticalKnockbackStrength, boolean shieldPenetration, float shieldDamageReduction) {
        this(DNLEntityTypes.SHIELDING_STONE_PILLAR_PROJECTILE.get(), level);
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

        //super(livingEntity, level, damagePercentage, x, y, z, hoverPos, hoverMaxSpeed, hoverMinSpeed, hoverStopAccuracy, triggerRequiredForDrop, hoverToDropInterval, dropSpeed, dropToDespawnInterval, stonePillarImpactRange, horizontalKnockbackStrength, verticalKnockbackStrength, shieldPenetration, shieldDamageReduction);
    }

    @Override
    protected Block getPillarBlock() {
        return DNLBlocks.SHIELDING_STONE_PILLAR.get();
    }

    @Override
    protected void placePillarBlock() {
        this.level().setBlock(this.blockPosition(), DNLBlocks.SHIELDING_STONE_PILLAR.get().defaultBlockState(), Block.UPDATE_ALL);
        this.level().setBlock(this.blockPosition().above(), DNLBlocks.SHIELDING_STONE_PILLAR.get().defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF ,DoubleBlockHalf.UPPER), Block.UPDATE_ALL);
    }
}
