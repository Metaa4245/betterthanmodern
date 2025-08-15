package me.meta4245.betterthanmodern.event;

import me.meta4245.betterthanmodern.gen.MelonPatchFeature;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeModificationEvent;

public class FeatureRegistry {
    @EventListener
    public void registerModifications(BiomeModificationEvent event) {
        if (MelonPatchFeature.suitableBiomes.contains(event.biome.name)) {
            MelonPatchFeature feature = new MelonPatchFeature();

            event.biome.addFeature(feature);
        }
    }
}
