package org.winterframework.core.autoconfigure;

import org.winterframework.core.annotation.UIComponent;
import org.winterframework.core.annotation.UIContainer;
import org.winterframework.event.WinterEventRouter;
import org.winterframework.stereotype.Controller;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class UIComponentInjector {
    public static void inject(Object controllerInstance) {
        Class<?> clazz = controllerInstance.getClass();

        for (Field field: clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) continue;

            field.setAccessible(true);
            Object instantiatedObject = null;

            try {
                if (field.isAnnotationPresent(UIContainer.class)) {
                    Constructor<?> ctor = field.getType().getConstructor();
                    ctor.setAccessible(true);
                    instantiatedObject = ctor.newInstance();
                    field.set(controllerInstance, instantiatedObject);

                    inject(instantiatedObject);
                }
                else if (field.isAnnotationPresent(UIComponent.class)) {
                    UIComponent componentConfig = field.getAnnotation(UIComponent.class);
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

        insertComponentsIntoContainer(controllerInstance);

        if (clazz.isAnnotationPresent(Controller.class)) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(PostConstruct.class)) {
                    try {
                        method.setAccessible(true);
                        method.invoke(controllerInstance);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        WinterEventRouter.bindEvents(controllerInstance);
    }

    private static void insertComponentsIntoContainer(Object controllerInstance) {
        Class<?> clazz = controllerInstance.getClass();

        for (Field field: clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(UIContainer.class)) {
                field.setAccessible(true);

                UIContainer containerConfig = field.getAnnotation(UIContainer.class);
                String[] childFieldNames = containerConfig.children();

                if (childFieldNames.length > 0) {
                    try {
                        Object livingContainer = field.get(controllerInstance);

                        if (livingContainer instanceof Container swingContainer) {
                            for (String childName: childFieldNames) {
                                Field childField = clazz.getDeclaredField(childName);
                                childField.setAccessible(true);
                                Object livingChild = childField.get(controllerInstance);

                                if (livingChild instanceof Component swingComponent) {
                                    swingContainer.add(swingComponent);
                                }
                            }
                        }
                    }
                    catch (Exception e) {
                        System.err.println("Winter Error: Failed to wire children layout trees for: " + field.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}