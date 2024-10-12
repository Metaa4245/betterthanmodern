package me.meta4245.betterthanmodern;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public abstract class ReflectionHacks {
    public static @NotNull String get_name(@NotNull Class<?> clazz) {
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

    public static List<Field> getFieldsOfType(@NotNull Class<?> clazz, Class<?> type) {
        List<Field> allFields = Arrays.asList(clazz.getDeclaredFields());
        return allFields.stream()
                .filter(field -> field.getType() == type)
                .toList();
    }
}
