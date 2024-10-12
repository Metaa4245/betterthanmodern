package me.meta4245.betterthanmodern.worldgen;

import me.meta4245.betterthanmodern.event.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class MelonPatchFeature extends Feature {
    public MelonPatchFeature() {}

    public boolean willGenerate(Random random) {
        return random.nextInt(11) == 5;
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if (!willGenerate(random)) return false;

        for(int i = 0; i < 64; ++i) {
            int randX = x + random.nextInt(8) - random.nextInt(8);
            int randY = y + random.nextInt(4) - random.nextInt(4);
            int randZ = z + random.nextInt(8) - random.nextInt(8);

            Block melon = BlockRegistry.melonBlock;
            boolean isAir = world.isAir(randX, randY, randZ);
            boolean canPlace = melon.canPlaceAt(world, randX, randY, randZ);
            int blockId = world.getBlockId(randX, randY - 1, randZ);

            if (isAir && blockId == Block.GRASS_BLOCK.id && canPlace) {
                world.setBlockWithoutNotifyingNeighbors(
                        randX,
                        randY,
                        randZ,
                        melon.id,
                        random.nextInt(4)
                );
            }
        }

        return true;
    }
}
