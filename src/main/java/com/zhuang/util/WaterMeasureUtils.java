package com.zhuang.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.lang.Collections;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 水利测量工具类
 */
public class WaterMeasureUtils {

    /**
     * 计算平均值
     *
     * @param dataList
     * @return
     */
    public static BigDecimal calcAvg4Water(List<? extends WaterData> dataList) {

        dataList = dataList.stream()
                .filter(c -> c.getDataTime() != null && c.getDataValue() != null)
                .sorted(Comparator.comparing(WaterData::getDataTime))
                .collect(Collectors.toList());

        boolean missingData = isMissingData(dataList);
        BigDecimal result = null;
        if (missingData) {
            // 如果有缺数据
            List<BigDecimal> minutesList = new ArrayList<>();
            List<BigDecimal> areaList = new ArrayList<>();
            for (int i = 0; i < dataList.size(); i++) {
                WaterData curWaterData = dataList.get(i);
                WaterData nextWaterData = null;
                if ((i + 1) < dataList.size()) {
                    nextWaterData = dataList.get(i + 1);
                }
                if (nextWaterData == null) break;
                BigDecimal minutes = BigDecimal.valueOf(DateUtil.between(curWaterData.getDataTime(), nextWaterData.getDataTime(), DateUnit.MINUTE));
                BigDecimal area = curWaterData.getDataValue().add(nextWaterData.getDataValue()).multiply(minutes).divide(new BigDecimal("2"), 10, RoundingMode.HALF_UP);
                areaList.add(area);
                minutesList.add(minutes);
            }
            BigDecimal sumArea = areaList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumMinutes = minutesList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            result = sumArea.divide(sumMinutes, 5, RoundingMode.HALF_UP);
        } else {
            // 没有缺数据
            if (dataList.size() < 3) {
                return dataList.stream().map(WaterData::getDataValue).reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(dataList.size()), 5, RoundingMode.HALF_UP);
            }
            WaterData firstWaterData = dataList.get(0);
            WaterData lastWaterData = dataList.get(dataList.size() - 1);
            List<WaterData> middleWaterDataList = dataList.stream().filter(c -> !c.equals(firstWaterData) && !c.equals(lastWaterData)).collect(Collectors.toList());
            BigDecimal sumMiddle = middleWaterDataList.stream().map(WaterData::getDataValue).reduce(BigDecimal.ZERO, BigDecimal::add);
            result = sumMiddle.add(firstWaterData.getDataValue().divide(new BigDecimal("2"), 5, RoundingMode.HALF_UP))
                    .add(lastWaterData.getDataValue().divide(new BigDecimal("2"), 5, RoundingMode.HALF_UP))
                    .divide(BigDecimal.valueOf(dataList.size() - 1), 5, RoundingMode.HALF_UP);
        }

        return result;
    }

    /**
     * 计算平均值
     *
     * @param dataList
     * @return
     */
    public static BigDecimal calcAvg4Normal(List<? extends WaterData> dataList) {
        dataList = dataList.stream()
                .filter(c -> c.getDataTime() != null && c.getDataValue() != null)
                .sorted(Comparator.comparing(WaterData::getDataTime))
                .collect(Collectors.toList());

        return dataList.stream().map(WaterData::getDataValue).reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(dataList.size()), 5, RoundingMode.HALF_UP);
    }

    public static boolean isMissingData(List<? extends WaterData> dataList) {
        if (CollectionUtils.isEmpty(dataList) || dataList.size() < 3) return false;
        Long preMinutes = null;
        for (int i = 0; i < dataList.size(); i++) {
            WaterData curWaterData = dataList.get(i);
            WaterData nextWaterData = null;
            if ((i + 1) < dataList.size()) {
                nextWaterData = dataList.get(i + 1);
            }
            if (nextWaterData == null) break;
            Long curMinutes = DateUtil.between(curWaterData.getDataTime(), nextWaterData.getDataTime(), DateUnit.MINUTE);
            if (preMinutes != null && !preMinutes.equals(curMinutes)) {
                return true;
            }
            preMinutes = curMinutes;
        }
        return false;
    }


    /**
     * 平滑数据
     *
     * @param dataList
     * @param leftCount
     * @param rightCount
     */
    public static void smoothDataList(List<? extends WaterData> dataList, int leftCount, int rightCount) {
        smoothDataList(dataList, leftCount, rightCount, null, null);
    }

    /**
     * 平滑数据
     *
     * @param dataList
     * @param leftCount
     * @param rightCount
     */
    public static void smoothDataList(List<? extends WaterData> dataList, int leftCount, int rightCount, List<? extends WaterData> extLeftDataList, List<? extends WaterData> extRightDataList) {
        for (int i = 0; i < dataList.size(); i++) {
            WaterData waterData = dataList.get(i);
            if (waterData.getDataValue() == null) continue;
            List<WaterData> sampleList = new ArrayList<>();
            sampleList.add(waterData);
            for (int j = 1; j <= leftCount; j++) {
                int sampleIndex = i - j;
                if (sampleIndex >= 0) {
                    sampleList.add(dataList.get(sampleIndex));
                } else if (!Collections.isEmpty(extLeftDataList)) {
                    sampleIndex = extLeftDataList.size() - Math.abs(sampleIndex);
                    if (sampleIndex >= 0) {
                        sampleList.add(extLeftDataList.get(sampleIndex));
                    }
                }
            }
            for (int j = 1; j <= rightCount; j++) {
                int sampleIndex = i + j;
                if (sampleIndex < dataList.size()) {
                    sampleList.add(dataList.get(sampleIndex));
                } else if (!Collections.isEmpty(extRightDataList)) {
                    sampleIndex = extRightDataList.size() - Math.abs(sampleIndex);
                    if (sampleIndex >= 0) {
                        sampleList.add(extRightDataList.get(sampleIndex));
                    }
                }
            }
            sampleList = sampleList.stream().filter(c -> c.getDataValue() != null).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(sampleList)) continue;
            BigDecimal avgValue = sampleList.stream().map(c -> c.getDataValue()).reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(sampleList.size()), 3, RoundingMode.HALF_UP);
            if (avgValue != null) {
                waterData.setDataValue(avgValue);
            }
        }
    }

    public interface WaterData {
        Date getDataTime();

        BigDecimal getDataValue();

        default void setDataValue(BigDecimal dataValue) {
        }
    }

    @Data
    public static class DefaultWaterData implements WaterData {

        private Date dataTime;
        private BigDecimal dataValue;

        public DefaultWaterData(Date dataTime, BigDecimal dataValue) {
            this.dataTime = dataTime;
            this.dataValue = dataValue;
        }
    }

}
