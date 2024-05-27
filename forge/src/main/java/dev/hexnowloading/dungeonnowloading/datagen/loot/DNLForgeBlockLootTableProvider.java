package dev.hexnowloading.dungeonnowloading.datagen.loot;

import dev.hexnowloading.dungeonnowloading.block.PileBlock;
import dev.hexnowloading.dungeonnowloading.registration.RegistryObject;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class DNLForgeBlockLootTableProvider extends BlockLootSubProvider {
    public DNLForgeBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(DNLBlocks.COILING_STONE_PILLAR.get());
        this.dropSelf(DNLBlocks.COILING_STONE_PILLAR_STAIRS.get());
        this.add(DNLBlocks.COILING_STONE_PILLAR_SLAB.get(), block -> createSlabItemTable(DNLBlocks.COILING_STONE_PILLAR_SLAB.get()));
        this.dropSelf(DNLBlocks.COILING_STONE_PILLAR_WALL.get());
        this.dropSelf(DNLBlocks.CHISELED_COILING_STONE_PILLAR.get());
        this.dropSelf(DNLBlocks.COILING_STONE_PILLAR_CAPITAL.get());
        this.dropSelf(DNLBlocks.STONE_TILES.get());
        this.dropSelf(DNLBlocks.CRACKED_STONE_TILES.get());
        this.dropSelf(DNLBlocks.STONE_TILE_STAIRS.get());
        this.add(DNLBlocks.STONE_TILE_SLAB.get(), block -> createSlabItemTable(DNLBlocks.STONE_TILE_SLAB.get()));
        this.dropSelf(DNLBlocks.STONE_TILE_WALL.get());
        this.dropSelf(DNLBlocks.SIGNALING_STONE_EMBLEM.get());
        this.dropSelf(DNLBlocks.DUELING_STONE_EMBLEM.get());
        this.dropSelf(DNLBlocks.PUZZLING_STONE_EMBLEM.get());
        this.add(DNLBlocks.BOOK_PILE.get(), block -> bookPile());
        this.add(DNLBlocks.COBBLESTONE_PEBBLES.get(), block -> pileBlock(block, DNLItems.COBBLESTONE_PEBBLE.get()));
        this.add(DNLBlocks.MOSSY_COBBLESTONE_PEBBLES.get(), block -> pileBlock(block, DNLItems.MOSSY_COBBLESTONE_PEBBLE.get()));
        this.dropSelf(DNLBlocks.DUNGEON_WALL_TORCH.get());
        this.add(DNLBlocks.EXPLOSIVE_BARREL.get(), block -> createSingleItemTable(Items.GUNPOWDER, UniformGenerator.between(1.0F, 3.0F)));
        this.add(DNLBlocks.IRON_INGOT_PILE.get(), block -> pileBlock(block, Items.IRON_INGOT));
        this.add(DNLBlocks.GOLD_INGOT_PILE.get(), block -> pileBlock(block, Items.GOLD_INGOT));
        this.dropSelf(DNLBlocks.WOODEN_WALL_RACK.get());
        this.dropSelf(DNLBlocks.WOODEN_WALL_PLATFORM.get());
    }

    private LootTable.Builder bookPile() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.BOOK)
                                .apply(EnchantRandomlyFunction.randomEnchantment().when(LootItemRandomChanceCondition.randomChance(0.25F)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(DNLBlocks.BOOK_PILE.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PileBlock.PILE, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(2.0F))
                        .add(LootItem.lootTableItem(Items.BOOK)
                                .apply(EnchantRandomlyFunction.randomEnchantment().when(LootItemRandomChanceCondition.randomChance(0.25F)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(DNLBlocks.BOOK_PILE.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PileBlock.PILE, 2))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(3.0F))
                        .add(LootItem.lootTableItem(Items.BOOK)
                                .apply(EnchantRandomlyFunction.randomEnchantment().when(LootItemRandomChanceCondition.randomChance(0.25F)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(DNLBlocks.BOOK_PILE.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PileBlock.PILE, 3))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(4.0F))
                        .add(LootItem.lootTableItem(Items.BOOK)
                                .apply(EnchantRandomlyFunction.randomEnchantment().when(LootItemRandomChanceCondition.randomChance(0.25F)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(DNLBlocks.BOOK_PILE.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PileBlock.PILE, 4))));
    }

    private LootTable.Builder pileBlock(Block block, ItemLike itemLike) {
        return LootTable.lootTable()
                .withPool(this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(itemLike)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PileBlock.PILE, 1))))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PileBlock.PILE, 2))))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PileBlock.PILE, 3))))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PileBlock.PILE, 4))))
                        )));
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return DNLBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
