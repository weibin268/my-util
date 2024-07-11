package com.zhuang.hutool;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;

public class StrUtilTest {

    @Test
    public void test() {
        System.out.println(StrUtil.padPre("225", 3, '0'));
    }
}
