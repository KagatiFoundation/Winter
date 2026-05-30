package org.winterframework.event.strategy;

import org.winterframework.event.EventType;
import org.winterframework.event.WinterEventRouter;

import javax.swing.AbstractButton;
import java.lang.reflect.Method;

public class ButtonClickStrategy implements EventBindingStrategy {
    @Override
    public boolean supports(EventType type, Object componentInstance) {
        return type == EventType.ButtonClick && componentInstance instanceof AbstractButton;
    }

    @Override
    public void bind(Object componentInstance, Method method, Object controllerInstance) {
        AbstractButton button = (AbstractButton) componentInstance;
        button.addActionListener(e -> WinterEventRouter.invokeEventListener(method, EventType.ButtonClick, controllerInstance, e));
    }
}