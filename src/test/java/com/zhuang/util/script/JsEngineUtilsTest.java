package com.zhuang.util.script;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JsEngineUtilsTest {

    @Test
    public void test() {
        Map<String, Object> context = new HashMap<>();
        Object eval = JsEngineUtils.eval("var a=1;var b=1; a+b;", context);
        System.out.println(eval);
    }
}
