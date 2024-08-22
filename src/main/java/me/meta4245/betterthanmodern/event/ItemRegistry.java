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

    private Item registerHelper(Class<? extends Item> clazz, String key) {
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
        rawMutton = registerHelper(RawMutton.class, "raw_mutton");
        rawChicken = registerHelper(RawChicken.class, "raw_chicken");
        rawPorkchop = registerHelper(RawPorkchop.class, "raw_porkchop");
        rawBeef = registerHelper(RawBeef.class, "raw_beef");

        cookedChicken = registerHelper(CookedChicken.class, "cooked_chicken");
        cookedMutton = registerHelper(CookedMutton.class, "cooked_mutton");
        cookedPorkchop = registerHelper(CookedPorkchop.class, "cooked_porkchop");
        steak = registerHelper(Steak.class, "steak");

        stalDisc = registerHelper(StalDisc.class, "stal_disc");
        blocksDisc = registerHelper(BlocksDisc.class, "blocks_disc");
        catDisc = registerHelper(CatDisc.class, "cat_disc");
        chirpDisc = registerHelper(ChirpDisc.class, "chirp_disc");
        elevenDisc = registerHelper(ElevenDisc.class, "eleven_disc");
        farDisc = registerHelper(FarDisc.class, "far_disc");
        mallDisc = registerHelper(MallDisc.class, "mall_disc");
        mellohiDisc = registerHelper(MellohiDisc.class, "mellohi_disc");
        stradDisc = registerHelper(StradDisc.class, "strad_disc");
        thirteenDisc = registerHelper(ThirteenDisc.class, "thirteen_disc");
        waitDisc = registerHelper(WaitDisc.class, "wait_disc");
        wardDisc = registerHelper(WardDisc.class, "ward_disc");
    }
}
