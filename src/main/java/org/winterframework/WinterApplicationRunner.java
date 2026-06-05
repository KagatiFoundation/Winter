package org.winterframework;

import org.winterframework.autoconfigure.EntryPoint;
import org.winterframework.autoconfigure.component.ComponentInjector;
import org.winterframework.autoconfigure.WinterApplication;
import org.winterframework.autoconfigure.component.ComponentLifecycleManager;
import org.winterframework.core.engine.ComponentTreeBuilder;
import org.winterframework.core.node.ComponentNode;
import org.winterframework.exception.FrameInstantiationException;

import javax.swing.JFrame;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class WinterApplicationRunner {

    public static String ROOT_PACKAGE = "";

    public static void boot(Class<?> rootFrameClass) {
        ROOT_PACKAGE = rootFrameClass.getPackageName();
        System.out.println("Initializing Winter Application Context...");
        System.out.println("Root package locked to: " + ROOT_PACKAGE);

        ComponentNode rootNode = ComponentTreeBuilder.buildTree(rootFrameClass);
        printTree(rootNode, "");
    }

    private static void printTree(ComponentNode node, String indent) {
        String fieldName = node.getDeclaringField() != null ? " (" + node.getDeclaringField().getName() + ")" : " [Root]";
        System.out.println(indent + "└── " + node.getComponentClass().getSimpleName() + fieldName);
        for (ComponentNode child : node.getChildren()) {
            printTree(child, indent + "    ");
        }
    }

    public static void run(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(WinterApplication.class)) {
            System.err.println("Class is not annotated with WinterApplication.");
            return;
        }

        FrameProperties mainFrameProps = FrameLoader.findMainFrame(clazz);
        JFrame mainFrameInstance = instantiateFrame(mainFrameProps);

        bootFrameLifecycle(mainFrameInstance);

        try {
            Object appInstance = clazz.getDeclaredConstructor().newInstance();
            ComponentInjector.inject(appInstance);

            for (Method method: clazz.getDeclaredMethods()) {
                method.setAccessible(true);

                if (method.isAnnotationPresent(EntryPoint.class)) {
                    method.invoke(appInstance);
                    break;
                }
            }
        }
        catch (NoSuchMethodException e) {
            System.err.println("Winter Error: Your application class must have a default, no-argument constructor.");
        }
        catch (InvocationTargetException e) {
            System.err.println("Winter Error: The entry point method threw an exception while running.");
            e.getCause().printStackTrace();
        }
        catch (Exception e) {
            System.err.println("Winter Error: Failed to initialize or execute application context.");
            e.printStackTrace();
        }

    }

    private static void bootFrameLifecycle(JFrame frameInstance) {
        ComponentLifecycleManager.invokeBeforeMount(frameInstance);

    }

    private static JFrame instantiateFrame(FrameProperties props) {
        JFrame mainFrameInstance = null;

        try {
            mainFrameInstance = (JFrame) props.mainFrameClass().getDeclaredConstructor(String.class).newInstance(props.title());
        }
        catch (NoSuchMethodException | InstantiationException e) {
            throw new FrameInstantiationException(props.mainFrameClass());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return mainFrameInstance;
    }
}
