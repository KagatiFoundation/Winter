package org.winterframework.exception;

import org.winterframework.WinterException;

public class FrameInstantiationException extends WinterException {
    public FrameInstantiationException(Class<?> frameClass) {
        super(String.format(
            "Failed to instantiate frame [%s]. A @Frame-annotated class must provide a constructor that accepts a String (title) argument.",
            frameClass.getName()
        ));
    }
}