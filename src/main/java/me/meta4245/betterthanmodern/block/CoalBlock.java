package me.meta4245.betterthanmodern.block;

import me.meta4245.betterthanmodern.annotation.Pickaxe;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

@Pickaxe
public class CoalBlock extends TemplateBlock {
    public CoalBlock(Identifier identifier) {
        super(identifier, Material.METAL);
        super.setHardness(5);
        super.setResistance(6);
    }
}
