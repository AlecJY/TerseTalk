package com.alec.ttalk.common;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/5/31.
 */
public class MessageBox {
    private boolean yesNoChoice = false;
    private JDialog dialog = new JDialog();

    public MessageBox(String title, String message, int x, int y, int mode) {
        ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); //  load lang
        JPanel msgPane = new JPanel(new GridBagLayout());
        JPanel mainPane = new JPanel(new BorderLayout());
        JLabel msg = new JLabel(message);
        JButton okButton = new JButton(lang.getString("okButton"));
        JButton yesButton = new JButton(lang.getString("yesButton"));
        GridBagConstraints g = new GridBagConstraints();

        okButton.addActionListener(e -> dialog.dispose());
        yesButton.addActionListener(e -> yes());

        msgPane.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));
        g.gridx = 0;
        g.gridy = 0;
        g.anchor = GridBagConstraints.CENTER;
        msgPane.add(msg, g);
        g.gridy = 1;
        if (mode == 2) { // yes no messagebox mode
            JPanel yesNoPane = new JPanel();

            yesNoPane.add(yesButton);
            okButton.setText(lang.getString("noButton"));
            yesNoPane.add(okButton);

            msgPane.add(yesNoPane, g);
        } else {
            msgPane.add(okButton, g);
        }

        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setUndecorated(true);

        mainPane.setBorder(BorderFactory.createLineBorder(new Color(97, 101, 104)));
        mainPane.add(new DialogTitleBar(dialog), BorderLayout.NORTH);
        mainPane.add(msgPane);

        dialog.add(mainPane);
        dialog.getRootPane().setDefaultButton(okButton);
        dialog.pack();
        dialog.setLocation(x - dialog.getWidth()/2, y - dialog.getHeight()/2);

        dialog.setVisible(true);
    }

    private void yes() {
        yesNoChoice = true;
        dialog.dispose();
    }

    public boolean choice() {
        return yesNoChoice;
    }
}
