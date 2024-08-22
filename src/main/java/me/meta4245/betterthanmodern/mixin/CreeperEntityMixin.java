package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.Mod;
import me.meta4245.betterthanmodern.config.Discs;
import me.meta4245.betterthanmodern.event.ItemRegistry;
import me.meta4245.betterthanmodern.mixin.accessor.EntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin {
    @Unique
    private final int[] discs = {
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
            ItemRegistry.wardDisc.id,
    };

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MonsterEntity;onKilledBy(Lnet/minecraft/entity/Entity;)V", shift = At.Shift.AFTER), method = "onKilledBy", cancellable = true)
    public void onKilledBy(Entity entity, CallbackInfo ci) {
        Discs d = Mod.config.discs;

        // TODO: truly amazing code here
        if (d.blocks || d.stal || d.cat || d.chirp || d.eleven || d.far || d.mall || d.mellohi || d.strad || d.thirteen || d.wait || d.ward) {
            EntityAccessor accessor = (EntityAccessor) this;
            Random random = accessor.getRandom();
            if (entity instanceof SkeletonEntity) {
                accessor.callDropItem(discs[random.nextInt(discs.length)], 1);
            }
            ci.cancel();
        }
    }
}
