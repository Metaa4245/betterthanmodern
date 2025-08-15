package me.meta4245.betterthanmodern.reflection;

import me.meta4245.betterthanmodern.event.BlockRegistry;
import me.meta4245.betterthanmodern.event.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

public abstract class Utilities {
    private static final Reflections reflections =
            new Reflections("me.meta4245.betterthanmodern");

    public static @NotNull String namespaceName(@NotNull Class<?> clazz) {
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

        return name.toString();
    }

    public static @NotNull String className(@NotNull Field field) {
        String name = field.getName();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static @NotNull String fieldName(@NotNull Class<?> clazz) {
        String name = clazz.getSimpleName();
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    @SuppressWarnings("unchecked")
    public static @NotNull Class<? extends Item> itemClass(@NotNull String name) {
        return (Class<? extends Item>) reflections.getClass(Item.class, name);
    }

    @SuppressWarnings("unchecked")
    public static @NotNull Class<? extends Block> blockClass(@NotNull String name) {
        return (Class<? extends Block>) reflections.getClass(Block.class, name);
    }

    public static Field getField(
            @NotNull Class<?> clazz,
            String name
    ) {
        try {
            return clazz.getDeclaredField(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getFieldValue(
            @NotNull Field field
    ) {
        try {
            return field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setField(
            @NotNull Field field,
            @NotNull Object value
    ) {
        try {
            field.set(null, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<Field> getFieldsOfType(
            @NotNull Class<?> clazz,
            Class<?> type
    ) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.getType() == type);
    }

    public static Stream<? extends Class<? extends Block>> getBlocks() {
        return getFieldsOfType(BlockRegistry.class, Block.class).map(f -> {
            try {
                return blockClass(className(f));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Stream<? extends Class<? extends Item>> getItems() {
        return getFieldsOfType(ItemRegistry.class, Item.class).map(f -> {
            try {
                return itemClass(className(f));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Entity createEntity(
            Class<? extends Entity> clazz,
            World world
    ) {
        try {
            return clazz.getConstructor(World.class).newInstance(world);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
