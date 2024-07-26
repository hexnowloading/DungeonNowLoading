package dev.hexnowloading.dungeonnowloading.block.entity;

import dev.hexnowloading.dungeonnowloading.block.DisabledFairkeeperChestBlock;
import dev.hexnowloading.dungeonnowloading.block.FairkeeperChestBlock;
import dev.hexnowloading.dungeonnowloading.block.property.ChestStates;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlockEntityTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DisabledFairkeeperChestBlockEntity extends RandomizableContainerBlockEntity implements MenuProvider {

    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    protected ResourceLocation lootTable;
    protected long lootTableSeed;
    private static final int OPEN_CLOSE_ANIMATION_DURATION = 10;
    private int openCloseAnimationProgress = 0;
    private int prevOpenCloseAnimationProgress = 0;

    public DisabledFairkeeperChestBlockEntity(BlockPos pos, BlockState state) {
        super(DNLBlockEntityTypes.DISABLED_FAIRKEEPER_CHEST.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (!this.trySaveLootTable(compoundTag)) {
            ContainerHelper.saveAllItems(compoundTag, this.items);
        }
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compoundTag)) {
            ContainerHelper.loadAllItems(compoundTag, this.items);
        }
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemStacks) {
        this.items = itemStacks;
    }

    @Override
    protected Component getDefaultName() {
        if (this.getBlockState().is(DNLBlocks.WISE_FAIRKEEPER_CHEST.get())) {
            return Component.translatable("block.dungeonnowloading.wise_fairkeeper_chest");
        } else {
            return Component.translatable("block.dungeonnowloading.fierce_fairkeeper_chest");
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return ChestMenu.threeRows(i, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            DisabledFairkeeperChestBlock.setFairkeeperChest(this.getLevel(), this.getBlockPos(), ChestStates.OPENING);
            DisabledFairkeeperChestBlockEntity.playSound(this.getLevel(), this.getBlockPos(), SoundEvents.CHEST_OPEN);
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            DisabledFairkeeperChestBlock.setFairkeeperChest(this.getLevel(), this.getBlockPos(), ChestStates.CLOSING);
            DisabledFairkeeperChestBlockEntity.playSound(this.getLevel(), this.getBlockPos(), SoundEvents.CHEST_CLOSE);
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, DisabledFairkeeperChestBlockEntity blockEntity) {
        if (level.isClientSide) {
            blockEntity.prevOpenCloseAnimationProgress = blockEntity.openCloseAnimationProgress;
            if (state.getValue(DNLProperties.CHEST_STATES) == ChestStates.OPENING) {
                if (blockEntity.openCloseAnimationProgress == OPEN_CLOSE_ANIMATION_DURATION) {
                    DisabledFairkeeperChestBlock.setFairkeeperChest(level, pos, ChestStates.OPENED);
                } else {
                    blockEntity.openCloseAnimationProgress++;
                }
            } else if (state.getValue(DNLProperties.CHEST_STATES) == ChestStates.CLOSING) {
                if (blockEntity.openCloseAnimationProgress == 0) {
                    DisabledFairkeeperChestBlock.setFairkeeperChest(level, pos, ChestStates.CLOSED);
                } else {
                    blockEntity.openCloseAnimationProgress--;
                }
            }
        }
        /*System.out.println(blockEntity);
        level.addParticle(new AxisParticleType.AxisParticleData(DNLParticleTypes.FAIRKEEPER_BOUNDARY_PARTICLE.get(), 1, 90), pos.getX() + 2, pos.getY() + 0.5F, pos.getZ() + 0.5F, 1, 90F, 0.0F);
        level.addParticle(DNLParticleTypes.FAIRKEEPER_BOUNDARY_PARTICLE.get(), pos.getX() + 0.5F, pos.getY() + 2, pos.getZ() + 0.5F, 0, 90F, 0.0F);
        level.addParticle(DNLParticleTypes.FAIRKEEPER_BOUNDARY_PARTICLE.get(), pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 2, 1, 0F, 0.0F);*/
    }

    public static void playSound(Level level, BlockPos blockPos, SoundEvent soundEvent) {
        level.playSound((Player) null, blockPos, soundEvent, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1f + 0.9f);
    }

    public float getOpenProgress(float partialTicks) {
        return (prevOpenCloseAnimationProgress + (openCloseAnimationProgress - prevOpenCloseAnimationProgress) * partialTicks) / OPEN_CLOSE_ANIMATION_DURATION;
    }
}
