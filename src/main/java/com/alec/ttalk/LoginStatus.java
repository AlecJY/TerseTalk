package com.alec.ttalk;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/5/24.
 */
public class LoginStatus {
    private ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); //  load lang
    private JDialog dialog = new JDialog();
    private JLabel status = new JLabel(lang.getString("loginstatus.connecting"));
    private int x , y;

    private XMPPControl xmppControl = TerseTalk.xmppControl;

    public LoginStatus(int x, int y) {
        this.x = x;
        this.y = y;

        JPanel panel = new JPanel();
        JProgressBar progressBar = new JProgressBar();
        dialog.setUndecorated(true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLocation(x-75, y-50);
        dialog.setLayout(new BorderLayout());
        dialog.setModal(true);

        progressBar.setIndeterminate(true); // always running

        panel.setLayout(new GridLayout(2, 1));
        panel.add(status);
        panel.add(progressBar);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(97, 101, 104)), BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        dialog.add(panel);
        dialog.pack();

        Connect connect = new Connect();
        connect.execute();

        dialog.setVisible(true);
    }

    private void errorDialog(String errMsg) {
        JDialog error = new JDialog();
        JPanel errorPane = new JPanel(new GridBagLayout());
        JPanel mainPane = new JPanel(new BorderLayout());
        JLabel msg = new JLabel(errMsg);
        JButton okButton = new JButton(lang.getString("okbutton"));
        GridBagConstraints g = new GridBagConstraints();

        okButton.addActionListener(e -> error.dispose());

        errorPane.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));
        g.gridx = 0;
        g.gridy = 0;
        g.anchor = GridBagConstraints.CENTER;
        errorPane.add(msg, g);
        g.gridy = 1;
        errorPane.add(okButton, g);

        error.setTitle("Login Error - TerseTalk");
        error.setModal(true);
        error.setLocation(x - 75, y - 50);
        error.setUndecorated(true);

        mainPane.setBorder(BorderFactory.createLineBorder(new Color(97, 101, 104)));
        mainPane.add(new DialogTitleBar(error), BorderLayout.NORTH);
        mainPane.add(errorPane);

        error.add(mainPane);
        error.getRootPane().setDefaultButton(okButton);
        error.pack();

        error.setVisible(true);
    }

    private class Connect extends SwingWorker<Void, Void> {
        boolean isConnected = false;

        @Override
        public Void doInBackground() {
            isConnected = xmppControl.connect();
            return null;
        }

        @Override
        public void done() {
            if (isConnected) {
                dialog.dispose();
                errorDialog("Connecting to Server failed");

            } else {
                status.setText(lang.getString("loginstatus.login"));
            }
        }
    }
}
