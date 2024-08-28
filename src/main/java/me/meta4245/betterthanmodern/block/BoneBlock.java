package me.meta4245.betterthanmodern.block;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class BoneBlock extends TemplateBlock {
    public BoneBlock(Identifier identifier) {
        // TODO: is this the correct Material?
        super(identifier, Material.SOLID_ORGANIC);
        super.setHardness(2);
        super.setResistance(2);
    }
}
