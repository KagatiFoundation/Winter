package org.winterframework.core.node;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ComponentNode {
    private final List<ComponentNode> children = new ArrayList<>();
    private final List<Property> properties = new ArrayList<>();

    private final Class<?> componentClass;
    private final Field declaringField;
    private Object instance;

    public ComponentNode(Class<?> componentClass, Field declaringField) {
        this.componentClass = componentClass;
        this.declaringField = declaringField;
    }

    public void addChild(ComponentNode node) {
        this.children.add(node);
    }

    public void addProperty(Property p) {
        this.properties.add(p);
    }

    public Field getDeclaringField() { return declaringField; }

    public Class<?> getComponentClass() { return componentClass; }

    public List<ComponentNode> getChildren() { return children; }

    public Object getInstance() { return instance; }

    public void setInstance(Object instance) { this.instance = instance; }
}