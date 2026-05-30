package org.winterframework.core;

import org.winterframework.core.autoconfigure.EntryPoint;
import org.winterframework.core.autoconfigure.WinterApplication;

import java.lang.reflect.Method;

public class WinterApplicationRunner {

    public static void run(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(WinterApplication.class)) {
            System.err.println("Class is not annotated with WinterApplication.");
            return;
        }

        try {
            Object appInstance = clazz.getDeclaredConstructor().newInstance();

            for (Method method: clazz.getDeclaredMethods()) {
                method.setAccessible(true);

                if (method.isAnnotationPresent(EntryPoint.class)) {
                    method.invoke(appInstance);
                    break;
                }
            }

            WinterEventRouter.bindEvents(appInstance);
        }
        catch (NoSuchMethodException e) {
            System.err.println("Winter Error: Your application class must have a default, no-argument constructor.");
        }
        catch (java.lang.reflect.InvocationTargetException e) {
            System.err.println("Winter Error: The entry point method threw an exception while running.");
            e.getCause().printStackTrace();
        }
        catch (Exception e) {
            System.err.println("Winter Error: Failed to initialize or execute application context.");
            e.printStackTrace();
        }

    }
}
