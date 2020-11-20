package com.zhuang.hutool;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import org.junit.Test;

import java.util.List;

public class ExcelUtilTest {

    @Test
    public void readBySax() {
        ExcelUtil.readBySax(this.getClass().getResourceAsStream("/excel/test.xlsx"), 0, new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowList) {
                rowList.forEach(System.out::println);
            }
        });
    }

}
