package com.zhuang.hutool;

import cn.hutool.extra.mail.MailUtil;
import org.junit.Test;

public class MailUtilTest {

    @Test
    public void test(){
        MailUtil.send("binweizhuang@hotmail.com", "测试", "邮件来自Hutool测试", false);
    }

}
