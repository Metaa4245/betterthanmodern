package me.meta4245.betterthanmodern.event;

import me.meta4245.betterthanmodern.item.disc.*;
import me.meta4245.betterthanmodern.item.food.*;
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
    public static Item rawMutton;
    public static Item rawChicken;
    public static Item rawPorkchop;
    public static Item rawBeef;
    public static Item cookedChicken;
    public static Item cookedMutton;
    public static Item cookedPorkchop;
    public static Item steak;
    public static Item stalDisc;
    public static Item blocksDisc;
    public static Item catDisc;
    public static Item chirpDisc;
    public static Item elevenDisc;
    public static Item farDisc;
    public static Item mallDisc;
    public static Item mellohiDisc;
    public static Item stradDisc;
    public static Item thirteenDisc;
    public static Item waitDisc;
    public static Item wardDisc;

    private Item item(Class<? extends Item> clazz) {
        StringBuilder name = new StringBuilder();
        String class_name = clazz.getSimpleName();
        int len = class_name.length();

        for (int i = 0; i < len; i++) {
            char c = class_name.charAt(i);
            if (Character.isUpperCase(c) && i != len - 1 && i != 0) {
                name.append("_");
            }
            name.append(Character.toLowerCase(c));
        }

        String key = name.toString();
        Item i;

        try {
            i = clazz.getConstructor(Identifier.class).newInstance(NAMESPACE.id(key)).setTranslationKey(NAMESPACE, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return i;
    }

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        rawMutton = item(RawMutton.class);
        cookedMutton = item(CookedMutton.class);
        rawChicken = item(RawChicken.class);
        cookedChicken = item(CookedChicken.class);
        rawPorkchop = item(RawPorkchop.class);
        cookedPorkchop = item(CookedPorkchop.class);
        rawBeef = item(RawBeef.class);
        steak = item(Steak.class);
        stalDisc = item(StalDisc.class);
        blocksDisc = item(BlocksDisc.class);
        catDisc = item(CatDisc.class);
        chirpDisc = item(ChirpDisc.class);
        elevenDisc = item(ElevenDisc.class);
        farDisc = item(FarDisc.class);
        mallDisc = item(MallDisc.class);
        mellohiDisc = item(MellohiDisc.class);
        stradDisc = item(StradDisc.class);
        thirteenDisc = item(ThirteenDisc.class);
        waitDisc = item(WaitDisc.class);
        wardDisc = item(WardDisc.class);
    }
}
