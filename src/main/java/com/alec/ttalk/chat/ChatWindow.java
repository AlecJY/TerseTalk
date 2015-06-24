package com.alec.ttalk.chat;

import com.alec.ttalk.TerseTalk;
import com.alec.ttalk.common.XMPPControl;
import com.alec.ttalk.struct.UserInfo;
import com.sun.jmx.snmp.Timestamp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/6/19.
 */
public class ChatWindow extends JFrame {
    private JButton sendButton;
    private JTextField message;
    private JPanel panel;
    private JPanel titlePane;
    private JPanel messageMainPane;
    private XMPPControl xmppControl = TerseTalk.xmppControl;

    private JPanel messagePane = new JPanel();
    private JScrollPane messageScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private JScrollBar verticalScrollBar = messageScrollPane.getVerticalScrollBar();
    private JTextPane messageArea = new JTextPane();

    private String jid;
    private String messageString = "<html>";
    private boolean isTyping = false;
    private int messageCounter = 0;
    private UserInfo info;
    private ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); // load lang

    public ChatWindow(String jid) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());
        setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("image/tTalk.png")));
        getRootPane().setDefaultButton(sendButton);

        this.jid = jid;

        info = xmppControl.getLightUserInfo(jid);
        if (info.name == null) {
            info.name = info.jid;
        }

        info = xmppControl.getUserInfo(jid);

        JPanel titleBar = new ChatWindowTitleBar(this, jid);
        titlePane.add(titleBar);

        messageArea.setEditable(false);
        messageArea.setContentType("text/html");
        messageArea.setBackground(null);
        messageArea.setBorder(null);

        Font font = UIManager.getFont("Label.font");
        Font DEFAULT_FONT = (font != null) ? font: new Font("Tahoma", Font.PLAIN, 11);
        messageArea.setFont(DEFAULT_FONT);
        messageArea.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

        messageScrollPane.getViewport().add(messageArea);
        messageMainPane.add(messageScrollPane);

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
        messageString = messageString + "<font color=\"#66B3FF\">You: </font>" + message.getText() + "<br/>";
        messageArea.setText(messageString + "</html>");
        messagePane.revalidate();
        scrollToBottom();
        message.setText(null);
    }

    public void addMessage(String message) {
        setDefaultButton();
        messageString = messageString + "<font color=\"#B15BFF\">" + info.name + ": </font>" + message + "<br/>";
        messageArea.setText(messageString + "</html>");
        messagePane.revalidate();
        scrollToBottom();
    }

    public void setTyping() {
        setDefaultButton();
        if (isTyping == false) {
            isTyping = true;
            messageArea.setText(messageString + "<font color=\"#EAC100\">" + info.name + lang.getString("ChatWindow.typing") +"</font></html>");
        } else {
            isTyping = false;
            messageArea.setText(messageString + "</html>");
        }
        messagePane.revalidate();
        scrollToBottom();
    }

    public void setDefaultButton() {
        getRootPane().setDefaultButton(sendButton);
    }
}
