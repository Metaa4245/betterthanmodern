package me.meta4245.betterthanmodern.mixin

import me.meta4245.betterthanmodern.event.ItemRegistry
import me.meta4245.betterthanmodern.mixin.accessor.EntityAccessor
import net.minecraft.entity.passive.SheepEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

@Mixin(SheepEntity::class)
abstract class SheepEntityMixin {
    @Inject(at = [At("HEAD")], method = ["getDroppedId"], cancellable = true)
    fun getDroppedId(cir: CallbackInfoReturnable<Int?>) {
        val accessor = this as EntityAccessor
        val fireTicks = accessor.fireTicks

        val rawMuttonId: Int
        val cookedMuttonId: Int

        try {
            rawMuttonId =
                ItemRegistry::class.java.getDeclaredField("RawMuttonId")[this] as Int
            cookedMuttonId =
                ItemRegistry::class.java.getDeclaredField("CookedMuttonId")[this] as Int
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        cir.returnValue = if (fireTicks > 0)
            cookedMuttonId
        else
            rawMuttonId
    }
}
