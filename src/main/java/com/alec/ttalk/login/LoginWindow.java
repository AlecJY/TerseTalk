package com.alec.ttalk.login;

import com.alec.ttalk.common.FrameTitleBar;
import com.alec.ttalk.TerseTalk;
import com.alec.ttalk.struct.WindowLocation;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

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
public class LoginWindow extends JFrame {
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

        rememberCheckBox.setVisible(false);
        autologinCheckBox.setVisible(false);

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
                    } else {
                        serverIP.setText("gmail.com");
                        serverIP.setVisible(false);
                    }
                }
            }
        });

        loginButton.addActionListener(new ActionListener() { // set login action
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverIP.getText().equals("")) {
                    warning.setText(lang.getString("LoginWindow.emptyServerIP"));
                } else if (username.getText().equals("")) {
                    warning.setText(lang.getString("LoginWindow.emptyUsername"));
                } else if (password.getPassword().length == 0) {
                    warning.setText(lang.getString("LoginWindow.emptyPassword"));
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

    public WindowLocation windowXY() {
        WindowLocation windowLocation = new WindowLocation();

        windowLocation.x = getX() + getWidth() / 2;
        windowLocation.y = getY() + getHeight() / 2;
        return windowLocation;
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
        panel.setLayout(new GridLayoutManager(5, 1, new Insets(20, 20, 40, 20), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        serverChoice = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Facebook");
        defaultComboBoxModel1.addElement("Hangout");
        defaultComboBoxModel1.addElement("Other");
        serverChoice.setModel(defaultComboBoxModel1);
        panel1.add(serverChoice, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(120, -1), new Dimension(120, -1), new Dimension(120, -1), 0, false));
        final JLabel label1 = new JLabel();
        this.$$$loadLabelText$$$(label1, ResourceBundle.getBundle("lang/tTalk").getString("LoginWindow.server"));
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        serverIP = new JTextField();
        panel1.add(serverIP, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(30, -1), null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("");
        panel1.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 30), null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(panel2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        username = new JTextField();
        panel2.add(username, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setEnabled(true);
        this.$$$loadLabelText$$$(label3, ResourceBundle.getBundle("lang/tTalk").getString("LoginWindow.username"));
        panel2.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), null, null, 0, false));
        final JLabel label4 = new JLabel();
        this.$$$loadLabelText$$$(label4, ResourceBundle.getBundle("lang/tTalk").getString("LoginWindow.password"));
        panel2.add(label4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), null, null, 0, false));
        password = new JPasswordField();
        panel2.add(password, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        rememberCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(rememberCheckBox, ResourceBundle.getBundle("lang/tTalk").getString("LoginWindow.rememberPassword"));
        panel3.add(rememberCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autologinCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(autologinCheckBox, ResourceBundle.getBundle("lang/tTalk").getString("LoginWindow.autologin"));
        panel3.add(autologinCheckBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loginButton = new JButton();
        this.$$$loadButtonText$$$(loginButton, ResourceBundle.getBundle("lang/tTalk").getString("LoginWindow.login"));
        panel.add(loginButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        warning = new JLabel();
        warning.setForeground(new Color(-65536));
        warning.setText("");
        panel.add(warning, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 20), null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setIcon(new ImageIcon(getClass().getResource("/image/tTalk_title.png")));
        label5.setText("");
        panel4.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel4.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 5), null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadLabelText$$$(JLabel component, String text) {
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
            component.setDisplayedMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
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
}
