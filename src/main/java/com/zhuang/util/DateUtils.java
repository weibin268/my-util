package com.zhuang.util;

import cn.hutool.core.date.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class DateUtils {

    /**
     *
     * @param strBeginDate 格式：yyyy-MM-dd
     * @param strEndDate 格式：yyyy-MM-dd
     * @param handler
     */
    public static void handleEachDate(String strBeginDate, String strEndDate, Consumer<String> handler) {
        Date beginDate = DateUtil.parseDate(strBeginDate);
        Date endDate = DateUtil.parseDate(strEndDate);
        if (beginDate.compareTo(endDate) > 0) return;
        if (beginDate.compareTo(endDate) == 0) {
            handler.accept(DateUtil.formatDate(beginDate));
            return;
        }
        List<Date> dateList = new ArrayList<>();
        long diffDays = DateUtil.betweenDay(beginDate, endDate, true);
        for (int i = 0; i <= diffDays; i++) {
            dateList.add(DateUtil.offsetDay(beginDate, i));
        }
        for (Date date : dateList) {
            handler.accept(DateUtil.formatDate(date));
        }
    }

}
