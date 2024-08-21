package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.entity.living.animal.CowEntity;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public class CowEntityMixin extends AnimalEntityMixin {
    public CowEntityMixin(Level level) {
        super(level);
    }

    @Inject(at = @At("HEAD"), method = "getMobDrops", cancellable = true)
    public void getMobDrops(CallbackInfoReturnable<Integer> cir) {
        CowEntity thisObject = (CowEntity) (Object) this;
        cir.setReturnValue(
                thisObject.fire > 0
                        ? ItemRegistry.steak.id
                        : ItemRegistry.rawBeef.id
        );
    }
}
