package me.meta4245.betterthanmodern;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("betterthanmodern");

    @Override
    public void onInitialize() {
        LOGGER.info("Better than Modern initialized");
    }

    // why is this here?
    // TODO: move to AnimalEntityMixin
    public static Entity createEntity(Class<? extends Entity> clazz, Level level) {
        try {
            return clazz.getConstructor(Level.class).newInstance(level);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't create Entity");
        }
    }
}
