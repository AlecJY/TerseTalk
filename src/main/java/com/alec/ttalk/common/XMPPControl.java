package com.alec.ttalk.common;

import com.alec.ttalk.struct.UserInfo;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.java7.Java7SmackInitializer;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import javax.net.ssl.SSLHandshakeException;
import javax.swing.*;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Alec on 2015/5/24.
 */
public class XMPPControl {
    public HashMap<String, UserInfo> friends = new HashMap();

    private boolean sslCert = true;
    private int friendNum = 0;
    private int friendLoadProgress = 0;

    private XMPPTCPConnection connection;
    private XMPPTCPConnectionConfiguration config;

    public void init(String server, String username, String password) {
        config = XMPPTCPConnectionConfiguration.builder()
                .setServiceName(server)
                .setUsernameAndPassword(username, password)
                .build();
        connection = new XMPPTCPConnection(config);
        new Java7SmackInitializer().initialize();
    }

    public boolean connect() {
        try {
            connection.connect();
            sslCert = true;
        } catch (SmackException e) {
            e.printStackTrace();
            if (e.getCause() instanceof SSLHandshakeException) { // if ssh certified failed
                sslCert = false;
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return isConnected();
    }

    public boolean login() {
        try {
            connection.login();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return isAuthenticated();
    }

    public void disableSSLCert() {
        XMPPTCPConnectionConfiguration noCertConfig = XMPPTCPConnectionConfiguration.builder()
                .setServiceName(config.getServiceName())
                .setUsernameAndPassword(config.getUsername(), config.getPassword())
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .build();
        config = noCertConfig;
        connection = new XMPPTCPConnection(config);
    }

    public void getFriendList() {
        Roster roster = Roster.getInstanceFor(connection);
        try {
            if (!roster.isLoaded()) {
                roster.reloadAndWait();
            }

            Collection<RosterEntry> entries = roster.getEntries();
            friendNum = entries.size();
            friendLoadProgress = 0;
            for (RosterEntry entry: entries) {
                try {
                    friendLoadProgress++;
                    Presence presence = roster.getPresence(entry.getUser());
                    if (friends.get(entry.getUser()) == null) {
                        friends.put(entry.getUser(), new UserInfo());
                    }
                    friends.get(entry.getUser()).jid = entry.getUser();
                    friends.get(entry.getUser()).name = entry.getName();
                    friends.get(entry.getUser()).status = presence.getType();
                } catch (Throwable t) {
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void getAvatar(String jid) {
        try {
            if (friends.get(jid).avatarLocation == null) {
                VCard vCard = VCardManager.getInstanceFor(connection).loadVCard(jid);
                friends.get(jid).avatarLocation = vCard.getAvatar();
                friends.get(jid).avatar = new ImageIcon(vCard.getAvatar());
            }
        } catch (XMPPException.XMPPErrorException e) {
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public int getFriendNum() {
        return friendNum;
    }

    public int getFriendLoadProgress() {
        return friendLoadProgress;
    }

    public boolean isSSLCert() {
        return sslCert;
    }

    public boolean isConnected() {
        return connection.isConnected();
    }

    public boolean isAuthenticated() {
        return connection.isAuthenticated();
    }
}
