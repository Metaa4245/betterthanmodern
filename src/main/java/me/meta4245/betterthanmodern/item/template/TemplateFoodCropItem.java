package me.meta4245.betterthanmodern.item.template;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public abstract class TemplateFoodCropItem extends TemplateStackableFoodItem {
    public TemplateFoodCropItem(
            Identifier identifier,
            int healAmount,
            int stackAmount,
            boolean isWolfFood
    ) {
        super(identifier, healAmount, isWolfFood, stackAmount);
    }

    protected abstract int getCropBlockId();

    @Override
    // any foodcrop is not meat
    public boolean isMeat() {
        return false;
    }

    @Override
    public boolean useOnBlock(
            ItemStack stack,
            PlayerEntity user,
            World world,
            int x,
            int y,
            int z,
            int side
    ) {
        if (side != 1) {
            return false;
        }

        int blockId = world.getBlockId(x, y, z);
        if (blockId != Block.FARMLAND.id) {
            return false;
        }

        if (!world.isAir(x, y + 1, z)) {
            return false;
        }

        world.setBlock(x, y + 1, z, getCropBlockId());
        stack.count--;

        return true;
    }
}
