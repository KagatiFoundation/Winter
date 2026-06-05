package org.winterframework.core.engine;

import org.winterframework.component.ComponentHelper;
import org.winterframework.core.node.ComponentNode;

import java.lang.reflect.Field;

public class ComponentTreeBuilder {
    /**
     * Entry point to generate the metadata tree starting from the root Frame.
     */
    public static ComponentNode buildTree(Class<?> rootFrameClass) {
        if (!ComponentHelper.isComponent(rootFrameClass)) {
            throw new IllegalArgumentException("Root class must be an eligible framework component!");
        }

        ComponentNode rootNode = new ComponentNode(rootFrameClass, null);

        parseChildren(rootNode);

        return rootNode;
    }

    /**
     * Recursive loop that parses fields of a node to find subcomponents.
     */
    private static void parseChildren(ComponentNode parentNode) {
        Class<?> parentClass = parentNode.getComponentClass();

        for (Field field: parentClass.getDeclaredFields()) {
            Class<?> fieldType = field.getType();

            if (ComponentHelper.isComponent(fieldType)) {
                ComponentNode childNode = new ComponentNode(fieldType, field);
                parentNode.addChild(childNode);

                // recursive
                parseChildren(childNode);
            }
        }
    }
}