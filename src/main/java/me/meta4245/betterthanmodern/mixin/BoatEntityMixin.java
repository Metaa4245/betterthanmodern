package me.meta4245.betterthanmodern.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BoatEntity.class)
public class BoatEntityMixin {
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

    @ModifyConstant(
            method = "tick",
            constant = @Constant(
                    doubleValue = 0.949999988079071
            )
    )
    public double modifyYSpeed(double constant) {
        return 1.45D;
    }
}
