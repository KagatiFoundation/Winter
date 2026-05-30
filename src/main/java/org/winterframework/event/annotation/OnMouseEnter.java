package org.winterframework.event.annotation;

import org.winterframework.event.EventType;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WinterEvent(type = EventType.MouseEnter)
public @interface OnMouseEnter {
    String component();
}