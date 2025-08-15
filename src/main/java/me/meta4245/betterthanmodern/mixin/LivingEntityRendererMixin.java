package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.IBreeding;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(
            method = "render(Lnet/minecraft/entity/LivingEntity;DDDFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/opengl/GL11;glScalef(FFF)V",
                    shift = At.Shift.AFTER
            )
    )
    public void makeSmall(
            LivingEntity entity,
            double x,
            double y,
            double z,
            float yaw,
            float partialTick,
            CallbackInfo ci
    ) {
        if (!(entity instanceof IBreeding)) {
            return;
        }

        if (!((IBreeding) entity).btm$isBaby()) {
            return;
        }

        GL11.glScalef(0.66f, 0.66f, 0.66f);
    }
}
