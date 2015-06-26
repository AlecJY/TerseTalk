package com.alec.ttalk.common;

import com.alec.ttalk.TerseTalk;
import com.alec.ttalk.struct.UserInfo;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.java7.Java7SmackInitializer;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jxmpp.util.XmppStringUtils;

import javax.net.ssl.SSLHandshakeException;
import javax.swing.*;
import java.security.cert.CertificateException;
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
    private boolean lostConnection = false;
    private boolean isAutoTalkStarted = false;
    private String scriptPath;

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
            if (e.getCause() instanceof SSLHandshakeException || e.getCause() instanceof CertificateException) { // if ssh certified failed
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

    public void startListener() {
        ChatManager.getInstanceFor(connection).addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                chat.addMessageListener(new ChatMessageListener() {
                    @Override
                    public void processMessage(Chat chat, Message message) {
                        TerseTalk.chatWindowManager.createWindow(chat.getParticipant());
                        if (message.getBody() == null) {
                            TerseTalk.chatWindowManager.getChatWindow(chat.getParticipant()).setTyping();
                        } else {
                            TerseTalk.chatWindowManager.getChatWindow(chat.getParticipant()).addMessage(message.getBody());
                        }
                    }
                });
            }
        });
    }

    public void sendMessage(String jid, String message) {
        Chat chat = ChatManager.getInstanceFor(connection).createChat(jid);
        try {
            chat.sendMessage(message);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public UserInfo getUserInfo(String jid) {
        UserInfo info = new UserInfo();
        Presence presence = Roster.getInstanceFor(connection).getPresence(jid);
        info.status = presence.getType();
        info.jid = jid;
        try {
            Roster roster = Roster.getInstanceFor(connection);
            RosterEntry entry = roster.getEntry(jid);
            info.name = entry.getName();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        try {
            VCard vCard = VCardManager.getInstanceFor(connection).loadVCard(jid);
            info.avatarLocation = vCard.getAvatar();
            info.avatar = new ImageIcon(vCard.getAvatar());
        } catch (XMPPException.XMPPErrorException e) {
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return info;
    }

    public UserInfo getLightUserInfo(String jid) {
        UserInfo info = new UserInfo();
        Presence presence = Roster.getInstanceFor(connection).getPresence(jid);
        info.status = presence.getType();
        info.jid = jid;
        try {
            Roster roster = Roster.getInstanceFor(connection);
            RosterEntry entry = roster.getEntry(jid);
            info.name = entry.getName();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return info;
    }

    public void startConnectionListener() {
        ReconnectionManager.getInstanceFor(connection).enableAutomaticReconnection();
        connection.addConnectionListener(new ConnectionListener() {
            @Override
            public void connected(XMPPConnection connection) {

            }

            @Override
            public void authenticated(XMPPConnection connection, boolean resumed) {

            }

            @Override
            public void connectionClosed() {

            }

            @Override
            public void connectionClosedOnError(Exception e) {
                if (!lostConnection) {
                    new ConnectionMessage(0);
                    lostConnection = true;
                }
            }

            @Override
            public void reconnectionSuccessful() {
                new ConnectionMessage(1);
            }

            @Override
            public void reconnectingIn(int seconds) {
            }

            @Override
            public void reconnectionFailed(Exception e) {

            }
        });
    }

    public int getFriendNum() {
        return friendNum;
    }

    public int getFriendLoadProgress() {
        return friendLoadProgress;
    }

    public String getJabberID() {
        return connection.getUser();
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

    public boolean isAutoTalkStarted() {
        return isAutoTalkStarted;
    }

    public void setAutoTalk(boolean isAutoTalkStarted) {
        this.isAutoTalkStarted = isAutoTalkStarted;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String path) {
        scriptPath = path;
    }
}
