package me.meta4245.betterthanmodern.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.meta4245.betterthanmodern.mixin.accessor.EntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {
    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/vehicle/BoatEntity;horizontalCollision:Z"
            )
    )
    public boolean noBreaking(boolean original) {
        return false;
    }

    @ModifyConstant(
            method = "tick",
            constant = @Constant(
                    doubleValue = 0.9900000095367432
            )
    )
    public double modifyXZSpeed(double constant) {
        return 1.5D;
    }

    @Inject(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/BoatEntity;dropItem(IIF)Lnet/minecraft/entity/ItemEntity;"
            ),
            cancellable = true
    )
    public void dropBoat(
            Entity source,
            int amount,
            @NotNull CallbackInfoReturnable<Boolean> cir
    ) {
        EntityAccessor accessor = (EntityAccessor) this;

        accessor.callDropItem(Item.BOAT.id, 1);
        accessor.callMarkDead();
        cir.setReturnValue(true);
    }
}
