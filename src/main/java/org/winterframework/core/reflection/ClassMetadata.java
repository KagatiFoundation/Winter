package org.winterframework.core.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ClassMetadata {

    public static Method findMethodWithAnnotation(Class<?> klass, Class<? extends Annotation> annotation) {
        for (Method method : klass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                return method;
            }
        }
        return null;
    }
}