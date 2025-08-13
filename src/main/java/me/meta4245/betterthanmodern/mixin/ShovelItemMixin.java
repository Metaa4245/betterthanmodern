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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static me.meta4245.betterthanmodern.reflection.Utilities.*;

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
        ArrayList<Block> effective = getBlocks()
                .filter(c -> c.isAnnotationPresent(Shovel.class))
                .map(c -> {
                    Field f = getField(BlockRegistry.class, fieldName(c));
                    return (Block) getFieldValue(f);
                })
                .collect(Collectors.toCollection(ArrayList::new));

        effective.add(Block.DIRT);
        effective.add(Block.SAND);
        effective.add(Block.SNOW);
        effective.add(Block.CLAY);
        effective.add(Block.GRAVEL);
        effective.add(Block.FARMLAND);
        effective.add(Block.SNOW_BLOCK);
        effective.add(Block.GRASS_BLOCK);

        shovelEffectiveBlocks = effective.toArray(new Block[0]);
    }
}
