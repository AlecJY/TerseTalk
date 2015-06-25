package com.alec.ttalk.autotalk;

import com.alec.ttalk.autotalk.factory.AutoTalkFactory;
import com.alec.ttalk.autotalk.interfaces.AutoTalkType;

/**
 * Created by Alec on 2015/6/25.
 */
public class AutoTalk {

    AutoTalkFactory autoTalkFactory;
    AutoTalkType type;

    String mode = "";
    String info = "";

    public AutoTalk(String path) {
        autoTalkFactory = new AutoTalkFactory(path);
        type = autoTalkFactory.create();
    }

    public String autoTalkMessage(String jid, String name, String message) {
        String getMessage = type.sendMessage(jid, name, message, mode, info);
        mode = type.getMode();
        info = type.getInfo();
        return getMessage;
    }
}
