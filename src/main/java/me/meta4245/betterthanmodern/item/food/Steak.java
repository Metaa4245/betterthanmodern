package me.meta4245.betterthanmodern.item.food;

import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class Steak extends TemplateStackableFoodItem {
    public Steak(Identifier identifier) {
        super(identifier, 10, true, 64);
    }
}