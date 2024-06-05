package dev.hexnowloading.dungeonnowloading.block.property;

import net.minecraft.util.StringRepresentable;

public enum RedstoneLaneMode implements StringRepresentable {

    UNPOWERED("unpowered"),
    POWERED("powered"),
    OVERPOWERED("overpowered");

    private final String name;

    private RedstoneLaneMode(String string) { this.name = string; }

    public String toString() { return this.name; }

    @Override
    public String getSerializedName() {
        return this.name;
    }

}
