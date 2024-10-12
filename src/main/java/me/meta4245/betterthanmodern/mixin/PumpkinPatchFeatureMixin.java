package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.PumpkinPatchFeature;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(PumpkinPatchFeature.class)
public class PumpkinPatchFeatureMixin {
    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    public void generateMelons(
            World world,
            @NotNull Random random,
            int x,
            int y,
            int z,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (random.nextBoolean()) {
            for (int i = 0; i < 64; ++i) {
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
            cir.setReturnValue(true);
        }
    }
}
