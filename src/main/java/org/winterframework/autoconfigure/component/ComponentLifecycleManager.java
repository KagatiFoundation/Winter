package org.winterframework.autoconfigure.component;

import org.winterframework.component.ComponentHelper;
import org.winterframework.core.reflection.ClassMetadata;
import org.winterframework.core.reflection.MethodInvoker;
import org.winterframework.component.annotation.AfterMount;
import org.winterframework.component.annotation.AfterUnmount;
import org.winterframework.component.annotation.BeforeMount;
import org.winterframework.component.annotation.BeforeUnmount;

import java.lang.reflect.Method;

public class ComponentLifecycleManager {

    public static void invokeBeforeMount(Object componentInstance) {
        if (!ComponentHelper.isComponent(componentInstance.getClass())) return;

        Method beforeMounts = ClassMetadata.findMethodWithAnnotation(componentInstance.getClass(), BeforeMount.class);

        if (beforeMounts != null) {
            beforeMounts.setAccessible(true);
            MethodInvoker.invokeVoid(componentInstance, beforeMounts);
        }
    }

    public static void invokeAfterMount(Object componentInstance) {
        if (!ComponentHelper.isComponent(componentInstance.getClass())) return;

        Method beforeMounts = ClassMetadata.findMethodWithAnnotation(componentInstance.getClass(), AfterMount.class);

        if (beforeMounts != null) {
            beforeMounts.setAccessible(true);
            MethodInvoker.invokeVoid(componentInstance, beforeMounts);
        }
    }

    public static void invokeBeforeUnmount(Object componentInstance) {
        if (!ComponentHelper.isComponent(componentInstance.getClass())) return;

        Method beforeMounts = ClassMetadata.findMethodWithAnnotation(componentInstance.getClass(), BeforeUnmount.class);

        if (beforeMounts != null) {
            beforeMounts.setAccessible(true);
            MethodInvoker.invokeVoid(componentInstance, beforeMounts);
        }
    }

    public static void invokeAfterUnmount(Object componentInstance) {
        if (!ComponentHelper.isComponent(componentInstance.getClass())) return;

        Method beforeMounts = ClassMetadata.findMethodWithAnnotation(componentInstance.getClass(), AfterUnmount.class);

        if (beforeMounts != null) {
            beforeMounts.setAccessible(true);
            MethodInvoker.invokeVoid(componentInstance, beforeMounts);
        }
    }
}