package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(TallPlantBlock.class)
public abstract class TallPlantBlockMixin {
    @Inject(method = "getDroppedItemId", at = @At("HEAD"), cancellable = true)
    public void getDroppedItemId(
            int blockMeta,
            Random random,
            CallbackInfoReturnable<Integer> cir
    ) {
        final Item[] ids = {
                ItemRegistry.carrot,
                ItemRegistry.potato,
                Item.SEEDS
        };

        int id = random.nextInt(8) == 0
                ? ids[random.nextInt(ids.length)].id
                : -1;

        cir.setReturnValue(id);
    }
}
