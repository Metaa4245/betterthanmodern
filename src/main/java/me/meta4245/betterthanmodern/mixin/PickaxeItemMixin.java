package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.annotation.Pickaxe;
import me.meta4245.betterthanmodern.event.BlockRegistry;
import me.meta4245.betterthanmodern.mixin.accessor.ToolItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.PickaxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

import static me.meta4245.betterthanmodern.ReflectionHacks.fieldName;
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

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void append(CallbackInfo ci) {
        pickaxeEffectiveBlocks = (Block[]) getBlocks().stream()
                .filter(clazz -> clazz.isAnnotationPresent(Pickaxe.class))
                .map(clazz -> {
                    try {
                        Block x = (Block) BlockRegistry.class.getDeclaredField(fieldName(clazz)).get(null);
                        tiers.put(x, clazz.getAnnotation(Pickaxe.class).value());

                        return x;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray();
    }

    @Inject(method = "isSuitableFor", at = @At("HEAD"), cancellable = true)
    public void isSuitableFor(Block block, CallbackInfoReturnable<Boolean> cir) {
        ToolItemAccessor accessor = (ToolItemAccessor) this;

        Integer tier = tiers.get(block);
        if (tier == null) {
            cir.setReturnValue(
                    block.material == Material.STONE
                            || block.material == Material.METAL
            );

            return;
        }

        cir.setReturnValue(tier >= accessor.getToolMaterial().getMiningLevel());
    }
}
