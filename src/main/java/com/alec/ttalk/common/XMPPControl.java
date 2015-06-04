package com.alec.ttalk.common;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.java7.Java7SmackInitializer;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import javax.net.ssl.SSLHandshakeException;

/**
 * Created by Alec on 2015/5/24.
 */
public class XMPPControl {
    private boolean sslCert = true;

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
        } catch (SmackException e) {
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
