package org.winterframework.components;

import org.winterframework.core.annotation.UIComponent;
import org.winterframework.core.autoconfigure.PostConstruct;
import org.winterframework.event.annotation.OnClick;
import org.winterframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;

@Controller
public class UserProfileComponent extends JPanel {
    @UIComponent(text = "Edit")
    private JButton editButton;

    @OnClick(component = "editButton")
    private void onClick() {
        System.out.println("Edit!");
    }

    @PostConstruct
    private void setupUI() {
        add(editButton);
    }

    public UserProfileComponent() {
        setLayout(new FlowLayout());
    }
}