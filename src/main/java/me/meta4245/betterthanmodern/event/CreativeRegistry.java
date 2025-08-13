package me.meta4245.betterthanmodern.event;

import me.meta4245.betterthanmodern.block.template.TemplateCropBlock;
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

import java.lang.reflect.Field;

import static me.meta4245.betterthanmodern.reflection.Utilities.*;

public class CreativeRegistry {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE = Null.get();

    public static CreativeTab tab;

    @EventListener
    public void onTabInit(@NotNull TabRegistryEvent event) {
        tab = new SimpleTab(NAMESPACE.id("tab"), new ItemStack(ItemRegistry.melonSlice));
        event.register(tab);

        getItems().forEach(i -> {
            Field f = getField(ItemRegistry.class, fieldName(i));
            tab.addItem(new ItemStack((Item) getFieldValue(f)));
        });
        getBlocks().forEach(b -> {
            Field f = getField(BlockRegistry.class, fieldName(b));
            Block block = (Block) getFieldValue(f);

            if (block instanceof TemplateCropBlock) {
                return;
            }

            tab.addItem(new ItemStack(block));
        });
    }
}
