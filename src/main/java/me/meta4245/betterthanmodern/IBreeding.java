package me.meta4245.betterthanmodern;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface IBreeding {
    boolean btm$isBreedable();

    void btm$setBreedingTimer(int value);

    boolean btm$isFoodItem(ItemStack stack);

    boolean btm$isFed();

    void btm$setFedTimer(int value);

    int btm$getChildTimer();

    void btm$setChildTimer(int value);

    boolean btm$isBaby();

    void btm$spawnBaby(IBreeding partner);

    void btm$setPassiveTarget(Entity entity);

    boolean btm$interact(PlayerEntity player);
}
