package com.zhuang.hutool;

import cn.hutool.core.util.ReUtil;
import org.junit.Test;

public class ReUtilTest {

    @Test
    public void get(){
        System.out.println(ReUtil.get("(7E|68)+[0-9A-F]+", "20241127154101:已下发指令到设备[4452812005]，平台发送报文HEX内容：680768304452812005E2CF16", 0));
    }
}
