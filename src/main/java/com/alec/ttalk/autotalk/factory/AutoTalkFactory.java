package com.alec.ttalk.autotalk.factory;

import com.alec.ttalk.autotalk.interfaces.AutoTalkType;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.File;

/**
 * Created by Alec on 2015/6/25.
 */
public class AutoTalkFactory {
    private PyObject autoTalkClass;

    public AutoTalkFactory(String path) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile(path);
        interpreter.exec("from com.alec.ttalk.autotalk.interfaces import AutoTalkType");
        autoTalkClass = interpreter.get("AutoTalk");
    }

    public AutoTalkType create () {
        PyObject autoTalkObject = autoTalkClass.__call__();
        return (AutoTalkType)autoTalkObject.__tojava__(AutoTalkType.class);
    }
}
