package me.meta4245.betterthanmodern.mixin

import me.meta4245.betterthanmodern.event.ItemRegistry
import me.meta4245.betterthanmodern.mixin.accessor.EntityAccessor
import net.minecraft.entity.Entity
import net.minecraft.entity.mob.CreeperEntity
import net.minecraft.entity.mob.SkeletonEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(CreeperEntity::class)
abstract class CreeperEntityMixin {
    @Inject(
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/mob/MonsterEntity;onKilledBy(Lnet/minecraft/entity/Entity;)V",
            shift = At.Shift.AFTER
        )], method = ["onKilledBy"], cancellable = true
    )
    fun onKilledBy(entity: Entity?, ci: CallbackInfo) {
        val accessor = this as EntityAccessor
        val random = accessor.random

        val blocksId: Int

        try {
            blocksId =
                ItemRegistry::class.java.getDeclaredField("BlocksDiscId")[this] as Int
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        if (entity is SkeletonEntity) {
            accessor.callDropItem(blocksId + random!!.nextInt(12), 1)
        }
        ci.cancel()
    }
}
