package org.winterframework.core.engine;

import org.winterframework.component.annotation.AfterMount;
import org.winterframework.component.annotation.AfterUnmount;
import org.winterframework.component.annotation.BeforeMount;
import org.winterframework.component.annotation.BeforeUnmount;
import org.winterframework.core.node.ComponentNode;
import org.winterframework.core.reflection.ClassMetadata;
import org.winterframework.core.reflection.MethodInvoker;

import java.lang.reflect.Method;

public class LifecycleMethodsExecutor {
    public static void executeBeforeMount(ComponentNode node) {
        if (node == null) return;

        executeHook(node, BeforeMount.class);

        for (ComponentNode child: node.getChildren()) {
            executeBeforeMount(child);
        }
    }

    public static void executeAfterMount(ComponentNode node) {
        if (node == null) return;

        executeHook(node, AfterMount.class);

        for (ComponentNode child: node.getChildren()) {
            executeAfterMount(child);
        }
    }

    public static void executeMountLifecycle(ComponentNode node) {
        if (node == null) return;

        executeHook(node, BeforeMount.class);

        for (ComponentNode child: node.getChildren()) {
            executeMountLifecycle(child);
        }

        executeHook(node, AfterMount.class);
    }

    public static void executeUnmountLifecycle(ComponentNode node) {
        if (node == null) return;

        executeHook(node, BeforeUnmount.class);

        for (ComponentNode child: node.getChildren()) {
            executeMountLifecycle(child);
        }

        executeHook(node, AfterUnmount.class);
    }

    private static void executeHook(
        ComponentNode node,
        Class<? extends java.lang.annotation.Annotation> annotation
    ) {
        Object instance = node.getInstance();
        if (instance == null) return;

        Method hook = ClassMetadata.findMethodWithAnnotation(instance.getClass(), annotation);
        if (hook != null) {
            MethodInvoker.invokeVoid(instance, hook);
        }
    }
}