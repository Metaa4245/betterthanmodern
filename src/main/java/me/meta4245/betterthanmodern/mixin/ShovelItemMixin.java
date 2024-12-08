package me.meta4245.betterthanmodern.mixin;

import com.google.common.reflect.ClassPath;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static me.meta4245.betterthanmodern.ReflectionHacks.field_name;

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
        Set<Class<?>> classes;
        try {
            classes = ClassPath.from(ClassLoader.getSystemClassLoader())
                    .getAllClasses()
                    .stream()
                    .filter(clazz -> clazz.getPackageName().startsWith("me.meta4245.betterthanmodern.block"))
                    .filter(clazz -> clazz.getClass().isAnnotationPresent(Shovel.class))
                    .map(ClassPath.ClassInfo::load)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Block> blocks = new ArrayList<>(Arrays.asList(shovelEffectiveBlocks));
        for (Class<?> c : classes) {
            Block block;
            try {
                block = (Block) BlockRegistry.class.getField(field_name(c)).get(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            blocks.add(block);
        }

        shovelEffectiveBlocks = blocks.toArray(new Block[0]);
    }
}