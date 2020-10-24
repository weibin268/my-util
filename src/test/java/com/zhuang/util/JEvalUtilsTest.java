package com.zhuang.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JEvalUtilsTest {

    @Test
    public void eval() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("num1", "0.1");
        variables.put("num2", "100");
        System.out.println(JEvalUtils.eval("#{num1}*#{num2}", variables));
    }
}
