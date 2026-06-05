package org.winterframework.core.context;

import java.util.HashMap;

public class WinterContext {
    private static final HashMap<Class<?>, Object> registry = new HashMap<>();

    public static void register(Class<?> clazz, Object instance) {
        registry.put(clazz, instance);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> klass) {
        return (T) registry.get(klass);
    }

    public static boolean contains(Class<?> clazz) {
        return registry.containsKey(clazz);
    }
}