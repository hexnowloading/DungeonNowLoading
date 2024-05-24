package dev.hexnowloading.dungeonnowloading.capabilities.player;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PlayerCapabilityHandler implements PlayerComponent<PlayerCapabilityHandler>, IPlayerCapability, AutoSyncedComponent {

    private int testPoint = (int) (Math.random() * 20);

    @Override
    public void setPoint(int amount) {
        this.testPoint = amount;
    }

    @Override
    public int getPoint() {
        return this.testPoint;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.testPoint = tag.getInt("testPoint");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putInt("testPoint", this.testPoint);
    }

    @Override
    public boolean shouldCopyForRespawn(boolean lossless, boolean keepInventory, boolean sameCharacter) {
        return lossless || keepInventory;
    }
}
