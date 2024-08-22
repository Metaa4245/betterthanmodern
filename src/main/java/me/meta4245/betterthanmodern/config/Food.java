package me.meta4245.betterthanmodern.config;

import net.glasslauncher.mods.api.gcapi.api.ConfigName;
import net.glasslauncher.mods.api.gcapi.api.MultiplayerSynced;

public class Food {
    @ConfigName("Chicken")
    @MultiplayerSynced
    public boolean chicken = true;

    @ConfigName("Mutton")
    @MultiplayerSynced
    public boolean mutton = true;

    @ConfigName("Porkchop")
    @MultiplayerSynced
    public boolean porkchop = true;

    @ConfigName("Beef")
    @MultiplayerSynced
    public boolean beef = true;
}
