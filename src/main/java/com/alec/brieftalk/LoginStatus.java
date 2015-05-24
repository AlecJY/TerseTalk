package com.alec.brieftalk;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/5/24.
 */
public class LoginStatus extends SwingWorker{
    private JDialog dialog = new JDialog();
    protected Void doInBackground() {
        dialog.setVisible(true);
        return null;
    }
    public LoginStatus(int x, int y) {
        ResourceBundle lang = ResourceBundle.getBundle("lang/BriefTalk");

        JPanel panel = new JPanel();
        JLabel connecting = new JLabel(lang.getString("loginstatus.connecting"));
        JLabel login = new JLabel(lang.getString("loginstatus.login"));
        JProgressBar progressBar = new JProgressBar();

        dialog.setUndecorated(true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLocation(x-75, y-50);
        dialog.setLayout(new BorderLayout());
        dialog.setModal(true);

        progressBar.setIndeterminate(true);

        panel.setLayout(new GridLayout(2, 1));
        panel.add(connecting);
        panel.add(progressBar);

        dialog.add(panel);
        dialog.pack();
    }
}
