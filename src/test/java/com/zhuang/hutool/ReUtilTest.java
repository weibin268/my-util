package com.zhuang.hutool;

import cn.hutool.core.util.ReUtil;
import org.junit.Test;

public class ReUtilTest {

    @Test
    public void getGroup0() {
        // 提取7E或68开头的16进制字符串
        System.out.println(ReUtil.getGroup0("(7E|68)+[0-9A-F]+", "20241127154101:已下发指令到设备[4452812005]，平台发送报文HEX内容：680768304452812005E2CF16"));
    }

    @Test
    public void delAll() {
        // 删除中括号及里面的内容
        System.out.println(ReUtil.delAll("\\[(.*?)\\]", "aa[4452812005]bb[1234567890]cc"));
    }
}
