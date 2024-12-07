package me.meta4245.betterthanmodern.item;

import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class GoldenCarrot extends TemplateStackableFoodItem {
    public GoldenCarrot(Identifier identifier) {
        super(identifier, 15, true, 64);
    }
}

