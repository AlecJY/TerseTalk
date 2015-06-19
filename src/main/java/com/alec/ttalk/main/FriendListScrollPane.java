package com.alec.ttalk.main;

import com.alec.ttalk.chat.ChatWindow;
import com.alec.ttalk.struct.UserInfo;
import org.jivesoftware.smack.packet.Presence;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/6/12.
 */
public class FriendListScrollPane extends JPanel{
    private JPanel friendPanel;
    private JList friendList;
    private JScrollPane scrollPane;

    private ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); // load lang
    private ArrayList<Object[]> friendInfo = new ArrayList<>();

    public FriendListScrollPane() {
        friendList.setCellRenderer(new FriendListCellRender());
        setLayout(new BorderLayout());
        add(friendPanel, BorderLayout.CENTER);

        friendList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() >= 2) {
                    Point mousePoint = new Point(e.getX(), e.getY());
                    String jid = (String) friendInfo.get(friendList.locationToIndex(mousePoint))[2];
                    new ChatWindow(jid);
                }
            }
        });
    }


    public void cleanFriend() {
        friendInfo.clear();
    }

    public void addFriend(UserInfo info) {
        Object infoArray[] = new Object[3];
        if (info.name.isEmpty()) {
            info.name = info.jid;
        }

        infoArray[0] = info.avatar;
        if (info.status == Presence.Type.available) {
            infoArray[1] = "<html>" + info.name + "<p><font color=green>" + lang.getString("online") + "</font><p></html>";
        }
        else {
            infoArray[1] = "<html>" + info.name + "<p><font color=gray>" + lang.getString("offline") + "</font></p></html>";
        }
        infoArray[2] = info.jid;
        friendInfo.add(infoArray);
    }

    public void showList() {
        if (!friendInfo.isEmpty()) {
            friendList.setListData(friendInfo.toArray());
            friendList.setListData(friendInfo.toArray());
        }
    }
}

class FriendListCellRender implements ListCellRenderer {
    protected DefaultListCellRenderer defaultListCellRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel renderer = (JLabel) defaultListCellRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        Object values[] = (Object[]) value;
        ImageIcon imageIcon = (ImageIcon) values[0];
        String text = (String) values[1];

        Color foreground = list.getForeground();

        if (!isSelected) {
            renderer.setForeground(foreground);
        }

        renderer.setIcon(imageIcon);
        renderer.setText(text);

        return renderer;
    }
}
