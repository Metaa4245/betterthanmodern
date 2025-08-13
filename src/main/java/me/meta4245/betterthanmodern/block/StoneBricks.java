package me.meta4245.betterthanmodern.block;

import me.meta4245.betterthanmodern.annotation.Pickaxe;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

@Pickaxe
public class StoneBricks extends TemplateBlock {
    public StoneBricks(Identifier identifier) {
        super(identifier, Material.STONE);

        super.setHardness(1.5F);
        super.setResistance(6);
    }
}
