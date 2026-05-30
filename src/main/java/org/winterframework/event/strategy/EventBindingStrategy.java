package org.winterframework.event.strategy;

import org.winterframework.event.EventType;

import java.lang.reflect.Method;

public interface EventBindingStrategy {
    boolean supports(EventType type, Object componentInstance);

    void bind(Object componentInstance, Method method, Object controllerInstance);
}