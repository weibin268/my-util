package com.zhuang.hutool;

import cn.hutool.core.date.DateUtil;
import cn.hutool.cron.pattern.CronPattern;
import cn.hutool.cron.pattern.CronPatternUtil;
import org.junit.Test;

import java.util.Date;

public class CronPatternUtilTest {

    @Test
    public void test() {
        Date date = CronPatternUtil.nextDateAfter(new CronPattern("0 0 * * * ? *"), DateUtil.parseDateTime("2022-07-26 22:00:11"), false);
        System.out.println(DateUtil.formatDateTime(date));
    }

}
