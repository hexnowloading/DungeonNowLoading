package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.block.property.*;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class DNLProperties {
    public static final EnumProperty<AllSides> ALL_SIDES = EnumProperty.create("all_sides", AllSides.class);
    public static final EnumProperty<BlockFaces> BLOCK_FACES = EnumProperty.create("block_face", BlockFaces.class);
    public static final EnumProperty<BarrierVertexs> BARRIER_VERTEXS = EnumProperty.create("barrier_vertex", BarrierVertexs.class);
    public static final EnumProperty<BarrierEdges> BARRIER_EDGES = EnumProperty.create("barrier_edge", BarrierEdges.class);
    public static final EnumProperty<RedstoneLaneMode> REDSTONE_LANE_MODE = EnumProperty.create("redstone_lane_mode", RedstoneLaneMode.class);
    public static final EnumProperty<ChestStates> CHEST_STATES = EnumProperty.create("chest_state", ChestStates.class);
    public static final IntegerProperty REDSTONE_LANE_POWER = IntegerProperty.create("redstone_lane_power", 0, 30);
    public static final IntegerProperty PILE = IntegerProperty.create("pile", 1, 4);
    public static final BooleanProperty BARRIER_ACTIVE = BooleanProperty.create("barrier_active");
    public static final BooleanProperty FAIRKEEPER_ALERT = BooleanProperty.create("fairkeeper_alert");
    public static final BooleanProperty FAIRKEEPER_DISABLED = BooleanProperty.create("fairkeeper_disabled");

    public static void init() {}
}
