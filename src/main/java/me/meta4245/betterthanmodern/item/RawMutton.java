package me.meta4245.betterthanmodern.item;

import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class RawMutton extends TemplateStackableFoodItem {
    public RawMutton(Identifier identifier) {
        super(identifier, 2, true, 64);
    }
}
