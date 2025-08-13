package me.meta4245.betterthanmodern.item.crop;

import me.meta4245.betterthanmodern.event.BlockRegistry;
import me.meta4245.betterthanmodern.item.template.TemplateCropItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class MelonSeeds extends TemplateCropItem {
    public MelonSeeds(Identifier identifier) {
        super(identifier, BlockRegistry.melonStem.id);
    }
}
