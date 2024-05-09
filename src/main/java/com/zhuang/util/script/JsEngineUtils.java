package com.zhuang.util.script;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Map;

public class JsEngineUtils {

    private static final ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("js");

    public static Object eval(String script, Map<String, Object> context) {
        try {
            ScriptContext scriptContext = jsEngine.getContext();
            Bindings bindings = scriptContext.getBindings(ScriptContext.ENGINE_SCOPE);
            bindings.putAll(context);
            Object result = jsEngine.eval(script, scriptContext);
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("执行js脚本出错", ex);
        }
    }
}
