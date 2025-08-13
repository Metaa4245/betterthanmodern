package me.meta4245.betterthanmodern.item.template;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.ItemTemplate;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class TemplateCropItem
        extends TemplateItem
        implements ItemTemplate {
    private final int cropBlockId;

    public TemplateCropItem(Identifier identifier, int cropBlockId) {
        super(identifier);

        this.cropBlockId = cropBlockId;
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

        world.setBlock(x, y + 1, z, cropBlockId);
        stack.count--;

        return true;
    }
}
