package com.zhuang.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

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

    public static BigDecimal calcAvg4Water(List<WaterData> dataList) {

        dataList = dataList.stream()
                .filter(c -> c.getDataTime() != null && c.getDataValue() != null)
                .sorted(Comparator.comparing(WaterData::getDataTime))
                .collect(Collectors.toList());
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
        BigDecimal result = sumArea.divide(sumMinutes, 5, RoundingMode.HALF_UP);
        return result;
    }


    public static BigDecimal calcAvg4Normal(List<WaterData> dataList) {
        dataList = dataList.stream()
                .filter(c -> c.getDataTime() != null && c.getDataValue() != null)
                .sorted(Comparator.comparing(WaterData::getDataTime))
                .collect(Collectors.toList());

        return dataList.stream().map(WaterData::getDataValue).reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(dataList.size()), 5, RoundingMode.HALF_UP);
    }

    public interface WaterData {
        Date getDataTime();

        BigDecimal getDataValue();
    }

}
