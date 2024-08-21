package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.monster.CreeperEntity;
import net.minecraft.entity.living.monster.SkeletonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin {
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

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/monster/MonsterEntity;onKilledBy(Lnet/minecraft/entity/Entity;)V", shift = At.Shift.AFTER), method = "onKilledBy", cancellable = true)
    public void onKilledBy(Entity entity, CallbackInfo ci) {
        CreeperEntity thisObject = (CreeperEntity) (Object) this;
        Random random = ((EntityAccessor) thisObject).getRandom();
        if (entity instanceof SkeletonEntity) {
            thisObject.dropItem(discs[random.nextInt(discs.length)], 1);
        }
        ci.cancel();
    }
}
