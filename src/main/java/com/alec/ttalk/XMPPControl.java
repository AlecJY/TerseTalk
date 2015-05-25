package com.alec.ttalk;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.java7.Java7SmackInitializer;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/**
 * Created by Alec on 2015/5/24.
 */
public class XMPPControl {
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

    public boolean isConnected() {
        return connection.isConnected();
    }

    public boolean isAuthenticated() {
        return connection.isAuthenticated();
    }
}
