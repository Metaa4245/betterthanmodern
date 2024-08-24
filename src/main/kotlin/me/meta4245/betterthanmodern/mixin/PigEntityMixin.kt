package me.meta4245.betterthanmodern.mixin

import me.meta4245.betterthanmodern.event.ItemRegistry
import me.meta4245.betterthanmodern.mixin.accessor.EntityAccessor
import net.minecraft.entity.passive.PigEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

@Mixin(PigEntity::class)
abstract class PigEntityMixin {
    @Inject(at = [At("HEAD")], method = ["getDroppedId"], cancellable = true)
    fun getDroppedId(cir: CallbackInfoReturnable<Int?>) {
        val accessor = this as EntityAccessor
        val fireTicks = accessor.fireTicks

        val rawPorkchopId: Int
        val cookedPorkchopId: Int

        try {
            rawPorkchopId =
                ItemRegistry::class.java.getDeclaredField("RawPorkchopId")[this] as Int
            cookedPorkchopId =
                ItemRegistry::class.java.getDeclaredField("CookedPorkchopId")[this] as Int
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        cir.returnValue = if (fireTicks > 0)
            cookedPorkchopId
        else
            rawPorkchopId
    }
}
