package org.winterframework;

import org.winterframework.autoconfigure.WinterApplication;
import org.winterframework.core.context.WinterContext;
import org.winterframework.core.engine.ComponentTreeBuilder;
import org.winterframework.core.node.ComponentNode;
import org.winterframework.exception.FrameInstantiationException;

import javax.swing.JFrame;

public class WinterApplicationRunner {

    public static String ROOT_PACKAGE = "";

    public static void bootApplication(Class<?> klass) {
        if (!klass.isAnnotationPresent(WinterApplication.class)) {
            System.err.println("Class is not annotated with WinterApplication.");
            return;
        }

        ROOT_PACKAGE = klass.getPackageName();

        FrameProperties mainFrameProps = FrameLoader.findMainFrame(klass);
        JFrame mainFrameInstance = instantiateFrame(mainFrameProps);

        WinterContext.register(mainFrameProps.mainFrameClass(), mainFrameInstance);

        launch(mainFrameProps.mainFrameClass());
    }

    private static void launch(Class<?> mainFrameClass) {
        JFrame mainFrame = (JFrame) WinterContext.getInstance(mainFrameClass);
        ComponentNode rootNode = ComponentTreeBuilder.buildTree(mainFrame.getClass());
        printTree(rootNode, "");
    }

    private static void printTree(ComponentNode node, String indent) {
        String fieldName = node.getDeclaringField() != null ? " (" + node.getDeclaringField().getName() + ")" : " [Root]";
        System.out.println(indent + "└── " + node.getComponentClass().getSimpleName() + fieldName);
        for (ComponentNode child : node.getChildren()) {
            printTree(child, indent + "    ");
        }
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
