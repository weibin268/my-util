package com.zhuang.util.script;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JsEngineUtilsTest {

    @Test
    public void test() {
        JsEngineUtilsTest t = new JsEngineUtilsTest();
        Map<String, Object> context = new HashMap<>();
        context.put("t", t);
        Object eval = JsEngineUtils.eval("var a=1;var b=1; t.sayHello(); a+b;", context);
        System.out.println(eval);
    }

    public void sayHello() {
        System.out.println("hello!");
    }
}
