package com.alec.ttalk.main;

import com.alec.ttalk.TerseTalk;
import com.alec.ttalk.about.AboutWindow;
import com.alec.ttalk.common.FrameTitleBar;
import com.alec.ttalk.common.XMPPControl;
import com.alec.ttalk.struct.UserInfo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/6/6.
 */
public class MainWindow extends JFrame{
    private JMenuBar menuBar = new JMenuBar();
    private ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); // load lang
    private XMPPControl xmppControl = TerseTalk.xmppControl;
    private FriendListPane friendListPane = new FriendListPane();
    private JScrollPane listPane = new JScrollPane();

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

        refreshFriends();

        listPane.add(friendListPane);

        add(northPane, BorderLayout.NORTH);
        add(listPane);
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
            while (true) {
                xmppControl.getFriendList();
                for (Object key : xmppControl.friends.keySet()) {
                    friendListPane.addElement(xmppControl.friends.get(key));
                }
                friendListPane.showList();
                Thread.sleep(10000);
            }
        }
    }
}
