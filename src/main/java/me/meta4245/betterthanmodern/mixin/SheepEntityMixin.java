package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.entity.passive.SheepEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public class SheepEntityMixin {
    @Inject(at = @At("HEAD"), method = "method_914", cancellable = true)
    public void drop(CallbackInfoReturnable<Integer> cir) {
        SheepEntity thisObject = (SheepEntity) (Object) this;
        cir.setReturnValue(
                thisObject.fire > 0
                        ? ItemRegistry.rawMutton.id
                        : ItemRegistry.cookedMutton.id
        );
    }
}
