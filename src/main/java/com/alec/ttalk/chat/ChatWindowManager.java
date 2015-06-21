package com.alec.ttalk.chat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alec on 2015/6/19.
 */
public class ChatWindowManager {
    private HashMap<String, ChatWindow> chatFrame = new HashMap();

    public void createWindow(String jid) {
        if (chatFrame.get(jid) == null) {
            chatFrame.put(jid, new ChatWindow(jid));
        } else {
            chatFrame.get(jid).setVisible(true);
        }
    }

    public ChatWindow getChatWindow(String jid) {
        return chatFrame.get(jid);
    }
}
