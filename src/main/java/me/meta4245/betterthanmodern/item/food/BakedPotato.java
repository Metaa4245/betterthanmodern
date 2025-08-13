package me.meta4245.betterthanmodern.item.food;

import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class BakedPotato extends TemplateStackableFoodItem {
    public BakedPotato(Identifier identifier) {
        super(identifier, 5, true, 64);
    }

    @Override
    public boolean isMeat() {
        return false;
    }
}
