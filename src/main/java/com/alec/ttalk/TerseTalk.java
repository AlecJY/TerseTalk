package com.alec.ttalk;

import com.bulenkov.darcula.DarculaLaf;

import javax.swing.*;

/**
 * Created by Alec on 2015/5/24.
 */
public class TerseTalk {
    public static final XMPPControl xmppControl = new XMPPControl();
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new DarculaLaf()); // set look and feel to Darcula look and feel
        } catch (Exception e) {

        }
        LoginWindow loginWindow = new LoginWindow(); // add login window

    }
}
