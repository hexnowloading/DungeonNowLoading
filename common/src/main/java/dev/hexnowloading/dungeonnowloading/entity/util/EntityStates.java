package dev.hexnowloading.dungeonnowloading.entity.util;

import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.dungeonnowloading.entity.monster.ScuttleEntity;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class EntityStates {
    public static final EntityDataSerializer<ChaosSpawnerEntity.State> CHAOS_SPAWNER_STATE;
    public static final EntityDataSerializer<ScuttleEntity.ScuttleState> SCUTTLE_STATE;
    static {
        CHAOS_SPAWNER_STATE = EntityDataSerializer.simpleEnum(ChaosSpawnerEntity.State.class);
        SCUTTLE_STATE = EntityDataSerializer.simpleEnum(ScuttleEntity.ScuttleState.class);
        EntityDataSerializers.registerSerializer(CHAOS_SPAWNER_STATE);
        EntityDataSerializers.registerSerializer(SCUTTLE_STATE);
    }

}
