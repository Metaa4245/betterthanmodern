package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.entity.living.animal.SheepEntity;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public class SheepEntityMixin extends AnimalEntityMixin {
    public SheepEntityMixin(Level level) {
        super(level);
    }

    @Inject(at = @At("HEAD"), method = "getMobDrops", cancellable = true)
    public void getMobDrops(CallbackInfoReturnable<Integer> cir) {
        SheepEntity thisObject = (SheepEntity) (Object) this;
        cir.setReturnValue(
                thisObject.fire > 0
                        ? ItemRegistry.cookedMutton.id
                        : ItemRegistry.rawMutton.id
        );
    }
}
