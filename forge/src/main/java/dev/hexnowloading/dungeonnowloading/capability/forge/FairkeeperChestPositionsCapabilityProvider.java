package dev.hexnowloading.dungeonnowloading.capability.forge;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FairkeeperChestPositionsCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<FairkeeperChestPositionsCapability> FAIRKEEPER_CHEST_POSITIONS = CapabilityManager.get(new CapabilityToken<FairkeeperChestPositionsCapability>() { });

    private FairkeeperChestPositionsCapability fairkeeperChestPositions = null;
    private final LazyOptional<FairkeeperChestPositionsCapability> optional = LazyOptional.of(this::createFairkeeperChestPositions);

    private FairkeeperChestPositionsCapability createFairkeeperChestPositions() {
        if (this.fairkeeperChestPositions == null) {
            this.fairkeeperChestPositions = new FairkeeperChestPositionsCapability();
        }

        return this.fairkeeperChestPositions;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == FAIRKEEPER_CHEST_POSITIONS) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        createFairkeeperChestPositions().saveNBTData(compoundTag);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        createFairkeeperChestPositions().loadNBTData(compoundTag);
    }
}
