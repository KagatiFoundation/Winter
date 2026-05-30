package org.winterframework.core.autoconfigure;

import org.winterframework.core.annotation.UIComponent;

import javax.swing.*;
import java.lang.reflect.Field;

public class UIComponentInjector {
    public static void inject(Object controllerInstance) {
        Class<?> clazz = controllerInstance.getClass();

        for (Field field: clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(UIComponent.class)) {
                UIComponent componentConfig = field.getAnnotation(UIComponent.class);

                try {
                    field.setAccessible(true);
                    Object instantiatedComponent = null;

                    if (field.getType() == JButton.class) {
                        instantiatedComponent = new JButton(componentConfig.text());
                    }

                    if (instantiatedComponent != null) {
                        field.set(controllerInstance, instantiatedComponent);
                    }
                }
                catch (Exception e) {
                    System.err.println("Winter Error: Failed to auto-instantiate field: " + field.getName());
                    e.printStackTrace();
                }
            }
        }
    }
}
