package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.entity.living.animal.PigEntity;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigEntity.class)
public class PigEntityMixin extends AnimalEntityMixin {
    public PigEntityMixin(Level level) {
        super(level);
    }

    @Inject(at = @At("HEAD"), method = "getMobDrops", cancellable = true)
    public void getMobDrops(CallbackInfoReturnable<Integer> cir) {
        // TODO: figure out a better way?
        PigEntity thisObject = (PigEntity) (Object) this;
        cir.setReturnValue(
                thisObject.fire > 0
                        ? ItemRegistry.cookedPorkchop.id
                        : ItemRegistry.rawPorkchop.id
        );
    }
}
