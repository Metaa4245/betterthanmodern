package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.entity.living.animal.ChickenEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChickenEntity.class)
public class ChickenEntityMixin extends AnimalEntityMixin {
    public ChickenEntityMixin(Level level) {
        super(level);
    }

    @Override
    public boolean betterthanmodern$isFoodItem(ItemStack item) {
        return item != null && item.getType() == Item.seeds;
    }

    @Inject(at = @At("HEAD"), method = "getMobDrops", cancellable = true)
    public void getMobDrops(CallbackInfoReturnable<Integer> cir) {
        ChickenEntity thisObject = (ChickenEntity) (Object) this;
        cir.setReturnValue(
                thisObject.fire > 0
                        ? ItemRegistry.cookedChicken.id
                        : ItemRegistry.rawChicken.id
        );
    }
}
