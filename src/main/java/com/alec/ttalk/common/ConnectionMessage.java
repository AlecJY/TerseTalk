package com.alec.ttalk.common;

import com.alec.ttalk.TerseTalk;

import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/6/24.
 */
public class ConnectionMessage {
    private ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); //  load lang

    public ConnectionMessage(int mode) {
        if (mode == 0) {
            new MessageBox(lang.getString("LoginStatus.errorTitle"), lang.getString("ConnectionMessage.connectionFailed"), 0, 0, 3);
            System.out.println(TerseTalk.xmppControl.getJabberID());
        } else {
            new MessageBox(lang.getString("ConnectionMessage.messageTitle"), lang.getString("ConnectionMessage.reconnect"), 0, 0, 3);
        }
    }
}
