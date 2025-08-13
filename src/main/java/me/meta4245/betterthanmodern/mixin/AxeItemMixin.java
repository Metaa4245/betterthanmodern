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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static me.meta4245.betterthanmodern.reflection.Utilities.*;

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
        ArrayList<Block> effective = getBlocks()
                .filter(c -> c.isAnnotationPresent(Axe.class))
                .map(c -> {
                    Field f = getField(BlockRegistry.class, fieldName(c));
                    return (Block) getFieldValue(f);
                })
                .collect(Collectors.toCollection(ArrayList::new));

        effective.add(Block.LOG);
        effective.add(Block.CHEST);
        effective.add(Block.PLANKS);
        effective.add(Block.BOOKSHELF);

        axeEffectiveBlocks = effective.toArray(new Block[0]);
    }
}
