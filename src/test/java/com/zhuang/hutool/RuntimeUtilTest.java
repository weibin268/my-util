package com.zhuang.hutool;

import cn.hutool.core.util.RuntimeUtil;
import org.junit.Test;

public class RuntimeUtilTest {

    @Test
    public void test() {
        System.out.println(RuntimeUtil.execForStr("cmd /c dir"));
    }
}
