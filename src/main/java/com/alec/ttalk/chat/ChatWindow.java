package com.alec.ttalk.chat;

import com.alec.ttalk.TerseTalk;
import com.alec.ttalk.common.XMPPControl;
import com.alec.ttalk.struct.UserInfo;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/6/19.
 */
public class ChatWindow extends JFrame {
    private JButton sendButton;
    private JTextArea message;
    private JPanel panel;
    private JPanel titlePane;
    private JPanel messageMainPane;
    private JPanel mainPane;
    private XMPPControl xmppControl = TerseTalk.xmppControl;

    private JPanel messagePane = new JPanel();
    private JScrollPane messageScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private JTextPane messageArea = new JTextPane();

    private String jid;
    private String messageString = "<html>";
    private boolean isTyping = false;
    private int messageCounter = 0;
    private UserInfo info;
    private ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); // load lang
    private boolean scroll = false;

    public ChatWindow(String jid) {
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getRootPane().setDefaultButton(sendButton);

        message.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doNothing");

        messageScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (scroll == true) {
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                    scroll = false;
                }
            }
        });

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
        Font DEFAULT_FONT = (font != null) ? font : new Font("Tahoma", Font.PLAIN, 11);
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

    public void sendMessage() {
        if (!message.getText().equals("")) {
            scroll = true;
            xmppControl.sendMessage(jid, message.getText());
            messageString = messageString + "<font color=\"#013ADF\">You: </font>" + message.getText() + "<br/>";
            messageArea.setText(messageString + "</html>");
            messagePane.revalidate();
            message.setText(null);
        }
    }

    public void addMessage(String message) {
        scroll = true;
        messageString = messageString + "<font color=\"#DF01D7\">" + info.name + ": </font>" + message + "<br/>";
        messageArea.setText(messageString + "</html>");
        messageScrollPane.revalidate();
        scroll = true;
        (new ScrollFix()).execute();
        try {
            URL url = getClass().getClassLoader().getResource("sound/alert.aif");
            AudioInputStream sound = AudioSystem.getAudioInputStream(url);
            DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(sound);
            clip.start();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void setTyping() {
        scroll = true;
        if (isTyping == false) {
            isTyping = true;
            messageArea.setText(messageString + "<font color=\"#31AB70\">" + info.name + lang.getString("ChatWindow.typing") + "</font></html>");
        } else {
            isTyping = false;
            messageArea.setText(messageString + "</html>");
        }
        messageScrollPane.revalidate();
        scroll = true;
        (new ScrollFix()).execute();
    }

    public void setDefaultButton() {
        getRootPane().setDefaultButton(sendButton);
    }

    public void scroll() {
        messageScrollPane.getVerticalScrollBar().setValue(messageScrollPane.getVerticalScrollBar().getMaximum());
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 6, 0), -1, -1));
        titlePane = new JPanel();
        titlePane.setLayout(new BorderLayout(0, 0));
        panel.add(titlePane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        mainPane = new JPanel();
        mainPane.setLayout(new GridLayoutManager(2, 1, new Insets(0, 2, 0, 2), -1, -1));
        panel.add(mainPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        messageMainPane = new JPanel();
        messageMainPane.setLayout(new BorderLayout(0, 0));
        mainPane.add(messageMainPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(250, 300), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        sendButton = new JButton();
        this.$$$loadButtonText$$$(sendButton, ResourceBundle.getBundle("lang/tTalk").getString("ChatWindow.send"));
        panel1.add(sendButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setVerticalScrollBarPolicy(20);
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 40), null, 0, false));
        message = new JTextArea();
        message.setLineWrap(true);
        message.setRows(0);
        scrollPane1.setViewportView(message);
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

    private class ScrollFix extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() throws Exception {
            Thread.sleep(50);
            scroll = true;
            return null;
        }
    }

}
