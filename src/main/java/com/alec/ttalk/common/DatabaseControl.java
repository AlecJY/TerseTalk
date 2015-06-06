package com.alec.ttalk.common;


import org.h2.tools.SimpleResultSet;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Alec on 2015/6/5.
 */
public class DatabaseControl {
    private Connection connection;

    public void connect() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/tTalkConfig", System.getProperty("user.name"), "");
            System.out.println(System.getProperty("user.dir"));
            SimpleResultSet rs = new SimpleResultSet();
        } catch (Throwable t) {
        }
    }
}
