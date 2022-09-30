package com.zhuang.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DateUtils {


    public static void handleEachMonth(String strBeginDate, String strEndDate, Consumer<String> handler) {
        Date beginDate = DateUtil.parseDate(strBeginDate);
        Date endDate = DateUtil.parseDate(strEndDate);
        if (beginDate.compareTo(endDate) > 0) return;
        if (beginDate.compareTo(endDate) == 0) {
            handler.accept(DateUtil.format(beginDate, "yyyy-MM-01"));
            return;
        }
        List<Date> dateList = new ArrayList<>();
        long diffMonths = DateUtil.betweenMonth(beginDate, endDate, true);
        for (int i = 0; i <= diffMonths; i++) {
            dateList.add(DateUtil.offsetMonth(beginDate, i));
        }
        for (Date date : dateList) {
            handler.accept(DateUtil.format(date, "yyyy-MM-01"));
        }
    }

    public static void handleEachMonth(Date beginDate, Date endDate, Consumer<Date> handler) {
        handleEachMonth(DateUtil.formatDate(beginDate), DateUtil.formatDate(endDate), c -> {
            handler.accept(DateUtil.parseDate(c));
        });
    }

    public static List<String> getEachMonth(String strBeginDate, String strEndDate) {
        List<String> result = new ArrayList<>();
        handleEachMonth(strBeginDate, strEndDate, result::add);
        return result;
    }

    public static List<Date> getEachMonth(Date beginDate, Date endDate) {
        return getEachMonth(DateUtil.formatDate(beginDate), DateUtil.formatDate(endDate)).stream().map(DateUtil::parseDate).collect(Collectors.toList());
    }


    public static void handleEachDay(String strBeginDate, String strEndDate, Consumer<String> handler) {
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

    public static void handleEachDay(Date beginDate, Date endDate, Consumer<Date> handler) {
        handleEachDay(DateUtil.formatDate(beginDate), DateUtil.formatDate(endDate), c -> {
            handler.accept(DateUtil.parseDate(c));
        });
    }

    public static List<String> getEachDay(String strBeginDate, String strEndDate) {
        List<String> result = new ArrayList<>();
        handleEachDay(strBeginDate, strEndDate, result::add);
        return result;
    }

    public static List<Date> getEachDay(Date beginDate, Date endDate) {
        return getEachDay(DateUtil.formatDate(beginDate), DateUtil.formatDate(endDate)).stream().map(DateUtil::parseDate).collect(Collectors.toList());
    }


    public static void handleEachHour(String strBeginDateTime, String strEndDateTime, Consumer<String> handler) {
        Date beginDateTime = DateUtil.parseDateTime(strBeginDateTime);
        Date endDateTime = DateUtil.parseDateTime(strEndDateTime);
        if (beginDateTime.compareTo(endDateTime) > 0) return;
        if (beginDateTime.compareTo(endDateTime) == 0) {
            handler.accept(DateUtil.formatDateTime(beginDateTime));
            return;
        }
        List<Date> hourDateList = new ArrayList<>();
        long diffHours = DateUtil.between(beginDateTime, endDateTime, DateUnit.HOUR);
        for (int i = 0; i <= diffHours; i++) {
            hourDateList.add(DateUtil.offsetHour(beginDateTime, i));
        }
        for (Date date : hourDateList) {
            handler.accept(DateUtil.formatDateTime(date));
        }
    }

    public static void handleEachHour(Date beginDateTime, Date endDateTime, Consumer<Date> handler) {
        handleEachHour(DateUtil.formatDateTime(beginDateTime), DateUtil.formatDateTime(endDateTime), c -> {
            handler.accept(DateUtil.parseDateTime(c));
        });
    }

    public static List<String> getEachHour(String strBeginDateTime, String strEndDateTime) {
        List<String> result = new ArrayList<>();
        handleEachHour(strBeginDateTime, strEndDateTime, result::add);
        return result;
    }

    public static List<Date> getEachHour(Date beginDateTime, Date endDateTime) {
        return getEachHour(DateUtil.formatDateTime(beginDateTime), DateUtil.formatDateTime(endDateTime)).stream().map(DateUtil::parseDateTime).collect(Collectors.toList());
    }


    public static void handleEachMinute(String strBeginDateTime, String strEndDateTime, Consumer<String> handler) {
        Date beginDateTime = DateUtil.parseDateTime(strBeginDateTime);
        Date endDateTime = DateUtil.parseDateTime(strEndDateTime);
        if (beginDateTime.compareTo(endDateTime) > 0) return;
        if (beginDateTime.compareTo(endDateTime) == 0) {
            handler.accept(DateUtil.formatDateTime(beginDateTime));
            return;
        }
        List<Date> minuteDateList = new ArrayList<>();
        long diffHours = DateUtil.between(beginDateTime, endDateTime, DateUnit.MINUTE);
        for (int i = 0; i <= diffHours; i++) {
            minuteDateList.add(DateUtil.offsetMinute(beginDateTime, i));
        }
        for (Date date : minuteDateList) {
            handler.accept(DateUtil.formatDateTime(date));
        }
    }

    public static void handleEachMinute(Date beginDateTime, Date endDateTime, Consumer<Date> handler) {
        handleEachMinute(DateUtil.formatDateTime(beginDateTime), DateUtil.formatDateTime(endDateTime), c -> {
            handler.accept(DateUtil.parseDateTime(c));
        });
    }

    public static List<String> getEachMinute(String strBeginDateTime, String strEndDateTime) {
        List<String> result = new ArrayList<>();
        handleEachMinute(strBeginDateTime, strEndDateTime, result::add);
        return result;
    }

    public static List<Date> getEachMinute(Date beginDateTime, Date endDateTime) {
        return getEachMinute(DateUtil.formatDateTime(beginDateTime), DateUtil.formatDateTime(endDateTime)).stream().map(DateUtil::parseDateTime).collect(Collectors.toList());
    }

    public static List<String> parseTimesToList(String times) {
        if (StrUtil.isEmpty(times)) return Collections.EMPTY_LIST;
        List<String> timeList = Arrays.asList(times.split(","));
        List<String> result = new ArrayList<>();
        List<String> timeList4Point = timeList.stream().filter(c -> !c.contains("-")).collect(Collectors.toList());
        timeList4Point = timeList4Point.stream().map(c -> {
            try {
                DateTime tempDate = DateUtil.parse(c, "HH:mm");
                return DateUtil.format(tempDate, "HH:mm");
            } catch (Exception e) {
                return null;
            }
        }).filter(c -> StrUtil.isNotEmpty(c)).collect(Collectors.toList());
        result.addAll(timeList4Point);
        List<String> timeList4Between = timeList.stream().filter(c -> c.contains("-")).collect(Collectors.toList());
        for (String time4Between : timeList4Between) {
            String[] timeArr = time4Between.split("-");
            String beginTime = timeArr[0];
            String endTime = timeArr[1];
            Date beginDate;
            Date endDate;
            try {
                beginDate = DateUtil.parseDateTime(StrUtil.format("2020-01-01 {}:00", beginTime));
                endDate = DateUtil.parseDateTime(StrUtil.format("2020-01-01 {}:00", endTime));
            } catch (Exception e) {
                continue;
            }
            List<String> tempTimeList = DateUtils.getEachMinute(DateUtil.formatDateTime(beginDate), DateUtil.formatDateTime(endDate))
                    .stream().map(c -> DateUtil.format(DateUtil.parseDateTime(c), "HH:mm")).collect(Collectors.toList());
            result.addAll(tempTimeList);
        }
        result = result.stream().distinct().sorted().collect(Collectors.toList());
        return result;
    }

    public static Date add(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }


    public static Map<Date, List<Date>> getBetweenDateGroupByMonth(Date beginDateTime, Date endDateTime) {
        String dateMonthFormat = "yyyy-MM";
        List<Date> eachMonth = DateUtils.getEachMonth(beginDateTime, endDateTime);
        Map<Date, List<Date>> dateMap = new LinkedHashMap<>();
        for (Date date : eachMonth) {
            List<Date> tempDateList = new ArrayList<>();
            if (DateUtil.format(date, dateMonthFormat).equals(DateUtil.format(beginDateTime, dateMonthFormat))) {
                tempDateList.add(beginDateTime);
            } else {
                tempDateList.add(DateUtil.beginOfMonth(date));
            }
            if (DateUtil.format(date, dateMonthFormat).equals(DateUtil.format(endDateTime, dateMonthFormat))) {
                tempDateList.add(endDateTime);
            } else {
                tempDateList.add(DateUtil.endOfMonth(date));
            }
            dateMap.put(date, tempDateList);
        }
        return dateMap;
    }

}
