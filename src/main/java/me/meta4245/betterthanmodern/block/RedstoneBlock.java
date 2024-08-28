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

    @Override
    public int getTickRate() {
        return 2;
    }

    @Override
    public boolean canEmitRedstonePower() {
        return true;
    }

    @Override
    public boolean isEmittingRedstonePowerInDirection(BlockView blockView, int x, int y, int z, int direction) {
        int side = blockView.getBlockMeta(x, y, z);
        if (side == 5 && direction == 1) {
            return false;
        } else if (side == 3 && direction == 3) {
            return false;
        } else if (side == 4 && direction == 2) {
            return false;
        } else if (side == 1 && direction == 5) {
            return false;
        } else {
            return side != 2 || direction != 4;
        }
    }

    @Override
    public boolean canTransferPowerInDirection(World world, int x, int y, int z, int direction) {
        return direction == 0
                &&
                this.isEmittingRedstonePowerInDirection(
                        world,
                        x,
                        y,
                        z,
                        direction
                );
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        if (world.getBlockMeta(x, y, z) == 0) {
            super.onPlaced(world, x, y, z);
        }

        world.notifyNeighbors(x, y - 1, z, this.id);
        world.notifyNeighbors(x, y + 1, z, this.id);
        world.notifyNeighbors(x - 1, y, z, this.id);
        world.notifyNeighbors(x + 1, y, z, this.id);
        world.notifyNeighbors(x, y, z - 1, this.id);
        world.notifyNeighbors(x, y, z + 1, this.id);
    }

    @Override
    public void onBreak(World world, int x, int y, int z) {
        world.notifyNeighbors(x, y - 1, z, this.id);
        world.notifyNeighbors(x, y + 1, z, this.id);
        world.notifyNeighbors(x - 1, y, z, this.id);
        world.notifyNeighbors(x + 1, y, z, this.id);
        world.notifyNeighbors(x, y, z - 1, this.id);
        world.notifyNeighbors(x, y, z + 1, this.id);
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        super.neighborUpdate(world, x, y, z, id);

        world.scheduleBlockUpdate(x, y, z, this.id, this.getTickRate());
    }
}
