package com.alec.ttalk;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/5/24.
 */
public class LoginStatus {
    private ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); //  load lang
    private JFrame parent;
    private JDialog dialog = new JDialog();
    private JLabel status = new JLabel(lang.getString("loginstatus.connecting"));
    private int x , y;

    private XMPPControl xmppControl = TerseTalk.xmppControl;

    public LoginStatus(int x, int y, JFrame parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;

        JPanel panel = new JPanel();
        JProgressBar progressBar = new JProgressBar();
        dialog.setUndecorated(true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        dialog.setModal(true);

        progressBar.setIndeterminate(true); // always running

        panel.setLayout(new GridLayout(2, 1));
        panel.add(status);
        panel.add(progressBar);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(97, 101, 104)), BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        dialog.add(panel);
        dialog.pack();
        dialog.setLocation(x - dialog.getWidth()/2, y - dialog.getHeight()/2);

        Connect connect = new Connect();
        connect.execute();

        dialog.setVisible(true);
    }



    private class Connect extends SwingWorker<Void, Void> {
        boolean isConnected;

        @Override
        public Void doInBackground() throws Exception {
            isConnected = xmppControl.connect();
            return null;
        }

        @Override
        public void done() {
            if (!isConnected) {
                dialog.dispose();
                new MessageBox(lang.getString("loginstatus.errortitle"), lang.getString("loginstatus.connectionfailed"), x, y);

            } else {
                status.setText(lang.getString("loginstatus.login"));
                (new Login()).execute();
            }
        }
    }

    private class Login extends SwingWorker<Void, Void> {
        boolean isLogin;

        @Override
        protected Void doInBackground() throws Exception {
            isLogin = xmppControl.login();
            return null;
        }

        @Override
        public void done() {
            if (!isLogin) {
                dialog.dispose();
                new MessageBox(lang.getString("loginstatus.errortitle"), lang.getString("loginstatus.loginfailed"), x, y);
            } else {
                parent.dispose();
            }
        }
    }
}
