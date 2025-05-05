package me.meta4245.betterthanmodern.mixin;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public final class BetterThanModernMixinPlugin implements IMixinConfigPlugin {

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.equals("me.meta4245.betterthanmodern.mixin.CreeperEntityMixin")) {
            if (FabricLoader.getInstance().isModLoaded("musicdiscs")) {
                System.out.println("MusicDisc mod detected! Using music discs from MusicDisc mod instead of BetterThanModern.");
                return false;
            }
        }

        return true;
    }

    // Boilerplate

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
