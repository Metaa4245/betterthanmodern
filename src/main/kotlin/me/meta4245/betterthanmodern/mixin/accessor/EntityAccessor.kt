package me.meta4245.betterthanmodern.mixin.accessor

import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor
import org.spongepowered.asm.mixin.gen.Invoker
import java.util.*

@Mixin(Entity::class)
interface EntityAccessor {
    @get:Accessor
    val random: Random?

    @get:Accessor
    val fireTicks: Int

    @Invoker
    fun callDropItem(id: Int, amount: Int): ItemEntity?
}
