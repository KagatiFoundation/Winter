package org.winterframework;

import org.winterframework.core.WinterApplicationRunner;
import org.winterframework.core.annotation.UIComponent;
import org.winterframework.core.autoconfigure.WinterApplication;
import org.winterframework.core.autoconfigure.EntryPoint;
import org.winterframework.event.annotation.OnClick;
import org.winterframework.event.annotation.OnMouseEnter;

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

    @OnClick(component = "submitButton")
    public void onSubmit(ActionEvent e) {
        System.out.println("Button was clicked!");
    }

    @OnMouseEnter(component = "submitButton")
    public void onSubmitButtonHover(MouseEvent e) {
        System.out.println("Mouse entered!");
    }

    @EntryPoint
    public void init() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        frame.add(usernameLabel);
        frame.add(submitButton);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        WinterApplicationRunner.run(MainController.class);
    }
}