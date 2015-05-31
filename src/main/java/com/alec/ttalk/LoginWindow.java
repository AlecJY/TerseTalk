package com.alec.ttalk;

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
    private JCheckBox rememberCheckBox;
    private JCheckBox autologinCheckBox;
    private JFrame thisFrame = this;

    public LoginWindow() {
        final ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); // load lang
        setTitle("Login - TerseTalk");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());
        getRootPane().setDefaultButton(loginButton);
        add(new FrameTitleBar(this, 2), BorderLayout.NORTH); //  add a title bar
        add(panel);
        pack();

        serverChoice.setSelectedItem("Facebook"); // set default server option
        serverIP.setText("chat.facebook.com"); // set default server
        serverIP.setVisible(false); // set server column invisible
        username.enableInputMethods(false); // set username disable im
        if (!rememberCheckBox.isSelected()) {
            autologinCheckBox.setEnabled(false);
        }

        setVisible(true);

        serverChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) { // set change server option action
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

        loginButton.addActionListener(new ActionListener() { // set login action
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
                    TerseTalk.xmppControl.init(serverIP.getText(), username.getText(), String.copyValueOf(password.getPassword()));
                    new LoginStatus(x, y, thisFrame);
                    //TODO Fix XMPP SSL ISSUE
                }
            }
        });
        rememberCheckBox.addItemListener(new ItemListener() { //set remember checkbox action
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (rememberCheckBox.isSelected()) {
                    autologinCheckBox.setEnabled(true);
                } else {
                    autologinCheckBox.setEnabled(false);
                    autologinCheckBox.setSelected(false);
                }
            }
        });
    }
}
