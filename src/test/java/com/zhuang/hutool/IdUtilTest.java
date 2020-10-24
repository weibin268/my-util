package com.zhuang.hutool;

import cn.hutool.core.util.IdUtil;
import org.junit.Test;

public class IdUtilTest {

    @Test
    public void test() {
        System.out.println(IdUtil.simpleUUID());
        System.out.println(IdUtil.getSnowflake(1, 1).nextId());
    }

}
