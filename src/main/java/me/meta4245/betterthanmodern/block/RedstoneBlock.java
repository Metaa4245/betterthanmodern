package me.meta4245.betterthanmodern.block;

import me.meta4245.betterthanmodern.annotation.Pickaxe;
import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

@Pickaxe
public class RedstoneBlock extends TemplateBlock {
    public RedstoneBlock(Identifier identifier) {
        super(identifier, Material.METAL);

        super.setHardness(5);
        super.setResistance(6);
    }

    @Override
    public boolean canEmitRedstonePower() {
        return true;
    }

    @Override
    public boolean isEmittingRedstonePowerInDirection(
            BlockView blockView,
            int x,
            int y,
            int z,
            int direction
    ) {
        return true;
    }

    @Override
    public boolean canTransferPowerInDirection(
            World world,
            int x,
            int y,
            int z,
            int direction
    ) {
        return true;
    }
}
