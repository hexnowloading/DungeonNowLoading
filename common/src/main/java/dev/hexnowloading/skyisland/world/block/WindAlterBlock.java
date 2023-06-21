package dev.hexnowloading.skyisland.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class WindAlterBlock extends Block {
    public WindAlterBlock(Properties $$0) {
        super($$0);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        level.playSound(player, pos, SoundEvents.ALLAY_DEATH, SoundSource.BLOCKS, 1f, 1f);
        return super.use(state, level, pos, player, hand, hit);
    }
}
