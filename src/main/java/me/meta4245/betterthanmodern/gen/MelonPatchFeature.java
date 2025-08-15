package me.meta4245.betterthanmodern.gen;

import me.meta4245.betterthanmodern.event.BlockRegistry;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.List;
import java.util.Random;

public class MelonPatchFeature extends Feature {
    public static List<String> suitableBiomes = List.of(
            "Plains",
            "Forest",
            "Seasonal Forest"
    );

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if (random.nextInt(32) != 0) {
            return false;
        }

        for (int i = 0; i < 64; i++) {
            int dx = x + random.nextInt(8) - random.nextInt(8);
            int dy = y + random.nextInt(4) - random.nextInt(4);
            int dz = z + random.nextInt(8) - random.nextInt(8);

            if (!world.isAir(dx, dy, dz)) {
                continue;
            }

            if (!BlockRegistry.melon.canPlaceAt(world, dx, dy, dz)) {
                continue;
            }

            world.setBlockWithoutNotifyingNeighbors(
                    dx,
                    dy,
                    dz,
                    BlockRegistry.melon.id
            );
        }

        return true;
    }
}
