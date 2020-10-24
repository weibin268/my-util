package com.zhuang.hutool;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import org.junit.Test;

public class ReflectUtilTest {

    @Test
    public void test() {
        DateTime date = DateUtil.date();
        Object getTime = ReflectUtil.invoke(date, "getField", DateField.DAY_OF_MONTH);
        System.out.println(getTime);
    }

}
