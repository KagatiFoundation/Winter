package org.winterframework.event;

import org.winterframework.event.annotation.WinterEvent;
import org.winterframework.event.strategy.*;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class WinterEventRouter {
    private static final List<EventBindingStrategy> strategies = List.of(
        new ButtonClickStrategy(),
        new MouseEnterStrategy(),
        new MouseMoveStrategy()
    );

    public static void bindEvents(Object controllerInstance) {
        Class<?> clazz = controllerInstance.getClass();

        for (Method method: clazz.getDeclaredMethods()) {
            for (Annotation annotation: method.getAnnotations()) {
                Class<? extends Annotation> annotationType = annotation.annotationType();

                if (annotationType.isAnnotationPresent(WinterEvent.class)) {
                    WinterEvent meta = annotationType.getAnnotation(WinterEvent.class);
                    EventType eventType = meta.type();

                    try {
                        Method componentMethod = annotationType.getMethod("component");
                        String componentName = (String) componentMethod.invoke(annotation);

                        Field field = clazz.getDeclaredField(componentName);
                        field.setAccessible(true);
                        Object componentInstance = field.get(controllerInstance);

                        if (componentInstance == null) {
                            throw new IllegalStateException("Field " + componentName + " must be instantiated before binding events.");
                        }

                        method.setAccessible(true);

                        for (EventBindingStrategy strategy : strategies) {
                            if (strategy.supports(eventType, componentInstance)) {
                                strategy.bind(componentInstance, method, controllerInstance);
                                System.out.println("Winter: Bound meta-event " + annotationType.getSimpleName() + " using " + strategy.getClass().getSimpleName());
                                break;
                            }
                        }
                    }
                    catch (Exception e) {
                        System.err.println("Winter Error: Failed to process meta-event annotation on method " + method.getName());
                        e.printStackTrace();
                    }
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
