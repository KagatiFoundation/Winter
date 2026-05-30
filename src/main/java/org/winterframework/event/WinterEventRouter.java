package org.winterframework.event;

import org.winterframework.event.annotation.EventListener;
import org.winterframework.event.strategy.ButtonClickStrategy;
import org.winterframework.event.strategy.EventBindingStrategy;
import org.winterframework.event.strategy.MouseEnterStrategy;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class WinterEventRouter {
    private static final List<EventBindingStrategy> strategies = List.of(
        new ButtonClickStrategy(),
        new MouseEnterStrategy()
    );

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

                    boolean strategyFound = false;
                    for (EventBindingStrategy strategy: strategies) {
                        if (strategy.supports(eventConfig.type(), componentInstance)) {
                            strategy.bind(componentInstance, method, controllerInstance);
                            strategyFound = true;
                            break;
                        }
                    }

                    if (!strategyFound) {
                        System.err.println("Winter Warning: Unsupported event combination [" + eventConfig.type() + "] for component type " + componentInstance.getClass().getSimpleName());
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

    public static void invokeEventListener(
        Method method,
        EventType type,
        Object instance,
        Object... args
    ) {
        try {
            Parameter[] parameters = method.getParameters();

            if (parameters.length == 0) {
                method.invoke(instance);
                return;
            }

            if (parameters.length != args.length) {
                System.err.println("Parameter mismatch on method: " + method.getName());
                return;
            }

            if (type == EventType.ButtonClick && !parameters[0].getType().equals(ActionEvent.class)) {
                throw new IllegalArgumentException("Expected ActionEvent parameter on method " + method.getName());
            }
            if (type == EventType.MouseEnter && !parameters[0].getType().equals(MouseEvent.class)) {
                throw new IllegalArgumentException("Expected MouseEvent parameter on method " + method.getName());
            }

            method.invoke(instance, args);
        } catch (Exception e) {
            System.err.println("Failed to execute event handler method: " + method.getName());
            e.printStackTrace();
        }
    }
}
