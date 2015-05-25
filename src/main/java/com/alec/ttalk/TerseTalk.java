package com.alec.ttalk;

import com.bulenkov.darcula.DarculaLaf;

import javax.swing.*;

/**
 * Created by Alec on 2015/5/24.
 */
public class TerseTalk {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (Exception e) {

        }
        XMPPControl xmppControl = new XMPPControl();
        LoginWindow loginWindow = new LoginWindow(xmppControl);

    }
}
