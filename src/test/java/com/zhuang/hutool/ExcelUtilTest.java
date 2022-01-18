package com.zhuang.hutool;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @Test
    public void test() {
        List<Map<String, Object>> userList = new ArrayList<>();
        Map<String, Object> user = new HashMap<>();
        user.put("name", "zwb");
        user.put("age", 11);
        userList.add(user);
        user = new HashMap<>();
        user.put("name", "lxc1111111111111111111111111111111");
        user.put("age", 12);
        userList.add(user);
        final ExcelWriter writer = ExcelUtil.getWriter("D:\\temp\\test.xlsx");
        final Font headFont = writer.getWorkbook().createFont();
        headFont.setBold(true);
        writer.addHeaderAlias("name", "姓名");
        writer.getHeadCellStyle().setFont(headFont);
        writer.setColumnWidth(1, 100);
        writer.write(userList).flush();
    }
}
