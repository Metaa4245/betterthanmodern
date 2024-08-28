package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import me.meta4245.betterthanmodern.mixin.accessor.EntityAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.SheepEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin {
    @Inject(
            method = "getDroppedId",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getDroppedId(CallbackInfoReturnable<Integer> cir) {
        EntityAccessor accessor = (EntityAccessor) this;
        Random random = accessor.getRandom();

        int muttonAmount = random.nextInt(1, 3);

        accessor.callDropItem(Block.WOOL.id, 1);
        accessor.callDropItem(ItemRegistry.rawMutton.id, muttonAmount);

        // LivingEntity doesn't drop if getDroppedId returns 0
        cir.setReturnValue(0);
    }
}
