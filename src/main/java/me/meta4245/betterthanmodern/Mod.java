package me.meta4245.betterthanmodern;

import me.meta4245.betterthanmodern.config.Config;
import net.fabricmc.api.ModInitializer;
import net.glasslauncher.mods.api.gcapi.api.GConfig;

public class Mod implements ModInitializer {
    @GConfig(value = "betterthanmodern", visibleName = "Better than Modern")
    public static final Config config = new Config();

    @Override
    public void onInitialize() {}
}
