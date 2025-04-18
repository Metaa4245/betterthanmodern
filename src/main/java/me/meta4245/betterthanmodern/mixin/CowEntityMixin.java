package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import me.meta4245.betterthanmodern.mixin.accessor.EntityAccessor;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin {
    @Inject(
            method = "getDroppedItemId",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getDroppedItemId(CallbackInfoReturnable<Integer> cir) {
        EntityAccessor accessor = (EntityAccessor) this;
        Random random = accessor.getRandom();

        int leatherAmount = random.nextInt(0, 3);
        int beefAmount = random.nextInt(1, 4);
        int id;

        if (accessor.getFireTicks() > 0) {
            id = ItemRegistry.steak.id;
        } else {
            id = ItemRegistry.rawBeef.id;
        }

        accessor.callDropItem(Item.LEATHER.id, leatherAmount);
        accessor.callDropItem(id, beefAmount);

        // LivingEntity doesn't drop if getDroppedId returns 0
        cir.setReturnValue(0);
    }
}
