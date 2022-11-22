package com.zhuang.util;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WaterMeasureUtilsTest {

    @Test
    public void calcAvg4Water() {
        List<WaterMeasureUtils.WaterData> dataList = new ArrayList<>();

//        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:00:00"), new BigDecimal("11.1")));
//        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:05:00"), new BigDecimal("22.2")));
//        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:10:00"), new BigDecimal("33.3")));
//        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:15:00"), new BigDecimal("44.44")));
//        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:20:00"), new BigDecimal("55.5")));
//        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:25:00"), new BigDecimal("66.6")));
//        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:30:00"), new BigDecimal("77.7")));
//        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:35:00"), new BigDecimal("88.8")));

        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:00:00"), new BigDecimal("2")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:05:00"), new BigDecimal("4")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:10:00"), new BigDecimal("6")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:15:00"), new BigDecimal("8")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:20:00"), new BigDecimal("10")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:25:00"), new BigDecimal("9")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:30:00"), new BigDecimal("8")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:35:00"), new BigDecimal("7")));
        System.out.println(WaterMeasureUtils.isMissingData(dataList));
        System.out.println(WaterMeasureUtils.calcAvg4Water(dataList));
        System.out.println(WaterMeasureUtils.calcAvg4Normal(dataList));

    }

    @Test
    public void smoothDataList() {
        List<WaterMeasureUtils.WaterData> dataList = new ArrayList<>();

        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:00:00"), new BigDecimal("11.1")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:05:00"), new BigDecimal("22.2")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:10:00"), new BigDecimal("33.3")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:15:00"), new BigDecimal("44.44")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:20:00"), new BigDecimal("55.5")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:25:00"), new BigDecimal("66.6")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:30:00"), new BigDecimal("77.7")));
        dataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:35:00"), new BigDecimal("88.8")));

        List<WaterMeasureUtils.WaterData> leftDataList = new ArrayList<>();
        leftDataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:00:00"), new BigDecimal("11.1")));
        leftDataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:05:00"), new BigDecimal("22.2")));

        List<WaterMeasureUtils.WaterData> rightDataList = new ArrayList<>();
        rightDataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:00:00"), new BigDecimal("11.1")));
        rightDataList.add(new MyWaterData(DateUtil.parseDateTime("2022-11-01 00:05:00"), new BigDecimal("22.2")));


        WaterMeasureUtils.smoothDataList(dataList, 2, 2, leftDataList, rightDataList);
        System.out.println(dataList);
    }

    @Data
    public static class MyWaterData implements WaterMeasureUtils.WaterData {

        private Date dataTime;
        private BigDecimal dataValue;

        public MyWaterData(Date dataTime, BigDecimal dataValue) {
            this.dataTime = dataTime;
            this.dataValue = dataValue;
        }

        @Override
        public Date getDataTime() {
            return this.dataTime;
        }

        @Override
        public BigDecimal getDataValue() {
            return this.dataValue;
        }

        @Override
        public void setDataValue(BigDecimal dataValue) {
            this.dataValue = dataValue;
        }
    }

}