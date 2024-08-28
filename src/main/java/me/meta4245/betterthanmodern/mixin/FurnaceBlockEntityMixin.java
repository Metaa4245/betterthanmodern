package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.BlockRegistry;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FurnaceBlockEntity.class)
public abstract class FurnaceBlockEntityMixin {
    @Inject(
            method = "getFuelTime",
            at = @At("RETURN"),
            cancellable = true
    )
    public void getFuelTime(ItemStack i, CallbackInfoReturnable<Integer> cir) {
        if (i.getItem().id == BlockRegistry.coalBlock.asItem().id) {
            cir.setReturnValue(16000);
        }
    }
}
