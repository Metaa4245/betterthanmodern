package me.meta4245.betterthanmodern.mixin

import me.meta4245.betterthanmodern.event.ItemRegistry
import me.meta4245.betterthanmodern.mixin.accessor.EntityAccessor
import net.minecraft.entity.passive.CowEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

@Mixin(CowEntity::class)
abstract class CowEntityMixin {
    @Inject(at = [At("HEAD")], method = ["getDroppedId"], cancellable = true)
    fun getDroppedId(cir: CallbackInfoReturnable<Int?>) {
        val accessor = this as EntityAccessor
        val fireTicks = accessor.fireTicks

        val rawBeefId: Int
        val steakId: Int

        try {
            rawBeefId =
                ItemRegistry::class.java.getDeclaredField("RawBeefId")[this] as Int
            steakId =
                ItemRegistry::class.java.getDeclaredField("SteakId")[this] as Int
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        cir.returnValue = if (fireTicks > 0)
            steakId
        else
            rawBeefId
    }
}
