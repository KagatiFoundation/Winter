package org.winterframework;

public record FrameProperties(
    Class<?> mainFrameClass,
    String title,
    int height,
    int width
) {}