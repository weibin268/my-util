package com.zhuang.util.script;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Map;

public class JsEngineUtils {

    public static Object eval(String script, Map<String, Object> context) {
        try {
            ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("js");
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
