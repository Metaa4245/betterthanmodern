package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.annotation.Axe;
import me.meta4245.betterthanmodern.event.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.meta4245.betterthanmodern.ReflectionHacks.fieldName;
import static me.meta4245.betterthanmodern.ReflectionHacks.getBlocks;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {
    @Shadow
    private static Block[] axeEffectiveBlocks;

    @Inject(method = "<clinit>", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/item/AxeItem;axeEffectiveBlocks:[Lnet/minecraft/block/Block;",
            opcode = Opcodes.PUTSTATIC,
            shift = At.Shift.AFTER))
    private static void append(CallbackInfo ci) {
        axeEffectiveBlocks = (Block[]) getBlocks()
                .stream()
                .filter(clazz -> clazz.isAnnotationPresent(Axe.class))
                .map(clazz -> {
                    try {
                        return (Block) BlockRegistry.class.getDeclaredField(fieldName(clazz)).get(null);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray();
    }
}
