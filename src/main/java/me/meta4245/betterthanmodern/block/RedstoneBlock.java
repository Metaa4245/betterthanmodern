package me.meta4245.betterthanmodern.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class RedstoneBlock extends TemplateBlock {
    public RedstoneBlock(Identifier identifier) {
        super(identifier, Material.METAL);

        super.setHardness(5);
        super.setResistance(6);
    }

    private void notify(World world, int x, int y, int z) {
        world.notifyNeighbors(x - 1, y, z, this.id);
        world.notifyNeighbors(x + 1, y, z, this.id);
        world.notifyNeighbors(x, y - 1, z, this.id);
        world.notifyNeighbors(x, y + 1, z, this.id);
        world.notifyNeighbors(x, y, z - 1, this.id);
        world.notifyNeighbors(x, y, z + 1, this.id);
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        super.onPlaced(world, x, y, z);

        notify(world, x, y, z);
    }

    @Override
    public void onBreak(World world, int x, int y, int z) {
        super.onBreak(world, x, y, z);

        notify(world, x, y, z);
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
