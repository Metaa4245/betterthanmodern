package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.IBreeding;
import me.meta4245.betterthanmodern.Mod;
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

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin extends MobEntity implements IBreeding {
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

    public AnimalEntityMixin(World world) {
        super(world);
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
        return breedingTimer <= 0 && !betterthanmodern$isBaby();
    }

    @Override
    public boolean betterthanmodern$isFed() {
        return fedTimer > 0;
    }

    @Override
    public boolean betterthanmodern$isFoodItem(ItemStack stack) {
        return stack != null && stack.getItem() == Item.WHEAT;
    }

    @Override
    public void betterthanmodern$spawnBaby(IBreeding partner) {
        if (!world.isRemote) return;

        AnimalEntity entity = (AnimalEntity) Mod.createEntity(this.getClass(), world);
        entity.move(x, y, z);

        ((IBreeding) entity).betterthanmodern$setChildTimer(20 * 60 * 5);

        world.spawnEntity(entity);
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
            this.setTarget(null);
        }
    }

    @Override
    public Entity betterthanmodern$getPassiveTarget() {
        return this.passiveTarget;
    }

    @Override
    public boolean interact(PlayerEntity player) {
        ItemStack item = player.getHeldItem();
        boolean flag = super.interact(player);

        if (item == null) return flag;
        if (!betterthanmodern$isFoodItem(item)) return flag;
        if (!betterthanmodern$isBreedable() || !betterthanmodern$isBaby()) return flag;

        item.use(world, player);

        if (this.betterthanmodern$isBaby()) {
            this.betterthanmodern$setChildTimer(
                    (int) (this.betterthanmodern$getChildTimer() * 0.75f)
            );

            double d = this.random.nextGaussian() * 0.02;
            double d1 = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            this.world.addParticle(
                    "soulflame",
                    this.x + this.random.nextDouble() * this.width * 2 - this.width,
                    this.y + 0.5 + this.random.nextDouble() * this.height,
                    this.z + this.random.nextDouble() + this.width * 2 - this.width,
                    d,
                    d1,
                    d2
            );
        } else {
            this.betterthanmodern$setFedTimer(20 * 15);
            isPersistent = true;
        }

        return true;
    }

    @Override
    public void tickLiving() {
        if (breedingTimer > 0) breedingTimer--;
        if (betterthanmodern$getChildTimer() > 0) {
            isPersistent = true;
            betterthanmodern$setChildTimer(betterthanmodern$getChildTimer() - 1);
        }
        if (betterthanmodern$isFed()) {
            fedTimer--;
        }

        Box area1 = this.boundingBox.copy();
        area1.expand(0.2, 0.2, 0.2);
        List<Entity> list = this.world.getEntities(this, area1);
        if (list != null && !list.isEmpty() && !isMovementBlocked()) {
            for (Entity e : list) {
                if (!(e instanceof IBreeding breed_e)) continue;

                if (e.getClass().isInstance(this)
                        && this.betterthanmodern$isFed()
                        && breed_e.betterthanmodern$isFed()
                        && this.betterthanmodern$isBreedable()
                        && breed_e.betterthanmodern$isBreedable()) {
                    this.betterthanmodern$spawnBaby(breed_e);
                    break;
                }
            }
        }

        if (age % 40 == 0 && !isMovementBlocked()) {
            Box area2 = this.boundingBox.copy();
            area2.expand(10.0, 10.0, 10.0);
            list = this.world.getEntities(this, area2);
            if (betterthanmodern$isBaby() && betterthanmodern$getPassiveTarget() == null) {
                for (Entity e : list) {
                    if (!(e instanceof IBreeding breed_e)) continue;

                    if (e.getClass().isInstance(this)
                            && breed_e.betterthanmodern$isBaby()) {
                        this.betterthanmodern$setPassiveTarget(e);
                        break;
                    }
                }
            } else if (betterthanmodern$isFed()) {
                this.betterthanmodern$setPassiveTarget(null);
                for (Entity e : list) {
                    if (!(e instanceof IBreeding breed_e)) continue;

                    if (e.getClass().isInstance(this)
                            && this.betterthanmodern$isFed()
                            && breed_e.betterthanmodern$isFed()
                            && this.betterthanmodern$isBreedable()
                            && breed_e.betterthanmodern$isBreedable()) {
                        this.betterthanmodern$setPassiveTarget(e);
                        break;
                    }
                }
            } else if (betterthanmodern$isBreedable()) {
                this.betterthanmodern$setPassiveTarget(null);
                for (Entity e : list) {
                    if (e instanceof PlayerEntity && betterthanmodern$isFoodItem(((PlayerEntity) e).getHeldItem())) {
                        this.betterthanmodern$setPassiveTarget(e);
                        break;
                    }
                }
            }
        }

        super.tick();

        if (betterthanmodern$isFed() && age % 4 == 0) {
            double d = this.random.nextGaussian() * 0.02;
            double d1 = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            this.world.addParticle(
                    "heart",
                    this.x + this.random.nextDouble() * this.width * 2 - this.width,
                    this.y + 0.5 + this.random.nextDouble() * this.height,
                    this.z + this.random.nextDouble() * this.width * 2 - this.width,
                    d,
                    d1,
                    d2
            );
        }
    }

    @Override
    protected void method_920() {
        if (passiveTarget == null && getTarget() == null) {
            this.setTarget(null);
        }

        if (passiveTarget != null) {
            if (passiveTarget instanceof PlayerEntity) {
                this.setTarget(null);
            } else {
                this.setTarget(passiveTarget);
            }
        }

        super.method_920();
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    private void writeNbt(NbtCompound tag, CallbackInfo ci) {
        tag.putInt("breeding$breedtime", breedingTimer);
        tag.putInt("breeding$fedtime", fedTimer);
        tag.putInt("breeding$childtime", childhoodTimer);
        tag.putBoolean("breeding$persistent", isPersistent);
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    private void readNbt(NbtCompound tag, CallbackInfo ci) {
        this.breedingTimer = tag.getInt("breeding$breedtime");
        this.fedTimer = tag.getInt("breeding$fedtime");
        this.childhoodTimer = tag.getInt("breeding$childtime");
        this.isPersistent = tag.getBoolean("breeding$persistent");
    }

    @Override
    public boolean canDespawn() {
        return super.canDespawn() && !isPersistent;
    }
}
