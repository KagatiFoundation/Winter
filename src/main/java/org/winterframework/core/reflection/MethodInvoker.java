package org.winterframework.core.reflection;

import org.winterframework.WinterException;
import java.lang.reflect.Method;

public class MethodInvoker {

    public static void invokeVoid(Object instance, Method method, Object... args) {
        if (method == null || instance == null) {
            return;
        }

        if (method.getReturnType() != void.class) {
            throw new WinterException("Method [" + method.getName() + "] must have a void return type.");
        }

        method.setAccessible(true);
        try {
            method.invoke(instance, args);
        } catch (Exception e) {
            throw new WinterException("Failed to invoke method: " + method.getName());
        }
    }

    public static Object invoke(Object instance, Method method, Object... args) {
        if (method == null || instance == null) {
            return null;
        }

        method.setAccessible(true);
        try {
            return method.invoke(instance, args);
        } catch (Exception e) {
            throw new WinterException("Failed to invoke method: " + method.getName());
        }
    }
}