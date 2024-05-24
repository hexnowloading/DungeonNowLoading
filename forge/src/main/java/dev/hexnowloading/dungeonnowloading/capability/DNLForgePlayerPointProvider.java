package dev.hexnowloading.dungeonnowloading.capability;

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

public class DNLForgePlayerPointProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<DNLForgePlayerPoint> PLAYER_TEST_POINT = CapabilityManager.get(new CapabilityToken<DNLForgePlayerPoint>() { });

    private DNLForgePlayerPoint playerPoint = null;
    private final LazyOptional<DNLForgePlayerPoint> optional = LazyOptional.of(this::createPlayerPoint);

    private DNLForgePlayerPoint createPlayerPoint() {
        if (this.playerPoint == null) {
            this.playerPoint = new DNLForgePlayerPoint();
        }

        return this.playerPoint;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_TEST_POINT) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerPoint().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerPoint().loadNBTData(nbt);
    }
}
