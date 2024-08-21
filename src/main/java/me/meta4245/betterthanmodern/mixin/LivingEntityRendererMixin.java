package me.meta4245.betterthanmodern.mixin;

import me.meta4245.betterthanmodern.IBreeding;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.living.LivingEntity;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(method = "method_822", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glScalef(FFF)V", shift = At.Shift.AFTER, remap = false))
    private void makeSmall(LivingEntity entity, double x, double y, double z, float a, float b, CallbackInfo ci) {
        if (entity instanceof IBreeding && ((IBreeding) entity).betterthanmodern$isBaby()) {
            GL11.glScalef(0.66f, 0.66f, 0.66f);
        }
    }
}
