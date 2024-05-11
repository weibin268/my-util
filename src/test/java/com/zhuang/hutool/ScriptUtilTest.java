package com.zhuang.hutool;

import cn.hutool.script.ScriptUtil;
import org.junit.Test;

import javax.script.ScriptContext;
import javax.script.SimpleScriptContext;

public class ScriptUtilTest {

    @Test
    public void sameContext() {
        ScriptContext scriptContext = new SimpleScriptContext();
        Object r1 = ScriptUtil.eval("a=1;b=2;a+b;", scriptContext);
        System.out.println(r1);
        Object r2 = ScriptUtil.eval("b-a", scriptContext);
        System.out.println(r2);
    }

    @Test
    public void diffContext() {
        ScriptContext scriptContext = new SimpleScriptContext();
        Object r1 = ScriptUtil.eval("a=1;b=2;a+b;", scriptContext);
        System.out.println(r1);
        scriptContext = new SimpleScriptContext();
        Object r2 = ScriptUtil.eval("b-a", scriptContext);
        System.out.println(r2);
    }

}
