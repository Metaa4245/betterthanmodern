package me.meta4245.betterthanmodern;

import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public abstract class ReflectionHacks {
    public static @NotNull String namespace_name(@NotNull Class<?> clazz) {
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

    public static @NotNull String class_name(@NotNull Field field) {
        String name = field.getName();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static @NotNull String field_name(@NotNull Class<?> clazz) {
        String name = clazz.getSimpleName();
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    public static @NotNull Class<? extends TemplateItem> item_class(@NotNull String name) throws ClassNotFoundException {
        return (Class<? extends TemplateItem>) Class.forName("me.meta4245.betterthanmodern.item." + name);
    }

    public static @NotNull Class<? extends TemplateBlock> block_class(@NotNull String name) throws ClassNotFoundException {
        return (Class<? extends TemplateBlock>) Class.forName("me.meta4245.betterthanmodern.block." + name);
    }

    public static List<Field> getFieldsOfType(@NotNull Class<?> clazz, Class<?> type) {
        List<Field> allFields = Arrays.asList(clazz.getDeclaredFields());
        return allFields.stream()
                .filter(field -> field.getType() == type)
                .toList();
    }
}
