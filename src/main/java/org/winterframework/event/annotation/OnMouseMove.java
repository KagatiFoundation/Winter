package org.winterframework.event.annotation;

import org.winterframework.event.EventType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WinterEvent(type = EventType.MouseMove)
public @interface OnMouseMove {
    String component() default "";
}