package me.meta4245.betterthanmodern.reflection;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class Reflections {
    private final List<Class<?>> classes;

    public Reflections(String pkg) {
        String path = pkg.replace('.', '/');
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            classes = Collections.list(loader.getResources(path)).stream()
                    .flatMap(url -> scanResource(url, pkg, path))
                    .filter(Reflections::isConcrete)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isConcrete(Class<?> clazz) {
        String name = clazz.getName();

        return !name.contains("$") && !name.equals("package-info");
    }

    private static Stream<Class<?>> scanResource(
            URL resource,
            String pkg,
            String path
    ) {
        try {
            return switch (resource.getProtocol()) {
                case "file" -> scanFiles(Paths.get(resource.toURI()), pkg);
                case "jar" -> scanJar(resource, path);
                default -> Stream.empty();
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<Class<?>> scanFiles(Path dirPath, String pkg) {
        try (Stream<Path> files = Files.walk(dirPath)) {
            return files
                    .filter(f -> f.toString().endsWith(".class"))
                    .filter(f -> !f.toString().contains("mixin"))
                    .<Class<?>>map(
                            f -> toClass(pkg, dirPath.relativize(f).toString())
                    )
                    .toList()
                    .stream();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<Class<?>> scanJar(URL resource, String path) {
        String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
        try (JarFile jar = new JarFile(
                URLDecoder.decode(jarPath, StandardCharsets.UTF_8)
        )) {
            return jar.stream()
                    .map(JarEntry::getName)
                    .filter(name -> name.startsWith(path))
                    .filter(name -> name.endsWith(".class"))
                    .filter(name -> !name.contains("mixin"))
                    .<Class<?>>map(Reflections::toClass)
                    .toList()
                    .stream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String stripSuffix(String s) {
        return s.endsWith(".class") ? s.substring(0, s.length() - 6) : s;
    }

    private static Class<?> toClass(String pkg, String relPath) {
        String className = stripSuffix(
                (pkg + "." + relPath).replace(File.separatorChar, '.')
        );
        return loadClass(className);
    }

    private static Class<?> toClass(String jarEntryName) {
        String className = stripSuffix(
                jarEntryName.replace('/', '.')
        );
        return loadClass(className);
    }

    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(
                    className,
                    false,
                    Thread.currentThread().getContextClassLoader()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public @NotNull Class<?> getClass(
            @NotNull Class<?> subtype,
            @NotNull String name
    ) {
        return classes
                .stream()
                .filter(subtype::isAssignableFrom)
                .filter(c -> c.getSimpleName().equals(name))
                .findFirst()
                .orElseThrow();
    }
}