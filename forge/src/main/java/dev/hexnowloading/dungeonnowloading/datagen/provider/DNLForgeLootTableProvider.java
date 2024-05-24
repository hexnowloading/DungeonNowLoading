package dev.hexnowloading.dungeonnowloading.datagen.provider;

import dev.hexnowloading.dungeonnowloading.datagen.loot.DNLForgeBlockLootTableProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class DNLForgeLootTableProvider {

    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(DNLForgeBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ));
    }

}
