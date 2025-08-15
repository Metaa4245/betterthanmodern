package me.meta4245.betterthanmodern.block;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class SmoothStone extends TemplateBlock {
    public SmoothStone(Identifier identifier) {
        super(identifier, Material.STONE);

        super.setHardness(2);
        super.setResistance(6);
    }
}
