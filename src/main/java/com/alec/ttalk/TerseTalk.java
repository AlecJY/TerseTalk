package com.alec.ttalk;

import com.alec.ttalk.about.AboutWindow;
import com.alec.ttalk.chat.ChatWindowManager;
import com.alec.ttalk.common.XMPPControl;
import com.alec.ttalk.login.LoginWindow;
import com.apple.eawt.AboutHandler;
import com.apple.eawt.AppEvent;
import com.apple.eawt.Application;
import com.apple.laf.AquaLookAndFeel;

import javax.swing.*;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/5/24.
 */
public class TerseTalk {
    public static final Application terseTalk = Application.getApplication();

    public static final XMPPControl xmppControl = new XMPPControl();
    public static final ChatWindowManager chatWindowManager = new ChatWindowManager();

    public static void main(String[] args) {
        ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); // load lang

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "TerseTalk");
        terseTalk.setDockIconImage(new ImageIcon(TerseTalk.class.getClassLoader().getResource("image/tTalk.png")).getImage());
        terseTalk.setAboutHandler(new AboutHandler() {
            @Override
            public void handleAbout(AppEvent.AboutEvent aboutEvent) {
                new AboutWindow(null);
            }
        });

        try {
            UIManager.setLookAndFeel(new AquaLookAndFeel()); // set look and feel to Aqua look and feel
        } catch (Exception e) { // if set failed, exit
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null, lang.getString("aquaError"), "TerseTalk", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }

        LoginWindow loginWindow = new LoginWindow(); // add login window
    }
}
