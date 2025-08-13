package me.meta4245.betterthanmodern.block.crop;

import me.meta4245.betterthanmodern.block.template.TemplateCropBlock;
import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

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
    protected int getSeedCount() {
        return 0;
    }

    @Override
    protected int getProductId() {
        return ItemRegistry.carrot.id;
    }

    @Override
    protected int getProductCount(Random random) {
        return random.nextInt(2, 6);
    }
}