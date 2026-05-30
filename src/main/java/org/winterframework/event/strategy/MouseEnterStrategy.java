package org.winterframework.event.strategy;

import org.winterframework.event.EventType;
import org.winterframework.event.WinterEventRouter;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;

public class MouseEnterStrategy implements EventBindingStrategy {
    @Override
    public boolean supports(EventType type, Object componentInstance) {
        return type == EventType.MouseEnter && componentInstance instanceof Component;
    }

    @Override
    public void bind(Object componentInstance, Method method, Object controllerInstance) {
        Component component = (Component) componentInstance;
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                WinterEventRouter.invokeEventListener(method, EventType.MouseEnter, controllerInstance, e);
            }
        });
    }
}