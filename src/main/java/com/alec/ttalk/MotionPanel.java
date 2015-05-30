package com.alec.ttalk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

// get from http://stackoverflow.com/questions/10773713/moving-undecorated-window-by-clicking-on-jpanel

public class MotionPanel extends JPanel{
    private Point initialClick;
    private JFrame parent;

    public MotionPanel(final JFrame parent, int mode){
        this.parent = parent;
        setLayout(new BorderLayout());

        JPanel actionPane = new JPanel();
        ImageIcon closeIcon = new ImageIcon(getClass().getClassLoader().getResource("image/close.png"));
        ImageIcon closeMouseIcon = new ImageIcon(getClass().getClassLoader().getResource("image/close_mouse.png"));
        ImageIcon iconifiedIcon = new ImageIcon(getClass().getClassLoader().getResource("image/iconified.png"));
        ImageIcon iconifiedMouseIcon = new ImageIcon(getClass().getClassLoader().getResource("image/iconified_mouse.png"));
        JButton closeButton = new JButton(closeIcon);
        JButton iconifiedButton = new JButton(iconifiedIcon);
        closeButton.setRolloverIcon(closeMouseIcon);
        closeButton.setPressedIcon(closeMouseIcon);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setContentAreaFilled(false);
        iconifiedButton.setRolloverIcon(iconifiedMouseIcon);
        iconifiedButton.setPressedIcon(iconifiedMouseIcon);
        iconifiedButton.setBorder(BorderFactory.createEmptyBorder());
        iconifiedButton.setContentAreaFilled(false);

        closeButton.addActionListener(e -> System.exit(0));
        iconifiedButton.addActionListener(e -> iconifiedWindow());
        if (mode > 1) {
            actionPane.add(iconifiedButton);
        }
        actionPane.add(closeButton);

        add(actionPane, BorderLayout.EAST);
        add(new JLabel("         " + parent.getTitle()));


        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                // get location of Window
                int thisX = parent.getLocation().x;
                int thisY = parent.getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
                int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                parent.setLocation(X, Y);
            }
        });
    }

    private void iconifiedWindow() {
        parent.setExtendedState(Frame.ICONIFIED);
    }

}
