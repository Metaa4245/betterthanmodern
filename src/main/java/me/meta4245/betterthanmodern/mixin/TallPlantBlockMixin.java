package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.ThreadLocalRandom;

@Mixin(TallPlantBlock.class)
public abstract class TallPlantBlockMixin {
    @Redirect(
            method = "getDroppedItemId",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/item/Item;SEEDS:Lnet/minecraft/item/Item;"
            )
    )
    public Item redirectItem() {
        ThreadLocalRandom rng = ThreadLocalRandom.current();

        final Item[] ids = {
                ItemRegistry.carrot,
                ItemRegistry.potato,
                Item.SEEDS
        };

        return ids[rng.nextInt(ids.length)];
    }
}
