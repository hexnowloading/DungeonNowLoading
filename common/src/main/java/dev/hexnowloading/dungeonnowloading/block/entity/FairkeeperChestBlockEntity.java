package dev.hexnowloading.dungeonnowloading.block.entity;

import dev.hexnowloading.dungeonnowloading.block.FairkeeperChestBlock;
import dev.hexnowloading.dungeonnowloading.platform.Services;
import dev.hexnowloading.dungeonnowloading.platform.services.DataHelper;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlockEntityTypes;
import dev.hexnowloading.dungeonnowloading.util.DNLMath;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class FairkeeperChestBlockEntity extends BlockEntity {
    public static final String LOOT_TABLE_TAG = "LootTable";
    public static final String LOOT_TABLE_SEED_TAG = "LootTableSeed";
    protected ResourceLocation lootTable;
    protected long lootTableSeed;
    private int actualRegion1X;
    private int actualRegion1Y;
    private int actualRegion1Z;
    private int actualRegion2X;
    private int actualRegion2Y;
    private int actualRegion2Z;
    private BlockPos maxRegion;
    private BlockPos minRegion;
    private static final double PLAYER_RANGE = 32.0D;

    public FairkeeperChestBlockEntity(BlockPos pos, BlockState state) {
        super(DNLBlockEntityTypes.FAIRKEEPER_CHEST.get(), pos, state);
        this.maxRegion = new BlockPos(5, -1, 3);
        this.minRegion = new BlockPos(-1, 1, -1);
    }

    // Saves the nbt when player leaves the world.
    @Override
    protected void saveAdditional(CompoundTag nbt) {
        this.trySaveLootTable(nbt);
        nbt.putInt("Region1X", this.maxRegion.getX());
        nbt.putInt("Region1Y", this.maxRegion.getY());
        nbt.putInt("Region1Z", this.maxRegion.getZ());
        nbt.putInt("Region2X", this.minRegion.getX());
        nbt.putInt("Region2Y", this.minRegion.getY());
        nbt.putInt("Region2Z", this.minRegion.getZ());
        super.saveAdditional(nbt);
    }

    // Loads the nbt when player joins the world.
    @Override
    public void load(CompoundTag nbt) {
        this.tryLoadLootTable(nbt);
        this.maxRegion = new BlockPos(nbt.getInt("Region1X"), nbt.getInt("Region1Y"), nbt.getInt("Region1Z"));
        this.minRegion = new BlockPos(nbt.getInt("Region2X"), nbt.getInt("Region2Y"), nbt.getInt("Region2Z"));
        super.load(nbt);
    }

    protected boolean trySaveLootTable(CompoundTag nbt) {
        if (this.lootTable == null) {
            return false;
        } else {
            nbt.putString("LootTable", this.lootTable.toString());
            if (this.lootTableSeed != 0L) {
                nbt.putLong("LootTableSeed", this.lootTableSeed);
            }
            return true;
        }
    }

    protected boolean tryLoadLootTable(CompoundTag nbt) {
        if (nbt.contains("LootTable", 8)) {
            this.lootTable = new ResourceLocation(nbt.getString("LootTable"));
            this.lootTableSeed = nbt.getLong("LootTableSeed");
            return true;
        } else {
            return false;
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FairkeeperChestBlockEntity blockEntity) {
        if (level.hasNearbyAlivePlayer(pos.getX(), pos.getY(), pos.getZ(), PLAYER_RANGE)) {
            if (level.getGameTime() % 20 == 0L) {
                BlockPos actualMax = new BlockPos(
                        Math.max(blockEntity.maxRegion.getX(), blockEntity.minRegion.getX()) + 1,
                        Math.max(blockEntity.maxRegion.getY(), blockEntity.minRegion.getY()) + 1,
                        Math.max(blockEntity.maxRegion.getZ(), blockEntity.minRegion.getZ()) + 1
                );
                BlockPos actualMin = new BlockPos(
                        Math.min(blockEntity.maxRegion.getX(), blockEntity.minRegion.getX()),
                        Math.min(blockEntity.maxRegion.getY(), blockEntity.minRegion.getY()),
                        Math.min(blockEntity.maxRegion.getZ(), blockEntity.minRegion.getZ())
                );
                BlockPos tempRegion1;
                BlockPos tempRegion2;
                switch (state.getValue(FairkeeperChestBlock.FACING)) {
                    case NORTH:
                    default:
                        //tempRegion1 = blockEntity.region1.rotate(Rotation.COUNTERCLOCKWISE_90);
                        //tempRegion2 = blockEntity.region2.rotate(Rotation.COUNTERCLOCKWISE_90);
                        tempRegion1 = DNLMath.rotateVector(actualMax, Direction.Axis.Y, Math.toRadians(90)).south();
                        tempRegion2 = DNLMath.rotateVector(actualMin, Direction.Axis.Y, Math.toRadians(90)).south();
                        break;
                    case EAST:
                        tempRegion1 = actualMax;
                        tempRegion2 = actualMin;
                        break;
                    case SOUTH:
                        //tempRegion1 = blockEntity.region1.rotate(Rotation.CLOCKWISE_90);
                        //tempRegion2 = blockEntity.region2.rotate(Rotation.CLOCKWISE_90);
                        tempRegion1 = DNLMath.rotateVector(actualMax, Direction.Axis.Y, Math.toRadians(-90)).east();
                        tempRegion2 = DNLMath.rotateVector(actualMin, Direction.Axis.Y, Math.toRadians(-90)).east();
                        break;
                    case WEST:
                        //tempRegion1 = blockEntity.region1.rotate(Rotation.CLOCKWISE_180);
                        //tempRegion2 = blockEntity.region2.rotate(Rotation.CLOCKWISE_180);
                        tempRegion1 = DNLMath.rotateVector(actualMax, Direction.Axis.Y, Math.toRadians(180)).south().east();
                        tempRegion2 = DNLMath.rotateVector(actualMin, Direction.Axis.Y, Math.toRadians(180)).south().east();
                        break;
                }


                blockEntity.actualRegion1X = pos.getX() + tempRegion1.getX();
                blockEntity.actualRegion1Y = pos.getY() + tempRegion1.getY();
                blockEntity.actualRegion1Z = pos.getZ() + tempRegion1.getZ();
                blockEntity.actualRegion2X = pos.getX() + tempRegion2.getX();
                blockEntity.actualRegion2Y = pos.getY() + tempRegion2.getY();
                blockEntity.actualRegion2Z = pos.getZ() + tempRegion2.getZ();

                //System.out.print("[" + ax + ", " + ay + ", " + az + ", " + bx + ", " + by + ", " + bz + "] ");
                AABB aabb = new AABB(blockEntity.actualRegion1X, blockEntity.actualRegion1Y, blockEntity.actualRegion1Z, blockEntity.actualRegion2X, blockEntity.actualRegion2Y, blockEntity.actualRegion2Z);
                //System.out.println("[" + aabb.minX + ", " + aabb.minY + ", " + aabb.minZ + ", " + aabb.maxX + ", " + aabb.maxY + ", " + aabb.maxZ + "]");
                //AABB aabb = new AABB(pos).inflate(PLAYER_RANGE);
                List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class, aabb);
                for (Player player : nearbyPlayers) {
                    System.out.println("in");
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, 3, true, true, true));
                    Services.DATA.setPoint(player, 10);
                    System.out.println(Services.DATA.getPoint(player));
                }
            }
            double x = blockEntity.actualRegion2X + (blockEntity.actualRegion1X - blockEntity.actualRegion2X) * level.random.nextFloat();
            double y = blockEntity.actualRegion2Y + (blockEntity.actualRegion1Y - blockEntity.actualRegion2Y) * level.random.nextFloat();
            double z = blockEntity.actualRegion2Z + (blockEntity.actualRegion1Z - blockEntity.actualRegion2Z) * level.random.nextFloat();
            level.addParticle(DustParticleOptions.REDSTONE, x, blockEntity.actualRegion1Y, blockEntity.actualRegion1Z, 0, 0, 0);
            level.addParticle(DustParticleOptions.REDSTONE, x, blockEntity.actualRegion2Y, blockEntity.actualRegion1Z, 0, 0, 0);
            level.addParticle(DustParticleOptions.REDSTONE, x, blockEntity.actualRegion1Y, blockEntity.actualRegion2Z, 0, 0, 0);
            level.addParticle(DustParticleOptions.REDSTONE, x, blockEntity.actualRegion2Y, blockEntity.actualRegion2Z, 0, 0, 0);
            level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion1X, y, blockEntity.actualRegion1Z, 0, 0, 0);
            level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion2X, y, blockEntity.actualRegion1Z, 0, 0, 0);
            level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion1X, y, blockEntity.actualRegion2Z, 0, 0, 0);
            level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion2X, y, blockEntity.actualRegion2Z, 0, 0, 0);
            level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion1X, blockEntity.actualRegion1Y, z, 0, 0, 0);
            level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion2X, blockEntity.actualRegion1Y, z, 0, 0, 0);
            level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion1X, blockEntity.actualRegion2Y, z, 0, 0, 0);
            level.addParticle(DustParticleOptions.REDSTONE, blockEntity.actualRegion2X, blockEntity.actualRegion2Y, z, 0, 0, 0);
        }
    }
}
