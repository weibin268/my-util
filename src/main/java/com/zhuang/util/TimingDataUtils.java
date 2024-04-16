package com.zhuang.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class TimingDataUtils {

    public static <T extends TimingData> List<T> fillMissingData(List<T> dataList) {
        Integer intervalSeconds = getIntervalSeconds(dataList);
        if (intervalSeconds == null) return dataList;
        return fillMissingData(dataList, intervalSeconds);
    }

    public static <T extends TimingData> List<T> fillMissingData(List<T> dataList, int intervalSeconds) {
        return fillMissingData(dataList, intervalSeconds, null, null, null);
    }

    public static <T extends TimingData> List<T> fillMissingData(List<T> dataList, Date beginDate, Date endDate, Class<T> modelClass) {
        return fillMissingData(dataList, beginDate, endDate, modelClass, 0);
    }

    public static <T extends TimingData> List<T> fillMissingData(List<T> dataList, Date beginDate, Date endDate, Class<T> modelClass, int delayIntervalCount) {
        Integer intervalSeconds = getIntervalSeconds(dataList);
        if (intervalSeconds == null) return dataList;
        return fillMissingData(dataList, intervalSeconds, beginDate, endDate, modelClass, delayIntervalCount);
    }

    public static <T extends TimingData> List<T> fillMissingData(List<T> dataList, int intervalSeconds, Date beginDate, Date endDate, Class<T> modelClass) {
        return fillMissingData(dataList, intervalSeconds, beginDate, endDate, modelClass, 0);
    }

    public static <T extends TimingData> List<T> fillMissingData(List<T> dataList, int intervalSeconds, Date beginDate, Date endDate, Class<T> modelClass, int delayIntervalCount) {
        Date now = new Date();
        if (delayIntervalCount > 0) {
            int delaySeconds = delayIntervalCount * intervalSeconds;
            Date delayDate = DateUtil.offsetSecond(now, -delaySeconds);
            if (beginDate.compareTo(delayDate) > 0) {
                beginDate = delayDate;
            }
            if (endDate.compareTo(delayDate) > 0) {
                endDate = delayDate;
            }
        }
        if (beginDate != null && endDate != null && modelClass != null) {
            if (intervalSeconds >= 60) {
                beginDate = roundBeginDateByIntervalMinutes(beginDate, intervalSeconds / 60);
                endDate = roundEndDateByIntervalMinutes(endDate, intervalSeconds / 60);
            } else {
                beginDate = roundBeginDateByIntervalSeconds(beginDate, intervalSeconds);
                endDate = roundEndDateByIntervalSeconds(endDate, intervalSeconds);
            }
            if (beginDate.compareTo(endDate) > 0) return dataList;
            List<T> tempDataList = new ArrayList<>();
            try {
                if (CollectionUtils.isEmpty(dataList)) {
                    T beginItem = modelClass.newInstance();
                    tempDataList.add(beginItem);
                    beginItem.setDataTime(beginDate);

                    T endItem = modelClass.newInstance();
                    tempDataList.add(endItem);
                    endItem.setDataTime(endDate);
                } else {
                    if (beginDate.compareTo(dataList.get(0).getDataTime()) < 0) {
                        T beginItem = modelClass.newInstance();
                        tempDataList.add(beginItem);
                        beginItem.setDataTime(beginDate);
                    }
                    tempDataList.addAll(dataList);
                    if (endDate.compareTo(dataList.get(dataList.size() - 1).getDataTime()) > 0) {
                        T endItem = modelClass.newInstance();
                        tempDataList.add(endItem);
                        endItem.setDataTime(endDate);
                    }
                }
                dataList = tempDataList;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (intervalSeconds <= 0) return dataList;
        List<T> missingDataList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            T currentItem = dataList.get(i);
            T nextItem = null;
            if ((i + 1) < dataList.size()) {
                nextItem = dataList.get(i + 1);
            }
            if (nextItem == null) break;
            if (currentItem.getDataTime().compareTo(nextItem.getDataTime()) >= 0) continue;
            int diffSeconds = (int) DateUtil.between(currentItem.getDataTime(), nextItem.getDataTime(), DateUnit.SECOND);
            if (diffSeconds <= intervalSeconds) continue;
            Date tempMissingDataTime = DateUtil.offsetSecond(currentItem.getDataTime(), intervalSeconds);
            while (tempMissingDataTime.compareTo(nextItem.getDataTime()) < 0) {
                try {
                    T procedureData = (T) currentItem.getClass().newInstance();
                    missingDataList.add(procedureData);
                    procedureData.setDataTime(tempMissingDataTime);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                tempMissingDataTime = DateUtil.offsetSecond(tempMissingDataTime, intervalSeconds);
            }
        }
        if (CollectionUtils.isEmpty(missingDataList)) return dataList;
        dataList.addAll(missingDataList);
        dataList = dataList.stream().sorted(Comparator.comparing(TimingData::getDataTime)).collect(Collectors.toList());
        return dataList;

    }

    public static <T extends TimingData> Integer getIntervalSeconds(List<T> dataList) {
        int maxSameTimes = 2;
        if (dataList.size() < 3) return null;
        int sameTimes = 0;
        int intervalSeconds = 0;
        for (int i = 0; i < dataList.size(); i++) {
            int nextIndex = i + 1;
            if (nextIndex >= dataList.size()) break;
            TimingData currentItem = dataList.get(i);
            TimingData nextItem = dataList.get(nextIndex);
            int tempIntervalSeconds = (int) DateUtil.between(currentItem.getDataTime(), nextItem.getDataTime(), DateUnit.SECOND);
            if (tempIntervalSeconds != intervalSeconds) {
                intervalSeconds = tempIntervalSeconds;
                sameTimes = 0;
            } else {
                sameTimes++;
            }
            if (sameTimes >= maxSameTimes) {
                break;
            }
        }
        return intervalSeconds;
    }

    public interface TimingData {

        Date getDataTime();

        void setDataTime(Date dataTime);
    }

    public static Date roundBeginDateByIntervalMinutes(Date date, int intervalMinutes) {
        return roundDateByIntervalMinutes(date, intervalMinutes, -1);
    }

    public static Date roundEndDateByIntervalMinutes(Date date, int intervalMinutes) {
        return roundDateByIntervalMinutes(date, intervalMinutes, 1);
    }

    public static Date roundDateByIntervalMinutes(Date date, int intervalMinutes, int direction) {
        List<String> timeList = getTimeList(intervalMinutes);
        String originTime = DateUtil.format(date, TIME_FORMAT);
        String newTime;
        if (direction < 0) {
            newTime = timeList.stream().filter(c -> c.compareTo(originTime) >= 0).sorted().findFirst().orElse(null);
        } else if (direction > 0) {
            newTime = timeList.stream().filter(c -> c.compareTo(originTime) <= 0).sorted(Comparator.reverseOrder()).findFirst().orElse(null);
        } else {
            return date;
        }
        if (StrUtil.isEmpty(newTime)) return date;
        return DateUtil.parse(DateUtil.format(date, "yyyy-MM-dd ") + newTime + ":00");
    }

    public static Date roundBeginDateByIntervalSeconds(Date date, int intervalSeconds) {
        return roundDateByIntervalSeconds(date, intervalSeconds, -1);
    }

    public static Date roundEndDateByIntervalSeconds(Date date, int intervalSeconds) {
        return roundDateByIntervalSeconds(date, intervalSeconds, 1);
    }

    public static Date roundDateByIntervalSeconds(Date date, int intervalSeconds, int direction) {
        List<String> timeList = getTimeList4IntervalSeconds(intervalSeconds);
        String originTime = DateUtil.format(date, TIME_FORMAT_SECONDS);
        String newTime;
        if (direction < 0) {
            newTime = timeList.stream().filter(c -> c.compareTo(originTime) >= 0).sorted().findFirst().orElse(null);
        } else if (direction > 0) {
            newTime = timeList.stream().filter(c -> c.compareTo(originTime) <= 0).sorted(Comparator.reverseOrder()).findFirst().orElse(null);
        } else {
            return date;
        }
        if (StrUtil.isEmpty(newTime)) return date;
        return DateUtil.parse(DateUtil.format(date, "yyyy-MM-dd ") + newTime);
    }

    public static List<String> getTimeList4IntervalSeconds(Integer intervalSeconds) {
        if (intervalSeconds == null) return Collections.emptyList();
        Date date = DateUtil.beginOfDay(new Date());
        List<String> result = new ArrayList<>();
        int totalSeconds = 1440 * 60;
        int times = totalSeconds / intervalSeconds;
        for (int i = 0; i < times; i++) {
            result.add(DateUtil.format(DateUtil.offsetSecond(date, intervalSeconds * i), TIME_FORMAT_SECONDS));
        }
        return result;
    }

    /**
     * 根据时间间隔导出时间节点列表
     *
     * @param intervalMinute
     * @return
     */
    public static List<String> getTimeList(Integer intervalMinute) {
        if (intervalMinute == null) return Collections.emptyList();
        Date date = DateUtil.beginOfDay(new Date());
        List<String> result = new ArrayList<>();
        int totalMinute = 1440;
        int times = totalMinute / intervalMinute;
        for (int i = 0; i < times; i++) {
            result.add(DateUtil.format(DateUtil.offsetMinute(date, intervalMinute * i), TIME_FORMAT));
        }
        return result;
    }

    public static final String TIME_FORMAT = "HH:mm";

    public static final String TIME_FORMAT_SECONDS = "HH:mm:ss";
}
