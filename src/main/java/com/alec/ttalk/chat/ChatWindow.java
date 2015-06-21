package com.alec.ttalk.chat;

import com.alec.ttalk.TerseTalk;
import com.alec.ttalk.common.XMPPControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alec on 2015/6/19.
 */
public class ChatWindow extends JFrame {
    private JButton sendButton;
    private JTextField message;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JPanel chatPane;
    private JPanel titlePane;
    private JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
    private XMPPControl xmppControl = TerseTalk.xmppControl;

    private String jid;
    private boolean isTyping = false;

    public ChatWindow(String jid) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());
        getRootPane().setDefaultButton(sendButton);

        this.jid = jid;

        JPanel infoPane = new JPanel();
        infoPane.add(new JLabel(new ImageIcon()));
        //TODO fix infoPane
        //titlePane.add(infoPane);

        add(panel);
        pack();
        setVisible(true);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    public void scrollToBottom() {
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }

    public void sendMessage() {
        xmppControl.sendMessage(jid, message.getText());
        message.setText(null);
    }

    public void addMessage(String message) {

    }

    public void setTyping() {
        if (isTyping = false) {
            isTyping = true;
        } else {
            isTyping =false;
        }
    }
}
