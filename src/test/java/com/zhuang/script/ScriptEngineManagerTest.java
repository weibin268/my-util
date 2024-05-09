package com.zhuang.script;

import cn.hutool.core.io.FileUtil;
import org.junit.Test;

import javax.naming.NamingException;
import javax.script.*;
import java.util.HashMap;
import java.util.Map;

public class ScriptEngineManagerTest {

    @Test
    public void test() throws NamingException, ScriptException {
        String loginJs = FileUtil.readString("classpath:script/login.js", "UTF-8");
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine jsEngine = manager.getEngineByName("js");
        ScriptContext scriptContext = jsEngine.getContext();
        Bindings bindings = scriptContext.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.put("username", "admin");
        bindings.put("password", "123");
        bindings.put("loginBean", new LoginBean());
        Object result =  jsEngine.eval(loginJs, scriptContext);
        System.out.println("result -> " + result);
    }

    public static class LoginBean {
        public Map login(String username, String password) {
            System.out.println("login...");
            Map<String, Object> map = new HashMap<>();
            map.put("username", username);
            map.put("password", password);
            return map;
        }
    }
}
