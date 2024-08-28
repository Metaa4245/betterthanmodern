package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import me.meta4245.betterthanmodern.mixin.accessor.EntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin {
    @Unique
    private static final int[] ids = {
            ItemRegistry.blocksDisc.id,
            ItemRegistry.catDisc.id,
            ItemRegistry.chirpDisc.id,
            ItemRegistry.elevenDisc.id,
            ItemRegistry.farDisc.id,
            ItemRegistry.mallDisc.id,
            ItemRegistry.mellohiDisc.id,
            ItemRegistry.stalDisc.id,
            ItemRegistry.stradDisc.id,
            ItemRegistry.thirteenDisc.id,
            ItemRegistry.waitDisc.id,
            ItemRegistry.wardDisc.id
    };

    @Inject(
            method = "onKilledBy",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/CreeperEntity;dropItem(II)Lnet/minecraft/entity/ItemEntity;"
            ),
            cancellable = true
    )
    private void onKilledBy(Entity par1, CallbackInfo ci) {
        EntityAccessor accessor = (EntityAccessor) this;
        Random random = accessor.getRandom();

        accessor.callDropItem(
                ids[random.nextInt(ids.length)],
                1
        );
        ci.cancel();
    }
}