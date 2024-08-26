package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import me.meta4245.betterthanmodern.mixin.accessor.EntityAccessor;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin {
    @Inject(method = "getDroppedId", at = @At("HEAD"), cancellable = true)
    private void getDroppedId(CallbackInfoReturnable<Integer> cir) {
        EntityAccessor accessor = (EntityAccessor) this;
        Random random = accessor.getRandom();

        int featherAmount = random.nextInt(0, 3);

        accessor.callDropItem(Item.FEATHER.id, featherAmount);
        accessor.callDropItem(ItemRegistry.rawChicken.id, 1);

        // LivingEntity doesn't drop if getDroppedId returns 0
        cir.setReturnValue(0);
    }
}
