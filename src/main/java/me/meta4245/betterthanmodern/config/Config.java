package me.meta4245.betterthanmodern.config;

import net.glasslauncher.mods.api.gcapi.api.ConfigCategory;
import net.glasslauncher.mods.api.gcapi.api.MultiplayerSynced;

public class Config {
    @ConfigCategory("Discs")
    @MultiplayerSynced
    public Discs discs = new Discs();

    @ConfigCategory("Foods")
    @MultiplayerSynced
    public Food foods = new Food();
}
