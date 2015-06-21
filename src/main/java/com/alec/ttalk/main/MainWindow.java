package com.alec.ttalk.main;

import com.alec.ttalk.TerseTalk;
import com.alec.ttalk.about.AboutWindow;
import com.alec.ttalk.common.FrameTitleBar;
import com.alec.ttalk.common.XMPPControl;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/6/6.
 */
public class MainWindow extends JFrame {
    private JMenuBar menuBar = new JMenuBar();
    private ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); // load lang
    private XMPPControl xmppControl = TerseTalk.xmppControl;
    private FriendListScrollPane friendListScrollPane = new FriendListScrollPane();

    public MainWindow() {
        setTitle("TerseTalk");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());

        menu();

        JPanel northPane = new JPanel(new GridLayout(2, 1));
        northPane.add(new FrameTitleBar(this, 2));
        northPane.add(menuBar);

        xmppControl.startListener();
        refreshFriends();

        add(northPane, BorderLayout.NORTH);
        add(friendListScrollPane);
        pack();
        setVisible(true);
    }

    private void menu() {
        JMenu file = new JMenu(lang.getString("menu.file"));
        JMenuItem preference = new JMenuItem(lang.getString("menu.file.preference"));
        JMenuItem exit = new JMenuItem(lang.getString("menu.file.exit"));
        file.add(preference);
        file.add(exit);


        exit.addActionListener(e -> System.exit(0));

        JMenu help = new JMenu(lang.getString("menu.help"));
        JMenuItem about = new JMenuItem(lang.getString("menu.help.about"));
        help.add(about);

        about.addActionListener(e -> new AboutWindow());

        menuBar.add(file);
        menuBar.add(help);
    }

    private void refreshFriends() {
        (new RefreshFriendList()).execute();
    }

    private class RefreshFriendList extends SwingWorker {
        @Override
        protected Object doInBackground() throws Exception {
            boolean isAvatarSwingWorkerStart = false;
            while (true) {
                xmppControl.getFriendList();
                friendListScrollPane.cleanFriend();
                for (Object key : xmppControl.friends.keySet()) {
                    friendListScrollPane.addFriend(xmppControl.friends.get(key));
                }
                friendListScrollPane.showList();
                if (!isAvatarSwingWorkerStart) {
                    (new RefreshAvatars()).execute();
                }
                Thread.sleep(10000);
            }
        }
    }

    private class RefreshAvatars extends SwingWorker {
        @Override
        protected Object doInBackground() throws Exception {
            while (true) {
                for (Object key : xmppControl.friends.keySet()) {
                    xmppControl.getAvatar(key.toString());
                    //TODO fix refresh issue
                    /*if (loadCount % 10 == 0 || loadCount == xmppControl.getFriendNum()) {
                        friendListScrollPane.cleanFriend();
                        for (Object loadKey : xmppControl.friends.keySet()) {
                            friendListScrollPane.addFriend(xmppControl.friends.get(loadKey));
                        }
                        friendListScrollPane.showList();
                    }*/
                }
                Thread.sleep(20000);
            }
        }
    }
}
