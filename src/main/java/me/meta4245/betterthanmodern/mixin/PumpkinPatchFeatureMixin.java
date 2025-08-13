package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.PumpkinPatchFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(PumpkinPatchFeature.class)
public abstract class PumpkinPatchFeatureMixin {
    // TODO: not sure if multiple field accesses can ruin this, probably
    @Redirect(
            method = "generate",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/Block;PUMPKIN:Lnet/minecraft/block/Block;"
            )
    )
    public Block generateMelons() {
        return new Random().nextBoolean()
                ? BlockRegistry.melon
                : Block.PUMPKIN;
    }
}
