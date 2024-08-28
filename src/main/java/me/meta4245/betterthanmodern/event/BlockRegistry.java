package me.meta4245.betterthanmodern.event;

import me.meta4245.betterthanmodern.ReflectionHacks;
import me.meta4245.betterthanmodern.block.CoalBlock;
import me.meta4245.betterthanmodern.block.RedstoneBlock;
import me.meta4245.betterthanmodern.block.SmoothStone;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class BlockRegistry {
    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Block smoothStone;
    public static Block coalBlock;
    public static Block redstoneBlock;

    private Block block(Class<? extends Block> clazz) {
        String key = ReflectionHacks.get_name(clazz);
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
        smoothStone = block(SmoothStone.class);
        coalBlock = block(CoalBlock.class);
        redstoneBlock = block(RedstoneBlock.class);
    }
}
