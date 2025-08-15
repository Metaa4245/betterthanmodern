package me.meta4245.betterthanmodern.mixin;

import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneTorchBlock.class)
public abstract class RedstoneTorchBlockMixin {
    @Inject(method = "isBurnedOut", at = @At("HEAD"), cancellable = true)
    public void noBurnOut(
            World world,
            int x,
            int y,
            int z,
            boolean addNew,
            CallbackInfoReturnable<Boolean> cir
    ) {
        cir.setReturnValue(false);
    }
}
