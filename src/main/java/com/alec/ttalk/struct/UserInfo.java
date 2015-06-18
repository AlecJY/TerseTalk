package com.alec.ttalk.struct;

import org.jivesoftware.smack.packet.Presence;

import javax.swing.*;
import java.net.URL;

/**
 * Created by Alec on 2015/6/5.
 */
public class UserInfo {
    public String jid;
    public String name;
    public Presence.Type status;
    public URL avatarURL;
    public ImageIcon avatar;
}
