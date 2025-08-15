package me.meta4245.betterthanmodern.mixin.accessor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor
    World getWorld();

    @Accessor
    double getX();

    @Accessor
    double getY();

    @Accessor
    double getZ();

    @Accessor
    int getFireTicks();

    @Accessor
    Random getRandom();

    @Invoker
    @SuppressWarnings("UnusedReturnValue")
    ItemEntity callDropItem(int id, int amount);

    @Invoker
    void callMarkDead();
}
