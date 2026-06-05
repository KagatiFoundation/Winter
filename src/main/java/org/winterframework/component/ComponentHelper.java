package org.winterframework.component;

import org.winterframework.WinterApplicationRunner;
import org.winterframework.stereotype.Component;

import java.lang.annotation.Annotation;

public class ComponentHelper {
    public static boolean isComponent(Class<?> klass) {
        if (klass == null) {
            return false;
        }

        String clazzPackage = klass.getPackageName();
        if (!clazzPackage.startsWith(WinterApplicationRunner.ROOT_PACKAGE)) {
            return false;
        }

        if (klass.isAnnotationPresent(Component.class)) {
            return true;
        }

        for (Annotation annotation : klass.getAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(Component.class)) {
                return true;
            }
        }

        return false;
    }
}