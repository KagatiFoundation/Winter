package org.winterframework.autoconfigure.component;

import org.winterframework.stereotype.Component;
import org.winterframework.event.WinterEventRouter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ComponentInjector {
    public static void inject(Object controllerInstance) {
        Class<?> clazz = controllerInstance.getClass();

        for (Field field: clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) continue;

            field.setAccessible(true);
            Object instantiatedObject = null;

            try {
                if (field.isAnnotationPresent(Component.class)) {
                    Component componentConfig = field.getAnnotation(Component.class);
                    try {
                        Constructor<?> stringCtor = field.getType().getConstructor(String.class);
                        stringCtor.setAccessible(true);
                        instantiatedObject = stringCtor.newInstance(componentConfig.text());
                    } catch (NoSuchMethodException e) {
                        Constructor<?> defaultCtor = field.getType().getConstructor();
                        defaultCtor.setAccessible(true);
                        instantiatedObject = defaultCtor.newInstance();
                    }

                    field.set(controllerInstance, instantiatedObject);
                }
            }
            catch (Exception e) {
                System.err.println("Winter Error: Failed to auto-instantiate field: " + field.getName());
                e.printStackTrace();
            }
        }

        WinterEventRouter.bindEvents(controllerInstance);
    }
}