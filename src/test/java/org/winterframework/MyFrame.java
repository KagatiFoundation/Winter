package org.winterframework;

import org.winterframework.stereotype.Component;
import org.winterframework.stereotype.Frame;

import javax.swing.*;

@Frame(title = "Main Frame")
public class MyFrame extends JFrame {
    private MyPanel panel;
    private MyPanel2 panel2;
}

@Component(text = "My Panel")
class MyPanel extends JPanel {
    private MyPanel2 panel;
}

@Component(text = "My Panel 2")
class MyPanel2 extends JPanel {

}