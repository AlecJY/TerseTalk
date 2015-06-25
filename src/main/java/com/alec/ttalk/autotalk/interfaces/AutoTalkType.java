package com.alec.ttalk.autotalk.interfaces;

/**
 * Created by Alec on 2015/6/25.
 */
public interface AutoTalkType {
    public String sendMessage(String jid, String name, String message, String mod, String inf);
    public String getMode();
    public String getInfo();
}
