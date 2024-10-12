package me.meta4245.betterthanmodern.event;

import me.meta4245.betterthanmodern.worldgen.MelonPatchFeature;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeModificationEvent;

public class FeatureRegistry {
    public static Feature melonPatch;

    @EventListener
    public void registerFeatures(BiomeModificationEvent event) {
        melonPatch = new MelonPatchFeature();

        event.biome.addFeature(melonPatch);
    }
}
