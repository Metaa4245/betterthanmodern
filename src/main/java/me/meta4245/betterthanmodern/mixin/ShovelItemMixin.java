package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.annotation.Shovel;
import me.meta4245.betterthanmodern.event.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ShovelItem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.meta4245.betterthanmodern.ReflectionHacks.fieldName;
import static me.meta4245.betterthanmodern.ReflectionHacks.getBlocks;

@Mixin(ShovelItem.class)
public abstract class ShovelItemMixin {
    @Shadow
    private static Block[] shovelEffectiveBlocks;

    @Inject(method = "<clinit>", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/item/ShovelItem;shovelEffectiveBlocks:[Lnet/minecraft/block/Block;",
            opcode = Opcodes.PUTSTATIC,
            shift = At.Shift.AFTER))
    private static void append(CallbackInfo ci) {
        shovelEffectiveBlocks = (Block[]) getBlocks()
                .stream()
                .filter(c -> c.isAnnotationPresent(Shovel.class))
                .map(c -> {
                    try {
                        return (Block) BlockRegistry.class.getDeclaredField(fieldName(c)).get(null);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray();
    }
}
