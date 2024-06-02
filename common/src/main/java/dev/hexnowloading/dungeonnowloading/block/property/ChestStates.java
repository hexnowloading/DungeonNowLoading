package dev.hexnowloading.dungeonnowloading.block.property;

import net.minecraft.util.StringRepresentable;

public enum ChestStates implements StringRepresentable {
    OPENING("opening"),
    OPENED("opened"),
    CLOSING("closing"),
    CLOSED("closed");

    private final String name;

    private ChestStates(String string) { this.name = string; }

    public String toString() { return this.name; }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
