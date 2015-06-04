package com.alec.ttalk;

import com.alec.ttalk.common.XMPPControl;
import com.alec.ttalk.login.LoginWindow;
import com.alec.ttalk.struct.WindowLocation;
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

        WindowLocation XY = loginWindow.windowXY(); // record login window's location


    }
}
