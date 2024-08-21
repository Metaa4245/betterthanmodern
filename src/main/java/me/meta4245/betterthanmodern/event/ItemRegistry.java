package me.meta4245.betterthanmodern.event;

import me.meta4245.betterthanmodern.item.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class ItemRegistry {
    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    private Item registerHelper(Class<? extends Item> clazz, String key) {
        Item i;
        try {
            i = clazz.getConstructor(Identifier.class).newInstance(NAMESPACE.id(key)).setTranslationKey(NAMESPACE, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    public static Item rawMutton;
    public static Item rawChicken;
    public static Item rawPorkchop;
    public static Item rawBeef;

    public static Item cookedChicken;
    public static Item cookedMutton;
    public static Item cookedPorkchop;
    public static Item steak;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        rawMutton = registerHelper(RawMutton.class, "raw_mutton");
        rawChicken = registerHelper(RawChicken.class, "raw_chicken");
        rawPorkchop = registerHelper(RawPorkchop.class, "raw_porkchop");
        rawBeef = registerHelper(RawBeef.class, "raw_beef");

        cookedChicken = registerHelper(CookedChicken.class, "cooked_chicken");
        cookedMutton = registerHelper(CookedMutton.class, "cooked_mutton");
        cookedPorkchop = registerHelper(CookedPorkchop.class, "cooked_porkchop");
        steak = registerHelper(Steak.class, "steak");
    }
}
