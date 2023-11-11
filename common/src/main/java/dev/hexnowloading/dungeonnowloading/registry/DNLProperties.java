package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.block.property.AllSides;
import dev.hexnowloading.dungeonnowloading.block.property.BlockFaces;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class DNLProperties {
    public static final EnumProperty<AllSides> ALL_SIDES = EnumProperty.create("all_sides", AllSides.class);
    public static final EnumProperty<BlockFaces> BLOCK_FACES = EnumProperty.create("block_face", BlockFaces.class);
    public static final BooleanProperty SIDE_HALF_2 = BooleanProperty.create("side_half_2");
}
