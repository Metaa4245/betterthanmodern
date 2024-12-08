package me.meta4245.betterthanmodern.block;

import me.meta4245.betterthanmodern.annotation.Axe;
import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

@Axe
public class Melon extends TemplateBlock {
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
}
