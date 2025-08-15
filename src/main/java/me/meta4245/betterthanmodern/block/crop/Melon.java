package me.meta4245.betterthanmodern.block.crop;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;
import java.util.Random;

public class Melon extends TemplateBlock {
    private static final List<Integer> suitableBlocks = List.of(
            Block.DIRT.id,
            Block.GRASS_BLOCK.id
    );

    public Melon(Identifier identifier) {
        super(identifier, Material.PUMPKIN);
        super.setHardness(1);
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return random.nextInt(3, 8);
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return ItemRegistry.melonSlice.id;
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        return super.canPlaceAt(world, x, y, z)
                && suitableBlocks.contains(world.getBlockId(x, y - 1, z));
    }
}
