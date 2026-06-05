package org.winterframework;

import org.winterframework.autoconfigure.WinterApplication;

@WinterApplication
public class AppTest {
    public static void main(String[] args) {
        WinterApplicationRunner.boot(MyFrame.class);
    }
}