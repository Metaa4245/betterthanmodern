package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.entity.passive.ChickenEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChickenEntity.class)
public class ChickenEntityMixin {
    @Inject(at = @At("HEAD"), method = "method_914", cancellable = true)
    public void drop(CallbackInfoReturnable<Integer> cir) {
        ChickenEntity thisObject = (ChickenEntity) (Object) this;
        cir.setReturnValue(
                thisObject.fire > 0
                        ? ItemRegistry.rawChicken.id
                        : ItemRegistry.cookedChicken.id
        );
    }
}
