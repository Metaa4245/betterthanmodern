package me.meta4245.betterthanmodern.item.foodcrop;

import me.meta4245.betterthanmodern.event.BlockRegistry;
import me.meta4245.betterthanmodern.item.template.TemplateFoodCropItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class Carrot extends TemplateFoodCropItem {
    public Carrot(Identifier identifier) {
        super(identifier, BlockRegistry.carrots.id, 2, 64, true);
    }
}
