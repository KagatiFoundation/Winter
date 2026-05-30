package org.winterframework.event;

import org.winterframework.event.annotation.EventListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class WinterEventRouter {
    public static void bindEvents(Object controllerInstance) {
        Class<?> clazz = controllerInstance.getClass();

        for (Method method: clazz.getDeclaredMethods()) {
            EventListener[] eventConfigs = method.getAnnotationsByType(EventListener.class);

            for (EventListener eventConfig: eventConfigs) {
                try {
                    Field field = clazz.getDeclaredField(eventConfig.component());
                    field.setAccessible(true);

                    Object componentInstance = field.get(controllerInstance);

                    if (componentInstance == null) {
                        throw new IllegalStateException("Field " + eventConfig.component() + " must be instantiated before binding events.");
                    }

                    method.setAccessible(true);

                    switch (eventConfig.type()) {
                        case ButtonClick: {
                            if (componentInstance instanceof AbstractButton button) {
                                button.addActionListener(e -> invokeEventListener(method, EventType.ButtonClick, controllerInstance, e));
                                System.out.println("Winter: Wired click event from '" + eventConfig.component() + "' to " + method.getName() + "()");
                            }
                            break;
                        }
                        default: {
                            System.out.println("Event type not supported yet!");
                        }
                    }
                }
                catch (NoSuchFieldException e) {
                    System.err.println("Error: No component named '" + eventConfig.component() + "' found in controller.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void invokeEventListener(
        Method method,
        EventType type,
        Object instance,
        Object... args
    ) {
        try {
            Parameter[] parameters = method.getParameters();

            if (parameters.length != args.length) {
                System.err.println("Expected ActionEvent as the first parameter on method: " + method.getName());
                return;
            }

            if (type == EventType.ButtonClick) {
                Parameter actionEventParameter = parameters[0];
                if (!actionEventParameter.getType().equals(ActionEvent.class)) {
                    throw new IllegalArgumentException("Invalid event type present as the first argument on method " + method.getName() + ".");
                }
            }

            method.invoke(instance, args);
        } catch (Exception e) {
            System.err.println("Failed to execute event handler method: " + method.getName());
            e.printStackTrace();
        }
    }
}
