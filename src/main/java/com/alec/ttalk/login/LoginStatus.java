package com.alec.ttalk.login;


import com.alec.ttalk.common.MessageBox;
import com.alec.ttalk.TerseTalk;
import com.alec.ttalk.common.XMPPControl;

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
    private JLabel status = new JLabel(lang.getString("LoginStatus.connecting"));
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
                if (!xmppControl.isSSLCert()) {
                    MessageBox disableSSLCert =  new MessageBox(lang.getString("LoginStatus.warningTitle"), lang.getString("LoginStatus.disableSSLCert"), x, y, 2);
                    if (disableSSLCert.choice()) {
                        xmppControl.disableSSLCert();
                        (new Connect()).execute();
                    } else {
                        dialog.dispose();
                        new MessageBox(lang.getString("LoginStatus.errorTitle"), lang.getString("LoginStatus.connectionFailed"), x, y, 1);
                    }
                } else {
                    dialog.dispose();
                    new MessageBox(lang.getString("LoginStatus.errorTitle"), lang.getString("LoginStatus.connectionFailed"), x, y, 1);
                }

            } else {
                status.setText(lang.getString("LoginStatus.login"));
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
                new MessageBox(lang.getString("LoginStatus.errorTitle"), lang.getString("LoginStatus.loginFailed"), x, y, 1);
            } else {
                parent.dispose();
                dialog.dispose();
            }
        }
    }
}
