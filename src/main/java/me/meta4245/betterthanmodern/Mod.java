package me.meta4245.betterthanmodern;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("betterthanmodern");

    // why is this here?
    // TODO: move to AnimalEntityMixin
    public static Entity createEntity(Class<? extends Entity> clazz, World world) {
        try {
            return clazz.getConstructor(World.class).newInstance(world);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't create Entity");
        }
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Better than Modern initialized");
    }
}
