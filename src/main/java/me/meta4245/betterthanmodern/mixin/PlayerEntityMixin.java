package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.IBreeding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Redirect(
            method = "interact",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;interact(Lnet/minecraft/entity/player/PlayerEntity;)Z"
            )
    )
    public boolean redirectInteract(Entity entity, PlayerEntity player) {
        if (!(entity instanceof AnimalEntity)) {
            return entity.interact(player);
        }
        return ((IBreeding) entity).btm$interact(player);
    }
}
