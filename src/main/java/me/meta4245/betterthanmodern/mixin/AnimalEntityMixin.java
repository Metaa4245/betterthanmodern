package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.IBreeding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static me.meta4245.betterthanmodern.reflection.Utilities.createEntity;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin
        extends MobEntity
        implements IBreeding {
    @Unique
    public int breedingTimer = 0;
    @Unique
    public int fedTimer = 0;
    @Unique
    public int childTimer = 0;

    @Unique
    public boolean isPersistent = false;

    @Unique
    public Entity passiveTarget = null;

    public AnimalEntityMixin(World world) {
        super(world);
    }

    @Unique
    protected abstract List<Item> getBreedFood();

    @Override
    public boolean btm$isBreedable() {
        return this.breedingTimer <= 0 && !btm$isBaby();
    }

    @Override
    public void btm$setBreedingTimer(int value) {
        this.breedingTimer = value;
    }

    @Override
    public boolean btm$isFoodItem(ItemStack stack) {
        if (stack == null) {
            return false;
        }

        return getBreedFood().contains(stack.getItem());
    }

    @Override
    public boolean btm$isFed() {
        return this.fedTimer > 0;
    }

    @Override
    public void btm$setFedTimer(int value) {
        this.fedTimer = value;
    }

    @Override
    public int btm$getChildTimer() {
        return this.childTimer;
    }

    @Override
    public void btm$setChildTimer(int value) {
        this.childTimer = value;
    }

    @Override
    public boolean btm$isBaby() {
        return this.childTimer > 0;
    }

    @Override
    public void btm$spawnBaby(IBreeding partner) {
        if (this.world.isRemote) {
            return;
        }

        int timer = (int) Math.ceil(33.3 * 60);

        AnimalEntity entity = (AnimalEntity)
                createEntity(this.getClass(), this.world);
        entity.setPosition(this.x, this.y, this.z);

        ((IBreeding) entity).btm$setChildTimer(20 * 60 * 5);

        this.world.spawnEntity(entity);

        fedTimer = 0;
        partner.btm$setFedTimer(0);

        breedingTimer = timer;
        partner.btm$setBreedingTimer(timer);

        passiveTarget = null;
        partner.btm$setPassiveTarget(null);
    }

    @Override
    public void btm$setPassiveTarget(Entity entity) {
        this.passiveTarget = entity;

        if (entity == null) {
            setTarget(null);
        }
    }

    @Override
    public boolean btm$interact(PlayerEntity player) {
        boolean flag = super.interact(player);

        ItemStack item = player.inventory.getSelectedItem();

        if (item == null) {
            return flag;
        }

        if (!btm$isFoodItem(item)) {
            return flag;
        }

        if (!(btm$isBreedable() || btm$isBaby())) {
            return flag;
        }

        item.count--;

        if (!btm$isBaby()) {
            fedTimer = 5 * 60;
            isPersistent = true;

            return true;
        }

        int timer = (int) Math.floor(btm$getChildTimer() * 0.75f);
        btm$setChildTimer(timer);

        double bbWidth = this.boundingBox.maxX;
        double bbHeight = this.boundingBox.maxY;

        double x = (this.random.nextFloat() * bbWidth * 2.0F) - bbWidth;
        double y = (this.random.nextFloat() * bbHeight);
        double z = (this.random.nextFloat() * bbWidth * 2.0F) - bbWidth;

        double vx = this.random.nextGaussian() * 0.02;
        double vy = this.random.nextGaussian() * 0.02;
        double vz = this.random.nextGaussian() * 0.02;

        this.world.addParticle("soulflame", x, y, z, vx, vy, vz);

        return true;
    }

    @Override
    public boolean interact(PlayerEntity player) {
        return btm$interact(player);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void tickLiving() {
        if (breedingTimer > 0) {
            breedingTimer--;
        }

        if (btm$isBaby()) {
            childTimer--;

            isPersistent = true;
        }

        if (btm$isFed()) {
            fedTimer--;
        }

        Box bbox = this.boundingBox
                .expand(0.2, 0.0, 0.2)
                .offset(-0.1, -0.1, -0.1);

        this.world.getEntities(this, bbox).forEach(e -> {
            if (!(e instanceof IBreeding)) {
                return;
            }

            if (!e.getClass().isInstance(this)) {
                return;
            }

            if (!btm$isFed()) {
                return;
            }

            if (!((IBreeding) e).btm$isFed()) {
                return;
            }

            if (!btm$isBreedable()) {
                return;
            }

            if (!((IBreeding) e).btm$isBreedable()) {
                return;
            }

            btm$spawnBaby((IBreeding) e);
        });

        if (age % 40 == 0) {
            bbox = this.boundingBox
                    .expand(10, 10, 10)
                    .offset(-5, -5, -5);
            List<Entity> entities = this.world.getEntities(this, bbox);

            if (btm$isBaby() && passiveTarget == null) {
                entities.forEach(e -> {
                    if (!(e instanceof IBreeding)) {
                        return;
                    }

                    if (!e.getClass().isInstance(this)) {
                        return;
                    }

                    if (((IBreeding) e).btm$isBaby()) {
                        return;
                    }

                    passiveTarget = e;
                });
            } else if (btm$isFed()) {
                passiveTarget = null;

                entities.forEach(e -> {
                    if (!(e instanceof IBreeding)) {
                        return;
                    }

                    if (!e.getClass().isInstance(this)) {
                        return;
                    }

                    if (!btm$isFed()) {
                        return;
                    }

                    if (!((IBreeding) e).btm$isFed()) {
                        return;
                    }

                    if (!btm$isBreedable()) {
                        return;
                    }

                    if (!((IBreeding) e).btm$isBreedable()) {
                        return;
                    }

                    passiveTarget = e;
                });
            } else if (btm$isBreedable()) {
                passiveTarget = null;

                entities.forEach(e -> {
                    if (!(e instanceof PlayerEntity)) {
                        return;
                    }

                    if (!btm$isFoodItem(((PlayerEntity) e).getHeldItem())) {
                        return;
                    }

                    passiveTarget = e;
                });
            }
        }

        super.tickLiving();

        if (!btm$isFed()) {
            return;
        }

        if (age % 4 != 0) {
            return;
        }

        double bbWidth = this.boundingBox.maxX;
        double bbHeight = this.boundingBox.maxY;

        double x = (this.random.nextFloat() * bbWidth * 2.0F) - bbWidth;
        double y = (this.random.nextFloat() * bbHeight);
        double z = (this.random.nextFloat() * bbWidth * 2.0F) - bbWidth;

        double vx = this.random.nextGaussian() * 0.02;
        double vy = this.random.nextGaussian() * 0.02;
        double vz = this.random.nextGaussian() * 0.02;

        this.world.addParticle("heart", x, y, z, vx, vy, vz);
    }

    @Override
    protected void pathingUpdate() {
        if (passiveTarget == null && getTarget() == null) {
            setTarget(null);
        }

        if (passiveTarget != null) {
            if (
                    passiveTarget instanceof PlayerEntity
                            && getSquaredDistance(passiveTarget) < 9
            ) {
                setTarget(null);
            } else {
                setTarget(passiveTarget);
            }
        }

        super.pathingUpdate();
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    public void readBreedNbt(NbtCompound compound, CallbackInfo ci) {
        this.breedingTimer = compound.getInt("breeding$breedtime");
        this.fedTimer = compound.getInt("breeding$fedtime");
        this.childTimer = compound.getInt("breeding$childtime");
        this.isPersistent = compound.getBoolean("breeding$persistent");
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    public void writeBreedNbt(NbtCompound compound, CallbackInfo ci) {
        compound.putInt("breeding$breedtime", this.breedingTimer);
        compound.putInt("breeding$fedtime", this.fedTimer);
        compound.putInt("breeding$childtime", this.childTimer);
        compound.putBoolean("breeding$persistent", this.isPersistent);
    }

    @Override
    public boolean canDespawn() {
        return super.canDespawn() && !this.isPersistent;
    }
}
