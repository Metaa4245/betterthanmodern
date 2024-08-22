package me.meta4245.betterthanmodern.event;

import me.meta4245.betterthanmodern.Mod;
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

        for (int i = 0; i < class_name.length(); i++) {
            char lower = Character.toLowerCase(class_name.charAt(i));
            if (i == class_name.length() - 1) {
                name.append(lower);
            } else if (Character.isUpperCase(class_name.charAt(i)) && i == 0) {
                name.append(lower);
            } else if (Character.isUpperCase(class_name.charAt(i + 1)) && i != 0) {
                name.append(lower);
                name.append("_");
            } else {
                name.append(lower);
            }
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
        // TODO: better config way
        // Foods
        if (Mod.config.foods.mutton) {
            rawMutton = item(RawMutton.class);
            cookedMutton = item(CookedMutton.class);
        }

        if (Mod.config.foods.chicken) {
            rawChicken = item(RawChicken.class);
            cookedChicken = item(CookedChicken.class);
        }

        if (Mod.config.foods.porkchop) {
            rawPorkchop = item(RawPorkchop.class);
            cookedPorkchop = item(CookedPorkchop.class);
        }

        if (Mod.config.foods.beef) {
            rawBeef = item(RawBeef.class);
            steak = item(Steak.class);
        }

        if (Mod.config.discs.stal) {
            stalDisc = item(StalDisc.class);
        }

        if (Mod.config.discs.blocks) {
            blocksDisc = item(BlocksDisc.class);
        }

        if (Mod.config.discs.cat) {
            catDisc = item(CatDisc.class);
        }

        if (Mod.config.discs.chirp) {
            chirpDisc = item(ChirpDisc.class);
        }

        if (Mod.config.discs.eleven) {
            elevenDisc = item(ElevenDisc.class);
        }

        if (Mod.config.discs.far) {
            farDisc = item(FarDisc.class);
        }

        if (Mod.config.discs.mall) {
            mallDisc = item(MallDisc.class);
        }

        if (Mod.config.discs.mellohi) {
            mellohiDisc = item(MellohiDisc.class);
        }

        if (Mod.config.discs.strad) {
            stradDisc = item(StradDisc.class);
        }

        if (Mod.config.discs.thirteen) {
            thirteenDisc = item(ThirteenDisc.class);
        }

        if (Mod.config.discs.wait) {
            waitDisc = item(WaitDisc.class);
        }

        if (Mod.config.discs.ward) {
            wardDisc = item(WardDisc.class);
        }
    }
}
