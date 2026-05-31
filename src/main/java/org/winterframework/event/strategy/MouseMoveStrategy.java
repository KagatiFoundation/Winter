package org.winterframework.event.strategy;

import org.winterframework.event.EventType;
import org.winterframework.event.WinterEventRouter;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.lang.reflect.Method;

public class MouseMoveStrategy implements EventBindingStrategy {
    @Override
    public boolean supports(EventType type, Object componentInstance) {
        return type == EventType.MouseMove && componentInstance instanceof Component;
    }

    @Override
    public void bind(Object componentInstance, Method method, Object controllerInstance) {
        Component component = (Component) componentInstance;
        component.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                WinterEventRouter.invokeEventListener(method, EventType.MouseMove, controllerInstance, e);
            }
        });
    }
}
