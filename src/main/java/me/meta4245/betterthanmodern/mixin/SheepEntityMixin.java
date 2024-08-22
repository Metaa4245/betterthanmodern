package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.Mod;
import me.meta4245.betterthanmodern.event.ItemRegistry;
import me.meta4245.betterthanmodern.mixin.accessor.EntityAccessor;
import net.minecraft.entity.passive.SheepEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin {
    @Inject(at = @At("HEAD"), method = "getDroppedId", cancellable = true)
    public void getDroppedId(CallbackInfoReturnable<Integer> cir) {
        if (Mod.config.foods.mutton) {
            EntityAccessor accessor = (EntityAccessor) this;
            int fireTicks = accessor.getFireTicks();

            cir.setReturnValue(
                    fireTicks > 0
                            ? ItemRegistry.cookedMutton.id
                            : ItemRegistry.rawMutton.id
            );
        }
    }
}
