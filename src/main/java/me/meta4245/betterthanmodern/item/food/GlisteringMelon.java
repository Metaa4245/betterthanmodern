package me.meta4245.betterthanmodern.item.food;

import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class GlisteringMelon extends TemplateStackableFoodItem {
    public GlisteringMelon(Identifier identifier) {
        super(identifier, 15, true, 64);
    }

    @Override
    public boolean isMeat() {
        return false;
    }
}
