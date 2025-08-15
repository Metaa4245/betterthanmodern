package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import me.meta4245.betterthanmodern.mixin.accessor.EntityAccessor;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntityMixin {
    @Unique
    private final static List<Item> breedFood = List.of(
            ItemRegistry.carrot,
            ItemRegistry.potato
    );

    public PigEntityMixin(World world) {
        super(world);
    }

    @Inject(
            method = "getDroppedItemId",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getDroppedItemId(CallbackInfoReturnable<Integer> cir) {
        EntityAccessor accessor = (EntityAccessor) this;
        Random random = accessor.getRandom();

        int porkchopAmount = random.nextInt(1, 4);
        int id = accessor.getFireTicks() > 0
                ? ItemRegistry.cookedPorkchop.id
                : ItemRegistry.rawPorkchop.id;

        accessor.callDropItem(id, porkchopAmount);

        // LivingEntity doesn't drop if getDroppedId returns 0
        cir.setReturnValue(0);
    }

    @Override
    protected List<Item> getBreedFood() {
        return breedFood;
    }
}
