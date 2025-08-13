package me.meta4245.betterthanmodern.item.foodcrop;

import me.meta4245.betterthanmodern.event.BlockRegistry;
import me.meta4245.betterthanmodern.item.template.TemplateFoodCropItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class Potato extends TemplateFoodCropItem {
    public Potato(Identifier identifier) {
        super(identifier, BlockRegistry.potatoes.id, 2, 64, true);
    }
}
