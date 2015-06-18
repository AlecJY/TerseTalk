//decrypt

package com.alec.ttalk.main;

import com.alec.ttalk.TerseTalk;
import com.alec.ttalk.common.XMPPControl;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/6/5.
 */
public class FriendsListStatus {
    private ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); //  load lang
    private JFrame parent;
    private JDialog dialog = new JDialog();
    private JLabel status = new JLabel(lang.getString("FriendsListStatus.getFriendListStatus"));
    private JProgressBar progressBar = new JProgressBar();
    private int x , y;

    private XMPPControl xmppControl = TerseTalk.xmppControl;

    public FriendsListStatus(int x, int y) {
        this.x = x;
        this.y = y;
        this.parent = parent;

        JPanel panel = new JPanel();
        dialog.setUndecorated(true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        dialog.setModal(true);

        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setIndeterminate(true); // always running

        panel.setLayout(new GridLayout(2, 1));
        panel.add(status);
        panel.add(progressBar);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(97, 101, 104)), BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        dialog.add(panel);
        dialog.pack();
        dialog.setLocation(x - dialog.getWidth() / 2, y - dialog.getHeight() / 2);

        (new UpdateLoadProgress()).execute();

        dialog.setVisible(true);
    }

    private class UpdateLoadProgress extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() throws Exception {
            double loadProgress;
            LoadFriendList loadFriendList = new LoadFriendList();
            loadFriendList.execute();
            while (!loadFriendList.isDone()) {
                Thread.sleep(500);
                if (xmppControl.getFriendNum() == 0) {
                    progressBar.setValue(0);
                    progressBar.setIndeterminate(false);
                } else {
                    loadProgress = (double)xmppControl.getFriendLoadProgress() / (double)xmppControl.getFriendNum();
                    progressBar.setValue((int)(loadProgress * 100));
                    progressBar.setIndeterminate(false);
                }
            }
            return null;
        }

        protected void done() {
            progressBar.setIndeterminate(true);
        }
    }

    private class LoadFriendList extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() throws Exception {
            xmppControl.getFriendList();
            return null;
        }
    }
}
