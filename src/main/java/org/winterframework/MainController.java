package org.winterframework;

import org.winterframework.core.WinterApplicationRunner;
import org.winterframework.core.annotation.UIComponent;
import org.winterframework.event.EventType;
import org.winterframework.event.annotation.EventListener;
import org.winterframework.core.autoconfigure.WinterApplication;
import org.winterframework.core.autoconfigure.EntryPoint;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

@WinterApplication
public class MainController {
    @UIComponent(text = "Execute Task")
    private JButton submitButton;

    @EventListener(component = "submitButton", type = EventType.ButtonClick)
    public void onSubmit(ActionEvent e) {
        System.out.println("Button was clicked!");
    }

    @EntryPoint
    public void init() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        frame.add(submitButton);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        WinterApplicationRunner.run(MainController.class);
    }
}