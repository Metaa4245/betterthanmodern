package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.IBreeding;
import me.meta4245.betterthanmodern.Mod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.WalkingEntity;
import net.minecraft.entity.living.animal.AnimalEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin extends WalkingEntity implements IBreeding {
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
        return breedingTimer <= 0 && !betterthanmodern$isBaby();
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

        AnimalEntity entity = (AnimalEntity) Mod.createEntity(this.getClass(), level);
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

        item.use(level, player);

        if (this.betterthanmodern$isBaby()) {
            this.betterthanmodern$setChildTimer(
                    (int) (this.betterthanmodern$getChildTimer() * 0.75f)
            );

            double d = this.random.nextGaussian() * 0.02;
            double d1 = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            this.level.addParticle(
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
    public void tick() {
        if (breedingTimer > 0) breedingTimer--;
        if (betterthanmodern$getChildTimer() > 0) {
            isPersistent = true;
            betterthanmodern$setChildTimer(betterthanmodern$getChildTimer() - 1);
        }
        if (betterthanmodern$isFed()) {
            fedTimer--;
        }

        List<Entity> list = this.level.getEntities(this, this.boundingBox.expanded(0.2, 0.0, 0.2));
        if (list != null && !list.isEmpty() && !cannotMove()) {
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

        if (ticks % 40 == 0 && !cannotMove()) {
            list = this.level.getEntities(this, this.boundingBox.expanded(10, 10, 10));
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

        if (betterthanmodern$isFed() && ticks % 4 == 0) {
            double d = this.random.nextGaussian() * 0.02;
            double d1 = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            this.level.addParticle(
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
    protected void tickHandSwing() {
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

        super.tickHandSwing();
    }

    @Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
    private void saveData(CompoundTag tag, CallbackInfo ci) {
        tag.put("breeding$breedtime", breedingTimer);
        tag.put("breeding$fedtime", fedTimer);
        tag.put("breeding$childtime", childhoodTimer);
        tag.put("breeding$persistent", isPersistent);
    }

    @Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
    private void loadData(CompoundTag tag, CallbackInfo ci) {
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
