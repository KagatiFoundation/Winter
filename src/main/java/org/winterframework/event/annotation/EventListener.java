package org.winterframework.event.annotation;

import org.winterframework.event.EventType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(EventListeners.class)
public @interface EventListener {
	String component(); 
    EventType type();
}