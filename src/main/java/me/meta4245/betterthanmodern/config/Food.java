package me.meta4245.betterthanmodern.config;

import net.glasslauncher.mods.api.gcapi.api.ConfigName;
import net.glasslauncher.mods.api.gcapi.api.MultiplayerSynced;

public class Food {
    @ConfigName("Chicken")
    @MultiplayerSynced
    public boolean chicken;

    @ConfigName("Mutton")
    @MultiplayerSynced
    public boolean mutton;

    @ConfigName("Porkchop")
    @MultiplayerSynced
    public boolean porkchop;

    @ConfigName("Beef")
    @MultiplayerSynced
    public boolean beef;
}
