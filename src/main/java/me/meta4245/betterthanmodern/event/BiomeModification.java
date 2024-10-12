package me.meta4245.betterthanmodern.event;

import me.meta4245.betterthanmodern.worldgen.MelonPatchFeature;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeModificationEvent;

public class BiomeModification {
    public static Feature melonPatch = new MelonPatchFeature();

    @EventListener
    public void modifyBiome(BiomeModificationEvent event) {
        event.biome.addFeature(melonPatch);
    }
}
