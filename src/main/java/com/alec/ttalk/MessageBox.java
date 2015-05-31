package com.alec.ttalk;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/5/31.
 */
public class MessageBox {
    public MessageBox(String title, String message, int x, int y) {
        ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); //  load lang
        JDialog dialog = new JDialog();
        JPanel msgPane = new JPanel(new GridBagLayout());
        JPanel mainPane = new JPanel(new BorderLayout());
        JLabel msg = new JLabel(message);
        JButton okButton = new JButton(lang.getString("okbutton"));
        GridBagConstraints g = new GridBagConstraints();

        okButton.addActionListener(e -> dialog.dispose());

        msgPane.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));
        g.gridx = 0;
        g.gridy = 0;
        g.anchor = GridBagConstraints.CENTER;
        msgPane.add(msg, g);
        g.gridy = 1;
        msgPane.add(okButton, g);

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
}
