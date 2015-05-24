package com.alec.brieftalk;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * Created by Alec on 2015/5/24.
 */
public class BriefTalk {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {

        }

        LoginWindow loginWindow = new LoginWindow();
    }
}
