package me.meta4245.betterthanmodern.block;

import me.meta4245.betterthanmodern.annotation.Fuel;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

@Fuel(800)
public class CoalBlock extends TemplateBlock {
    public CoalBlock(Identifier identifier) {
        super(identifier, Material.METAL);

        super.setHardness(5);
        super.setResistance(6);
    }
}
