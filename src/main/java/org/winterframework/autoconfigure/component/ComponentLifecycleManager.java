package org.winterframework.autoconfigure.component;

import org.winterframework.component.ComponentHelper;
import org.winterframework.core.reflection.ClassMetadata;
import org.winterframework.core.reflection.MethodInvoker;
import org.winterframework.component.annotation.AfterComponentMounts;
import org.winterframework.component.annotation.AfterComponentUnmounts;
import org.winterframework.component.annotation.BeforeComponentMounts;
import org.winterframework.component.annotation.BeforeComponentUnmounts;

import java.lang.reflect.Method;

public class ComponentLifecycleManager {

    public static void invokeBeforeMount(Object componentInstance) {
        if (!ComponentHelper.isComponent(componentInstance.getClass())) return;

        Method beforeMounts = ClassMetadata.findMethodWithAnnotation(componentInstance.getClass(), BeforeComponentMounts.class);

        if (beforeMounts != null) {
            beforeMounts.setAccessible(true);
            MethodInvoker.invokeVoid(componentInstance, beforeMounts);
        }
    }

    public static void invokeAfterMount(Object componentInstance) {
        if (!ComponentHelper.isComponent(componentInstance.getClass())) return;

        Method beforeMounts = ClassMetadata.findMethodWithAnnotation(componentInstance.getClass(), AfterComponentMounts.class);

        if (beforeMounts != null) {
            beforeMounts.setAccessible(true);
            MethodInvoker.invokeVoid(componentInstance, beforeMounts);
        }
    }

    public static void invokeBeforeUnmount(Object componentInstance) {
        if (!ComponentHelper.isComponent(componentInstance.getClass())) return;

        Method beforeMounts = ClassMetadata.findMethodWithAnnotation(componentInstance.getClass(), BeforeComponentUnmounts.class);

        if (beforeMounts != null) {
            beforeMounts.setAccessible(true);
            MethodInvoker.invokeVoid(componentInstance, beforeMounts);
        }
    }

    public static void invokeAfterUnmount(Object componentInstance) {
        if (!ComponentHelper.isComponent(componentInstance.getClass())) return;

        Method beforeMounts = ClassMetadata.findMethodWithAnnotation(componentInstance.getClass(), AfterComponentUnmounts.class);

        if (beforeMounts != null) {
            beforeMounts.setAccessible(true);
            MethodInvoker.invokeVoid(componentInstance, beforeMounts);
        }
    }
}