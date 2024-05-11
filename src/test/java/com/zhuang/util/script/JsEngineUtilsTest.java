package com.zhuang.util.script;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JsEngineUtilsTest {

    @Test
    public void sameContext() {
        JsEngineUtilsTest t = new JsEngineUtilsTest();
        Map<String, Object> context = new HashMap<>();
        context.put("t", t);
        Object r1 = JsEngineUtils.eval("var a=1;var b=1; t.sayHello(); a+b;", context);
        System.out.println(r1);
        Object r2 = JsEngineUtils.eval("a+b;", context);
        System.out.println(r2);
    }

    @Test
    public void diffContext() {
        JsEngineUtilsTest t = new JsEngineUtilsTest();
        Map<String, Object> context = new HashMap<>();
        context.put("t", t);
        Object r1 = JsEngineUtils.eval("var a=1;var b=1; t.sayHello(); a+b;", context, true);
        System.out.println(r1);
        Object r2 = JsEngineUtils.eval("a+b;", context, true);
        System.out.println(r2);
    }

    public void sayHello() {
        System.out.println("hello!");
    }
}
