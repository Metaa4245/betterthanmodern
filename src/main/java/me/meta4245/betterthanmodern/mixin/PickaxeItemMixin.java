package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.annotation.Pickaxe;
import me.meta4245.betterthanmodern.event.BlockRegistry;
import me.meta4245.betterthanmodern.mixin.accessor.ToolItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.PickaxeItem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

import static me.meta4245.betterthanmodern.ReflectionHacks.field_name;
import static me.meta4245.betterthanmodern.ReflectionHacks.getBlocks;

@Mixin(PickaxeItem.class)
public abstract class PickaxeItemMixin {
    @Unique
    private static final Map<Block, Integer> tiers = new HashMap<>(
            Map.ofEntries(
                    Map.entry(Block.OBSIDIAN, 3),
                    Map.entry(Block.DIAMOND_BLOCK, 2),
                    Map.entry(Block.DIAMOND_ORE, 2),
                    Map.entry(Block.GOLD_BLOCK, 2),
                    Map.entry(Block.GOLD_ORE, 2),
                    Map.entry(Block.REDSTONE_ORE, 2),
                    Map.entry(Block.LIT_REDSTONE_ORE, 2),
                    Map.entry(Block.IRON_BLOCK, 1),
                    Map.entry(Block.IRON_ORE, 1),
                    Map.entry(Block.LAPIS_BLOCK, 1),
                    Map.entry(Block.LAPIS_ORE, 1)
            )
    );

    @Shadow
    private static Block[] pickaxeEffectiveBlocks;

    @Inject(method = "<clinit>", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/item/PickaxeItem;pickaxeEffectiveBlocks:[Lnet/minecraft/block/Block;",
            opcode = Opcodes.PUTSTATIC,
            shift = At.Shift.AFTER))
    private static void append(CallbackInfo ci) {
        List<Class<?>> classes;
        try {
            classes = getBlocks()
                    .stream()
                    .filter(clazz -> clazz.isAnnotationPresent(Pickaxe.class))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Block> blocks = new ArrayList<>(Arrays.asList(pickaxeEffectiveBlocks));
        for (Class<?> c : classes) {
            Block block;
            try {
                block = (Block) BlockRegistry.class.getDeclaredField(field_name(c)).get(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            blocks.add(block);
            tiers.put(block, block.getClass().getAnnotation(Pickaxe.class).tier());
        }

        pickaxeEffectiveBlocks = blocks.toArray(new Block[0]);
    }

    @Inject(method = "isSuitableFor", at = @At("HEAD"), cancellable = true)
    public void isSuitableFor(Block block, CallbackInfoReturnable<Boolean> cir) {
        ToolItemAccessor accessor = (ToolItemAccessor) this;

        Integer tier = tiers.get(block);
        if (tier == null) {
            cir.setReturnValue(block.material == Material.STONE || block.material == Material.METAL);
            // IntelliJ don't warn please
            return;
        }

        cir.setReturnValue(tier >= accessor.getToolMaterial().getMiningLevel());
    }
}
