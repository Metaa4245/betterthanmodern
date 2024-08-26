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
    private final static Namespace NAMESPACE = Null.get();

    Item blocksDisc;
    Item catDisc;
    Item chirpDisc;
    Item elevenDisc;
    Item farDisc;
    Item mallDisc;
    Item mellohiDisc;
    Item stalDisc;
    Item stradDisc;
    Item thirteenDisc;
    Item waitDisc;
    Item wardDisc;

    Item cookedChicken;
    Item cookedMutton;
    Item cookedPorkchop;
    Item steak;
    Item rawBeef;
    Item rawChicken;
    Item rawMutton;
    Item rawPorkchop;

    private Item item(Class<? extends Item> clazz) {
        StringBuilder name = new StringBuilder();
        String simpleName = clazz.getSimpleName();
        int len = simpleName.length();

        for (int i = 0; i < len; i++) {
            char c = simpleName.charAt(i);

            if (Character.isUpperCase(c) && i != len - 1 && i != 0) {
                name.append("_");
            }
            name.append(Character.toLowerCase(c));
        }

        String key = name.toString();
        Item item;

        try {
            item = clazz.getConstructor(Identifier.class)
                    .newInstance(NAMESPACE.id(key))
                    .setTranslationKey(key);
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
