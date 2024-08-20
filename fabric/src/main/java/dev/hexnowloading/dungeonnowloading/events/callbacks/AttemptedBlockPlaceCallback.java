package dev.hexnowloading.dungeonnowloading.events.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;

public interface AttemptedBlockPlaceCallback {

    Event<AttemptedBlockPlaceCallback> EVENT = EventFactory.createArrayBacked(AttemptedBlockPlaceCallback.class,
            (listeners) -> (context -> {
                for(AttemptedBlockPlaceCallback listener : listeners){
                    InteractionResult result = listener.onBlockAttemptedPlace(context);
                    if(result != InteractionResult.PASS){
                        return result;
                    }
                }
                return InteractionResult.PASS;
            })) ;

    InteractionResult onBlockAttemptedPlace(BlockPlaceContext context);
}
