package com.alec.brieftalk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Alec on 2015/5/24.
 */
public class LoginWindow extends JFrame{
    private JComboBox serverChoice;
    private JTextField serverIP;
    private JTextField username;
    private JButton LoginButton;
    private JPanel panel;
    private JPasswordField password;

    public LoginWindow() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());
        add(panel);
        pack();

        serverChoice.setSelectedItem("Facebook");
        serverIP.setVisible(false);
        username.enableInputMethods(false);

        serverChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getItem().equals("Other")) {
                        serverIP.setVisible(true);
                    }
                    else {
                        serverIP.setVisible(false);
                    }
                }
            }
        });

        setVisible(true);
    }
}
