package me.meta4245.betterthanmodern.event;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

import static me.meta4245.betterthanmodern.ReflectionHacks.*;

public class ItemRegistry {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE = Null.get();

    public static Item blocksDisc;
    public static Item carrot;
    public static Item catDisc;
    public static Item chirpDisc;
    public static Item elevenDisc;
    public static Item farDisc;
    public static Item glisteringMelon;
    public static Item goldenCarrot;
    public static Item goldNugget;
    public static Item mallDisc;
    public static Item mellohiDisc;
    public static Item melonSeeds;
    public static Item stalDisc;
    public static Item stradDisc;
    public static Item thirteenDisc;
    public static Item waitDisc;
    public static Item wardDisc;
    public static Item cookedChicken;
    public static Item cookedMutton;
    public static Item cookedPorkchop;
    public static Item steak;
    public static Item rawBeef;
    public static Item rawChicken;
    public static Item rawMutton;
    public static Item rawPorkchop;
    public static Item melonSlice;

    private Item item(Class<? extends Item> clazz) {
        String key = namespaceName(clazz);
        Item item;

        try {
            item = clazz.getConstructor(Identifier.class)
                    .newInstance(NAMESPACE.id(key))
                    .setTranslationKey(NAMESPACE, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return item;
    }

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        getFieldsOfType(ItemRegistry.class, Item.class).forEach(f -> {
            try {
                f.set(null, item(itemClass(className(f))));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
