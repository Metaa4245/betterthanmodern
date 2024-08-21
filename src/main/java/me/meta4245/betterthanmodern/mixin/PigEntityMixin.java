package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.entity.passive.PigEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigEntity.class)
public class PigEntityMixin {
    @Inject(at = @At("HEAD"), method = "method_914", cancellable = true)
    public void drop(CallbackInfoReturnable<Integer> cir) {
        // TODO: figure out a better way?
        PigEntity thisObject = (PigEntity) (Object) this;
        cir.setReturnValue(
                thisObject.fire > 0
                        ? ItemRegistry.cookedPorkchopId
                        : ItemRegistry.rawPorkchopId
        );
    }
}
