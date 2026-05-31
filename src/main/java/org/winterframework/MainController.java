package org.winterframework;

import org.winterframework.core.WinterApplicationRunner;
import org.winterframework.core.annotation.UIComponent;
import org.winterframework.core.autoconfigure.WinterApplication;
import org.winterframework.core.autoconfigure.EntryPoint;
import org.winterframework.event.annotation.OnClick;
import org.winterframework.event.annotation.OnMouseEnter;
import org.winterframework.event.annotation.OnMouseMove;

import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

@WinterApplication
public class MainController {
    @UIComponent(text = "Execute Task")
    private JButton submitButton;

    @UIComponent(text = "Ramesh")
    private JLabel usernameLabel;

    @UIComponent(text = "Application Frame")
    private JFrame mainFrame;

    @OnClick(component = "submitButton")
    public void onSubmit(ActionEvent e) {
        System.out.println("Button was clicked!");
    }

    @OnMouseEnter(component = "submitButton")
    public void onSubmitButtonHover(MouseEvent e) {
        System.out.println("Mouse entered!");
    }

    @OnMouseMove(component = "mainFrame")
    public void onMouseMoveOnMainFrame() {
        System.out.println("Move ya all!");
    }

    @EntryPoint
    public void init() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
        mainFrame.setLayout(new FlowLayout());

        mainFrame.add(usernameLabel);
        mainFrame.add(submitButton);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        WinterApplicationRunner.run(MainController.class);
    }
}