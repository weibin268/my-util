package com.zhuang.util.script;

import cn.hutool.script.ScriptUtil;

import javax.script.*;
import java.util.Map;

/**
 * 建议使用hutool的ScriptUtil，它做了缓存实现
 */
public class JsEngineUtils {

    public static Object eval(String script, Map<String, Object> context) {
        return eval(script, context, false);
    }

    public static Object eval(String script, Map<String, Object> context, boolean newContext) {
        try {
            ScriptEngine jsEngine = ScriptUtil.getJsEngine();
            ScriptContext scriptContext;
            if (newContext) {
                scriptContext = new SimpleScriptContext();
            } else {
                scriptContext = jsEngine.getContext();
            }
            Bindings bindings = scriptContext.getBindings(ScriptContext.ENGINE_SCOPE);
            bindings.putAll(context);
            Object result = jsEngine.eval(script, scriptContext);
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("执行js脚本出错", ex);
        }
    }
}
