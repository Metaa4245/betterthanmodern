package me.meta4245.betterthanmodern.event;

import me.meta4245.betterthanmodern.ReflectionHacks;
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
    private final static Namespace NAMESPACE = Null.get();

    public static Item blocksDisc;
    public static Item catDisc;
    public static Item chirpDisc;
    public static Item elevenDisc;
    public static Item farDisc;
    public static Item mallDisc;
    public static Item mellohiDisc;
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

    private Item item(Class<? extends Item> clazz) {
        String key = ReflectionHacks.get_name(clazz);
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
        blocksDisc = item(BlocksDisc.class);
        catDisc = item(CatDisc.class);
        chirpDisc = item(ChirpDisc.class);
        elevenDisc = item(ElevenDisc.class);
        farDisc = item(FarDisc.class);
        mallDisc = item(MallDisc.class);
        mellohiDisc = item(MellohiDisc.class);
        stalDisc = item(StalDisc.class);
        stradDisc = item(StradDisc.class);
        thirteenDisc = item(ThirteenDisc.class);
        waitDisc = item(WaitDisc.class);
        wardDisc = item(WardDisc.class);

        cookedChicken = item(CookedChicken.class);
        cookedMutton = item(CookedMutton.class);
        cookedPorkchop = item(CookedPorkchop.class);
        steak = item(Steak.class);
        rawChicken = item(RawChicken.class);
        rawMutton = item(RawMutton.class);
        rawPorkchop = item(RawPorkchop.class);
        rawBeef = item(RawBeef.class);
    }
}
