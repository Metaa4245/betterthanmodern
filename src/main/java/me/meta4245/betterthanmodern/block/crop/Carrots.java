package me.meta4245.betterthanmodern.block.crop;

import me.meta4245.betterthanmodern.block.template.TemplateCropBlock;
import me.meta4245.betterthanmodern.event.ItemRegistry;
import me.meta4245.betterthanmodern.math.Binomial;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.util.Identifier;

public class Carrots extends TemplateCropBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 7);

    public Carrots(Identifier identifier) {
        super(identifier);

        setDefaultState(getDefaultState().with(AGE, 0));
    }

    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected Item getSeedItem() {
        return null;
    }

    @Override
    protected int getSeedCount(int age) {
        return 0;
    }

    @Override
    protected Item getProduct(int age) {
        return ItemRegistry.carrot;
    }

    @Override
    protected int getProductCount(int age) {
        if (age != 7) {
            return 1;
        }

        return Binomial.rollRangePercent(2, 3, 57.14286);
    }
}
