package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public class SheepEntityMixin extends AnimalEntityMixin {
    public SheepEntityMixin(World world) {
        super(world);
    }

    @Inject(at = @At("HEAD"), method = "getDroppedId", cancellable = true)
    public void getDroppedId(CallbackInfoReturnable<Integer> cir) {
        SheepEntity thisObject = (SheepEntity) (Object) this;
        cir.setReturnValue(
                thisObject.fireTicks > 0
                        ? ItemRegistry.cookedMutton.id
                        : ItemRegistry.rawMutton.id
        );
    }
}
