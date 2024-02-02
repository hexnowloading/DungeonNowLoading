package dev.hexnowloading.dungeonnowloading.world.processors;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.hexnowloading.dungeonnowloading.registry.DNLProcessors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.Weight;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeightedListProcessor extends StructureProcessor {

    public static final Codec<WeightedListProcessor> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("input_block").forGetter(config -> config.input_block),
            Codec.mapPair(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block"), Codec.intRange(1, Integer.MAX_VALUE).fieldOf("weight"))
                    .codec().listOf().fieldOf("weighted_list_of_replacement_blocks")
                    .forGetter(processor -> processor.weightedReplacementBlocks))
            .apply(instance, instance.stable(WeightedListProcessor::new)));

    private final List<Pair<Block, Integer>> weightedReplacementBlocks;
    private final Block input_block;

    public WeightedListProcessor(Block input_block, List<Pair<Block, Integer>> weightedReplacementBlocks) {
        this.input_block = input_block;
        this.weightedReplacementBlocks = weightedReplacementBlocks;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader, BlockPos jigsawPiecePos, BlockPos jigsawPieceBottomCenterPos, StructureTemplate.StructureBlockInfo blockInfoLocal, StructureTemplate.StructureBlockInfo blockInfoGlobal, StructurePlaceSettings settings) {
        if (blockInfoGlobal.state().getBlock() == input_block) {
            double totalWeight = 0.0D;
            RandomSource randomSource = settings.getRandom(blockInfoGlobal.pos());
            Block replacementBlock;
            if (weightedReplacementBlocks.size() == 1) {
                replacementBlock = weightedReplacementBlocks.get(0).getFirst();
            } else {
                for (Pair<Block, Integer> pair : weightedReplacementBlocks) {
                    totalWeight += pair.getSecond();
                }
                int index = 0;
                for (double randomWeightPicked = randomSource.nextFloat() * totalWeight; index < weightedReplacementBlocks.size() - 1; ++ index) {
                    randomWeightPicked -= weightedReplacementBlocks.get(index).getSecond();
                    if (randomWeightPicked <= 0.0) break;
                }
                replacementBlock = weightedReplacementBlocks.get(index).getFirst();
            }
            blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), replacementBlock.defaultBlockState(), blockInfoGlobal.nbt());
        }
        return blockInfoGlobal;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return DNLProcessors.WEIGHTED_LIST_PROCESSOR.get();
    }
}
