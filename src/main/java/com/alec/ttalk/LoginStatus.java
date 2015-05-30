package com.alec.ttalk;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/5/24.
 */
public class LoginStatus {
    private JDialog dialog = new JDialog();

    public LoginStatus(int x, int y) {
        ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); //  load lang

        JPanel panel = new JPanel();
        JLabel connecting = new JLabel(lang.getString("loginstatus.connecting"));
        JLabel login = new JLabel(lang.getString("loginstatus.login"));
        JProgressBar progressBar = new JProgressBar();
        dialog.setUndecorated(true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLocation(x-75, y-50);
        dialog.setLayout(new BorderLayout());
        dialog.setModal(true);

        progressBar.setIndeterminate(true); // always running

        panel.setLayout(new GridLayout(2, 1));
        panel.add(connecting);
        panel.add(progressBar);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(97, 101, 104)), BorderFactory.createEmptyBorder(10, 10, 10, 10)));


        dialog.add(panel);
        dialog.pack();
        dialog.setVisible(true);
    }
}
