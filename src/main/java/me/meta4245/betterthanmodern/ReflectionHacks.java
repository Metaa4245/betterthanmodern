package me.meta4245.betterthanmodern;

public abstract class ReflectionHacks {
    public static String get_name(Class<?> clazz) {
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
}
