package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChickenEntity.class)
public class ChickenEntityMixin extends AnimalEntityMixin {
    public ChickenEntityMixin(World world) {
        super(world);
    }

    @Override
    public boolean betterthanmodern$isFoodItem(ItemStack item) {
        return item != null && item.getItem() == Item.SEEDS;
    }

    @Inject(at = @At("HEAD"), method = "getDroppedId", cancellable = true)
    public void getDroppedId(CallbackInfoReturnable<Integer> cir) {
        ChickenEntity thisObject = (ChickenEntity) (Object) this;
        cir.setReturnValue(
                thisObject.fireTicks > 0
                        ? ItemRegistry.cookedChicken.id
                        : ItemRegistry.rawChicken.id
        );
    }
}
