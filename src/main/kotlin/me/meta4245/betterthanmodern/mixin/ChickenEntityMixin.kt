package me.meta4245.betterthanmodern.mixin

import me.meta4245.betterthanmodern.mixin.accessor.EntityAccessor
import net.minecraft.entity.passive.ChickenEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

@Mixin(ChickenEntity::class)
abstract class ChickenEntityMixin {
    @Inject(at = [At("HEAD")], method = ["getDroppedId"], cancellable = true)
    fun getDroppedId(cir: CallbackInfoReturnable<Int?>?) {
        val accessor = this as EntityAccessor
        val fireTicks = accessor.fireTicks

        //        int rawChickenId;
//        int cookedChickenId;
//
//        try {
//            rawChickenId = (int) ItemRegistry.class.getDeclaredField("RawChickenId").get(this);
//            cookedChickenId = (int) ItemRegistry.class.getDeclaredField("CookedChickenId").get(this);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        cir.setReturnValue(
//                fireTicks > 0
//                        ? cookedChickenId
//                        : rawChickenId
//        );
    }
}
