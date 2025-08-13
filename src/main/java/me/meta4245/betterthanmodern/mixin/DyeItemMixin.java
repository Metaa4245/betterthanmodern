package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.block.template.TemplateCropBlock;
import me.meta4245.betterthanmodern.event.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;

import static me.meta4245.betterthanmodern.reflection.Utilities.*;

@Mixin(DyeItem.class)
public abstract class DyeItemMixin {
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void useOnBlock(
            ItemStack stack,
            PlayerEntity user,
            World world,
            int x,
            int y,
            int z,
            int side,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (stack.getDamage() != 15) {
            cir.setReturnValue(false);
        }

        int blockId = world.getBlockId(x, y, z);
        Block block = getBlocks().map(c -> {
            Field f = getField(BlockRegistry.class, fieldName(c));
            return (Block) getFieldValue(f);
        }).filter(b -> b.id == blockId).findFirst().orElse(null);

        if (block instanceof TemplateCropBlock crop) {
            if (!world.isRemote) {
                crop.applyFullGrowth(world, x, y, z);
                stack.count--;
            }

            cir.setReturnValue(true);
        }
    }
}