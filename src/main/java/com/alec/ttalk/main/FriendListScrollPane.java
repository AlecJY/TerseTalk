package com.alec.ttalk.main;

import com.alec.ttalk.struct.UserInfo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Alec on 2015/6/12.
 */
public class FriendListScrollPane extends JPanel{
    private JPanel friendPanel;
    private JList friendList;
    private JScrollPane scrollPane;

    private ArrayList<Object[]> friendInfo = new ArrayList<>();

    public FriendListScrollPane() {
        friendList.setCellRenderer(new FriendListCellRender());
        setLayout(new BorderLayout());
        add(friendPanel, BorderLayout.CENTER);
    }

    public void cleanFriend() {
        friendInfo.clear();
    }

    public void addFriend(UserInfo info) {
        if (info.name.isEmpty()) {
            info.name = info.jid;
        }

        Object infoArray[] = {new ImageIcon(getClass().getClassLoader().getResource("image/avatar_default.png")), info.name};
        friendInfo.add(infoArray);
    }

    public void showList() {
        if (!friendInfo.isEmpty()) {
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
