package org.winterframework;

import org.winterframework.components.UserProfileComponent;
import org.winterframework.core.WinterApplicationRunner;
import org.winterframework.core.annotation.UIComponent;
import org.winterframework.core.annotation.UIContainer;
import org.winterframework.core.autoconfigure.WinterApplication;
import org.winterframework.core.autoconfigure.EntryPoint;
import org.winterframework.event.annotation.OnClick;

import javax.swing.*;
import java.awt.FlowLayout;

@WinterApplication
public class AppTest {
    @UIComponent(text = "Main Frame")
    private JFrame mainFrame;

    @UIComponent(text = "Click Me!")
    private JButton clicker;

    @OnClick(component = "clicker")
    private void onClick() {
        System.out.println("Clicked!!!");
    }

    @UIContainer(children = { "clicker" })
    private UserProfileComponent profileContainer;

    @EntryPoint
    public void init() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
        mainFrame.setLayout(new FlowLayout());

        mainFrame.add(profileContainer);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        WinterApplicationRunner.run(AppTest.class);
    }
}