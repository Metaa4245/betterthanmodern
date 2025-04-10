package me.meta4245.betterthanmodern.event;

import me.meta4245.betterthanmodern.annotation.Fuel;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.recipe.FuelRegistry;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

import java.lang.reflect.Field;
import java.util.List;

import static me.meta4245.betterthanmodern.ReflectionHacks.*;

public class BlockRegistry {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE = Null.get();

    public static Block smoothStone;
    public static Block coalBlock;
    public static Block redstoneBlock;
    public static Block stoneBricks;
    public static Block melon;

    private Block block(Class<? extends Block> clazz) {
        String key = namespace_name(clazz);
        Block block;

        try {
            block = clazz.getConstructor(Identifier.class)
                    .newInstance(NAMESPACE.id(key))
                    .setTranslationKey(NAMESPACE, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return block;
    }

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        List<Field> fields = getFieldsOfType(BlockRegistry.class, Block.class);

        for (Field f : fields) {
            try {
                Class<? extends Block> clazz = block_class(class_name(f));
                f.set(null, block(clazz));

                Fuel anno = clazz.getAnnotation(Fuel.class);
                if (anno != null) {
                    Block fieldBlock = (Block)f.get(null);
                    FuelRegistry.addFuelItem(fieldBlock.asItem(), anno.value());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
