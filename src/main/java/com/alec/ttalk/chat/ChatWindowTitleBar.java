package com.alec.ttalk.chat;

import apple.laf.AquaLookAndFeel;
import com.alec.ttalk.TerseTalk;
import com.alec.ttalk.struct.UserInfo;
import org.jivesoftware.smack.packet.Presence;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ResourceBundle;

// get from http://stackoverflow.com/questions/10773713/moving-undecorated-window-by-clicking-on-jpanel

public class ChatWindowTitleBar extends JPanel{
    private Point initialClick;
    private JFrame parent;

    private ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); // load lang
    private int mode = 2;

    public ChatWindowTitleBar(final JFrame parent, String jid){
        this.parent = parent;
        setLayout(new BorderLayout());
        parent.setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("image/tTalk.png"))); // set application's icon

        JPanel infoPane = new JPanel();
        UserInfo userInfo = TerseTalk.xmppControl.getUserInfo(jid);
        if (userInfo.name == null) {
            userInfo.name = userInfo.jid;
        }
        parent.setTitle(userInfo.name + " - TerseTalk");
        JLabel userInfoLabel = new JLabel();
        if (userInfo.status == Presence.Type.available) {
            userInfoLabel.setText("<html>" + userInfo.name + "<p><font color=green>" + lang.getString("online") + "</font><p></html>");
        } else {
            userInfoLabel.setText("<html>" + userInfo.name + "<p><font color=gray>" + lang.getString("offline") + "</font></p></html>");
        }
        userInfoLabel.setIcon(userInfo.avatar);
        infoPane.add(userInfoLabel);

        add(infoPane, BorderLayout.WEST); // add user info


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
    } // make window iconified

}