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

    public MotionPanel(final JFrame parent){
        this.parent = parent;
        setLayout(new BorderLayout());

        JPanel actionPane = new JPanel();
        ImageIcon cancelIcon = new ImageIcon(getClass().getClassLoader().getResource("pic/cancel.png"));
        ImageIcon hideIcon = new ImageIcon(getClass().getClassLoader().getResource("pic/hide.png"));
        JButton cancelButton = new JButton(cancelIcon);
        JButton hideButton = new JButton(hideIcon);
        cancelButton.setBorder(BorderFactory.createEmptyBorder());
        cancelButton.setContentAreaFilled(false);
        hideButton.setBorder(BorderFactory.createEmptyBorder());
        hideButton.setContentAreaFilled(false);

        cancelButton.addActionListener(e -> System.exit(0));
        hideButton.addActionListener(e -> hideWindow());

        actionPane.add(hideButton);
        actionPane.add(cancelButton);

        add(actionPane, BorderLayout.EAST);
        add(new JLabel("         TerseTalk"));


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

    private void hideWindow() {
        parent.setExtendedState(Frame.ICONIFIED);
    }

}
