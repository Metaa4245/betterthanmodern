package me.meta4245.betterthanmodern;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public interface IBreeding {
    int betterthanmodern$getBreedingTimer();
    int betterthanmodern$getFedTimer();
    int betterthanmodern$getChildTimer();
    void betterthanmodern$setBreedingTimer(int value);
    void betterthanmodern$setFedTimer(int value);
    void betterthanmodern$setChildTimer(int value);
    boolean betterthanmodern$isBreedable();
    boolean betterthanmodern$isFed();
    boolean betterthanmodern$isFoodItem(ItemStack stack);
    void betterthanmodern$spawnBaby(IBreeding partner);
    boolean betterthanmodern$isBaby();
    void betterthanmodern$setPassiveTarget(Entity entity);
    Entity betterthanmodern$getPassiveTarget();
}
