// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

public class ScriptEngineSingleton
{
    private static volatile ScriptEngine instance;
    
    public static ScriptEngine getInstance() {
        if (ScriptEngineSingleton.instance == null) {
            synchronized (ScriptEngineSingleton.class) {
                if (ScriptEngineSingleton.instance == null) {
                    final ScriptEngineManager mgr = new ScriptEngineManager();
                    ScriptEngineSingleton.instance = mgr.getEngineByName("nashorn");
                }
            }
        }
        return ScriptEngineSingleton.instance;
    }
    
    static {
        ScriptEngineSingleton.instance = null;
    }
}
