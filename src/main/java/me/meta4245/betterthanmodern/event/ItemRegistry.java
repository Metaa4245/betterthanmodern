package me.meta4245.betterthanmodern.event;

import me.meta4245.betterthanmodern.item.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class ItemRegistry {
    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Item rawMutton;
    public static Item rawChicken;
    public static Item rawPorkchop;
    public static Item rawBeef;

    public static Item cookedChicken;
    public static Item cookedMutton;
    public static Item cookedPorkchop;
    public static Item steak;

    public static int rawMuttonId = 0;
    public static int rawChickenId = 0;
    public static int rawPorkchopId = 0;
    public static int rawBeefId = 0;
    public static int cookedChickenId = 0;
    public static int cookedMuttonId = 0;
    public static int cookedPorkchopId = 0;
    public static int steakId = 0;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        rawMutton = new RawMutton(NAMESPACE.id("raw_mutton")).setTranslationKey(NAMESPACE, "raw_mutton");
        rawChicken = new RawChicken(NAMESPACE.id("raw_chicken")).setTranslationKey(NAMESPACE, "raw_chicken");
        rawPorkchop = new RawPorkchop(NAMESPACE.id("raw_porkchop")).setTranslationKey(NAMESPACE, "raw_porkchop");
        rawBeef = new RawBeef(NAMESPACE.id("raw_beef")).setTranslationKey(NAMESPACE, "raw_beef");

        cookedChicken = new CookedChicken(NAMESPACE.id("cooked_chicken")).setTranslationKey(NAMESPACE, "cooked_chicken");
        cookedMutton = new CookedMutton(NAMESPACE.id("cooked_mutton")).setTranslationKey(NAMESPACE, "cooked_mutton");
        cookedPorkchop = new CookedPorkchop(NAMESPACE.id("cooked_porkchop")).setTranslationKey(NAMESPACE, "cooked_porkchop");
        steak = new Steak(NAMESPACE.id("steak")).setTranslationKey(NAMESPACE, "steak");

        rawMuttonId = rawMutton.id;
        rawChickenId = rawChicken.id;
        rawPorkchopId = rawPorkchop.id;
        rawBeefId = rawBeef.id;
        cookedChickenId = cookedChicken.id;
        cookedMuttonId = cookedMutton.id;
        cookedPorkchopId = cookedPorkchop.id;
        steakId = steak.id;
    }
}
