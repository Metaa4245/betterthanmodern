package me.meta4245.betterthanmodern;

import net.fabricmc.api.ModInitializer;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import static org.reflections.scanners.Scanners.TypesAnnotated;

public class Mod implements ModInitializer {
    public static Reflections reflections;

    @Override
    public void onInitialize() {
        reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackage("me.meta4245.betterthanmodern")
                        .setScanners(TypesAnnotated)
        );
    }
}
