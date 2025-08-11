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
        String key = namespaceName(clazz);
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
        getFieldsOfType(BlockRegistry.class, Block.class).forEach(f -> {
            Class<? extends Block> clazz;
            try {
                clazz = blockClass(className(f));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Block block = block(clazz);

            try {
                f.set(null, block);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Fuel anno = clazz.getAnnotation(Fuel.class);
            if (anno != null) {
                FuelRegistry.addFuelItem(block.asItem(), anno.value());
            }
        });
    }
}
