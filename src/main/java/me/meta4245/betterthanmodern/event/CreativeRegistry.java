package me.meta4245.betterthanmodern.event;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import org.jetbrains.annotations.NotNull;
import paulevs.bhcreative.api.CreativeTab;
import paulevs.bhcreative.api.SimpleTab;
import paulevs.bhcreative.registry.TabRegistryEvent;

import static me.meta4245.betterthanmodern.ReflectionHacks.getFieldsOfType;

public class CreativeRegistry {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE = Null.get();

    public static CreativeTab tab;

    @EventListener
    public void onTabInit(@NotNull TabRegistryEvent event) {
        tab = new SimpleTab(NAMESPACE.id("tab"), new ItemStack(ItemRegistry.melonSlice));
        event.register(tab);

        getFieldsOfType(ItemRegistry.class, Item.class).forEach(f -> {
            try {
                tab.addItem(new ItemStack((Item) f.get(null)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        getFieldsOfType(BlockRegistry.class, Block.class).forEach(f -> {
            try {
                tab.addItem(new ItemStack((Block) f.get(null)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
