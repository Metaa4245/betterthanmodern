package me.meta4245.betterthanmodern.item;

import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class CookedMutton extends TemplateStackableFoodItem {
    public CookedMutton(Identifier identifier) {
        super(identifier, 10, true, 64);
    }
}