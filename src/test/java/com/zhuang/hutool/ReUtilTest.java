package com.zhuang.hutool;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.Test;

public class ReUtilTest {

    @Test
    public void getGroup0() {
        int index = 0;
        int length = 100;
        String tpl1 = "{}.{}：{}";
        String tpl2 = "原：{}\n新：{}";
        System.out.println(StrUtil.repeat('-', length));

        String title = "提取7E或68开头的16进制字符串";
        String pattern = "(7E|68)+[0-9A-F]+";
        String content = "20241127154101:已下发指令到设备[4452812005]，平台发送报文HEX内容：680768304452812005E2CF16";
        System.out.println(StrUtil.format(tpl1, (++index), title, pattern));
        System.out.println(StrUtil.format(tpl2, content, ReUtil.getGroup0(pattern, content)));
        System.out.println(StrUtil.repeat('-', length));
    }

    @Test
    public void findAll() {
        int index = 0;
        int length = 100;
        String tpl1 = "{}.{}：{}";
        String tpl2 = "原：{}\n新：{}";
        System.out.println(StrUtil.repeat('-', length));

        String title = "提取括号及里面的内容";
        String pattern = "\\[(.*?)\\]";
        String content = "aa[4452812005]bb[1234567890]cc";
        System.out.println(StrUtil.format(tpl1, (++index), title, pattern));
        String result = StrUtil.format("group0={}、group1={}", String.join(",", ReUtil.findAll(pattern, content, 0)), String.join(",", ReUtil.findAll(pattern, content, 1)));
        System.out.println(StrUtil.format(tpl2, content, result));
        System.out.println(StrUtil.repeat('-', length));
    }

    @Test
    public void delAll() {
        int index = 0;
        int length = 100;
        String tpl1 = "{}.{}：{}";
        String tpl2 = "原：{}\n新：{}";
        System.out.println(StrUtil.repeat('-', length));

        String title = "删除中括号及里面的内容";
        String pattern = "\\[(.*?)\\]";
        String content = "aa[4452812005]bb[1234567890]cc";
        System.out.println(StrUtil.format(tpl1, (++index), title, pattern));
        System.out.println(StrUtil.format(tpl2, content, ReUtil.delAll(pattern, content)));
        System.out.println(StrUtil.repeat('-', length));
    }
}
