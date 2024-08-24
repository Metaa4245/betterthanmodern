package me.meta4245.betterthanmodern.mixin;

import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Stats.class)
public abstract class StatsMixin {
    // TODO: this is hacky
    @Inject(method = "initializeCraftedItemStats", at = @At("HEAD"), cancellable = true)
    private static void initializeCraftedItemStats(CallbackInfo ci) {
        ci.cancel();
    }
}