package org.winterframework.core.engine;

import org.winterframework.core.context.WinterContext;
import org.winterframework.core.node.ComponentNode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ComponentInstantiator {
    public static void instantiateNode(ComponentNode node) {
        if (node == null) return;

        Object currentInstance = node.getInstance();
        if (currentInstance == null) {
            throw new IllegalStateException("Parent instance is missing! Top-down instantiation flow was broken.");
        }

        for (ComponentNode childNode: node.getChildren()) {
            try {
                Class<?> childClass = childNode.getComponentClass();
                Object childInstance;

                if (WinterContext.contains(childClass)) {
                    childInstance = WinterContext.getInstance(childClass);
                }
                else {
                    Constructor<?> constructor = childClass.getDeclaredConstructor();
                    constructor.setAccessible(true);

                    childInstance = constructor.newInstance();
                }

                childNode.setInstance(childInstance);

                Field declaringField = childNode.getDeclaringField();
                if (declaringField != null) {
                    declaringField.setAccessible(true);
                    declaringField.set(currentInstance, childInstance);
                }

                instantiateNode(childNode);
            }
            catch (Exception e) {
                throw new RuntimeException("Error processing component instantiation branch for type: "
                    + childNode.getComponentClass().getSimpleName(), e);
            }
        }
    }
}