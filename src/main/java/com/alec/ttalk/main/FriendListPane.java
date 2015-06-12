package com.alec.ttalk.main;

import com.alec.ttalk.struct.UserInfo;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Alec on 2015/6/10.
 */
public class FriendListPane extends JPanel{
    private ArrayList<UserInfo> friendInfo = new ArrayList<>();
    private ArrayList<String> showInfo = new ArrayList<>();
    private DefaultListModel<String> model = new DefaultListModel<>();

    private JList<String> friendList = new JList<>(model);

    public FriendListPane() {
        friendList.setSelectedIndex(-1);

        add(friendList);
    }

    public void addElement(UserInfo info) {
        if (info.name.isEmpty()) {
            info.name = info.jid;
        }
        showInfo.add(
                "<html>" +
                        "<table>" +
                            "<tr>" +
                                "<td><img src=" + info.avatarURL + "></img></td>" +
                            "</tr>"+
                            "<tr>"+
                                "<td><center><strong>" +info.name + "</strong></center></td>" +
                            "</tr>"+
                        "</table>" +
                "</html>"
        );
    }

    public void showList() {
        model.clear();
        for (int i = 0; i < showInfo.size(); i++) {
            //model.addElement(showInfo.get(i));
        }
        this.revalidate();
    }

}
