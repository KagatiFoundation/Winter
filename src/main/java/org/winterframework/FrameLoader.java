package org.winterframework;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationParameterValueList;

import org.winterframework.stereotype.Frame;

import javax.swing.JFrame;

public class FrameLoader {
    public static FrameProperties findMainFrame(Class<?> primarySource) {
        String userRootPackage = primarySource.getPackageName();

        try (ScanResult scanResult = new ClassGraph()
            .verbose()
            .enableAllInfo()
            .acceptPackages(userRootPackage)
            .scan()
        ) {
            ClassInfoList mainFrameClasses = scanResult.getClassesWithAnnotation(Frame.class.getName());

            if (mainFrameClasses.isEmpty()) {
                throw new IllegalStateException("No class annotated with @Frame found in the project!");
            }
            if (mainFrameClasses.size() > 1) {
                throw new IllegalStateException("Multiple @Frame classes found. Only one is allowed.");
            }

            ClassInfo mainFrameClass = mainFrameClasses.iterator().next();

            if (!mainFrameClass.extendsSuperclass(JFrame.class)) {
                throw new IllegalArgumentException("@Frame can only be applied to subclasses of JFrame");
            }

            AnnotationInfo annotationInfo = mainFrameClass.getAnnotationInfo(Frame.class.getName());
            AnnotationParameterValueList parameterValues = annotationInfo.getParameterValues();

            String title = (String) parameterValues.getValue("title");
            int width = (int) parameterValues.getValue("width");
            int height = (int) parameterValues.getValue("height");

            Class<?> actualFrameClass = mainFrameClass.loadClass();
            return new FrameProperties(actualFrameClass, title, height, width);
        }
    }
}