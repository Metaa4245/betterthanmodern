package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.IBreeding;
import me.meta4245.betterthanmodern.Mod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.animal.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin extends Entity implements IBreeding {
    @Unique
    public int breedingTimer = 0;
    @Unique
    public int fedTimer = 0;
    @Unique
    public int childhoodTimer = 0;
    @Unique
    public boolean isPersistent = false;
    @Unique
    public Entity passiveTarget = null;

    public AnimalEntityMixin(Level level) {
        super(level);
    }

    @Override
    public int betterthanmodern$getBreedingTimer() {
        return breedingTimer;
    }

    @Override
    public int betterthanmodern$getFedTimer() {
        return fedTimer;
    }

    @Override
    public int betterthanmodern$getChildTimer() {
        return childhoodTimer;
    }

    @Override
    public void betterthanmodern$setBreedingTimer(int value) {
        this.breedingTimer = value;
    }

    @Override
    public void betterthanmodern$setFedTimer(int value) {
        this.fedTimer = value;
    }

    @Override
    public void betterthanmodern$setChildTimer(int value) {
        this.childhoodTimer = value;
    }

    @Override
    public boolean betterthanmodern$isBreedable() {
        return breedingTimer <= 0 && ! betterthanmodern$isBaby();
    }

    @Override
    public boolean betterthanmodern$isFed() {
        return fedTimer > 0;
    }

    @Override
    public boolean betterthanmodern$isFoodItem(ItemStack stack) {
        return stack != null && stack.getType() == Item.wheat;
    }

    @Override
    public void betterthanmodern$spawnBaby(IBreeding partner) {
        if (!level.isRemote) return;

        AnimalEntity entity = (AnimalEntity) Mod.createEntity(this.getClass(), world);
        entity.move(x, y, z);

        ((IBreeding) entity).betterthanmodern$setChildTimer(20 * 60 * 5);

        level.spawnEntity(entity);
        this.betterthanmodern$setFedTimer(0);
        partner.betterthanmodern$setFedTimer(0);
        this.betterthanmodern$setBreedingTimer(20 * 100);
        partner.betterthanmodern$setBreedingTimer(20 * 100);
        this.betterthanmodern$setPassiveTarget(null);
        partner.betterthanmodern$setPassiveTarget(null);
    }

    @Override
    public boolean betterthanmodern$isBaby() {
        return betterthanmodern$getChildTimer() > 0;
    }

    @Override
    public void betterthanmodern$setPassiveTarget(Entity entity) {
        this.passiveTarget = entity;
        if (entity == null) {
        }
    }

    @Override
    public Entity betterthanmodern$getPassiveTarget() {
        return null;
    }
}
