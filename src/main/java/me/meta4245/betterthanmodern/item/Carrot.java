package me.meta4245.betterthanmodern.item;

import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class Carrot extends TemplateStackableFoodItem {
    public Carrot(Identifier identifier) {
        super(identifier, 2, true, 64);
    }
}
