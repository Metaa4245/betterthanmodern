package me.meta4245.betterthanmodern.mixin.accessor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor
    int getFireTicks();

    @Accessor
    Random getRandom();

    @Invoker
    ItemEntity callDropItem(int id, int amount);

    @Invoker
    void callMarkDead();
}
