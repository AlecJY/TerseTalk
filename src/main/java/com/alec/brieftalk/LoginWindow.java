package com.alec.brieftalk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/5/24.
 */
public class LoginWindow extends JFrame{
    private JComboBox serverChoice;
    private JTextField serverIP;
    private JTextField username;
    private JButton loginButton;
    private JPanel panel;
    private JPasswordField password;
    private JLabel warning;

    public LoginWindow(XMPPControl xmppControl) {
        ResourceBundle lang = ResourceBundle.getBundle("lang/BriefTalk");
        setTitle("Login - BriefTalk");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());
        getRootPane().setDefaultButton(loginButton);
        add(panel);
        pack();

        serverChoice.setSelectedItem("Facebook");
        serverIP.setText("chat.facebook.com");
        serverIP.setVisible(false);
        username.enableInputMethods(false);

        setVisible(true);

        serverChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getItem().equals("Other")) {
                        serverIP.setText("");
                        serverIP.setVisible(true);
                    } else if (e.getItem().equals("Facebook")) {
                        serverIP.setText("chat.facebook.com");
                        serverIP.setVisible(false);
                    }
                    else {
                        serverIP.setText("talk.google.com");
                        serverIP.setVisible(false);
                    }
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverIP.getText().equals("")) {
                    warning.setText(lang.getString("loginwindow.emptyserverip"));
                } else if (username.getText().equals("")) {
                    warning.setText(lang.getString("loginwindow.emptyusername"));
                } else if (password.getPassword().length == 0) {
                    warning.setText(lang.getString("loginwindow.emptypassword"));
                } else {
                    int x = getX() + getWidth() / 2;
                    int y = getY() + getHeight() / 2;
                    //new LoginStatus(x, y).execute();
                    xmppControl.init(serverIP.getText(), username.getText(), password.getPassword());
                    System.out.println(xmppControl.connect());
                    System.out.println(xmppControl.login());
                }
            }
        });
    }
}
