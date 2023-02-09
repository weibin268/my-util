package com.zhuang.hutool;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.thread.ThreadUtil;
import lombok.Data;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvUtilTest {

    @Test
    public void test() {
        StringWriter stringWriter = new StringWriter();
        CsvWriter csvWriter = CsvUtil.getWriter(stringWriter);
        List<List<Object>> list = new ArrayList<>();
        List<Object> row1 = new ArrayList<>();
        row1.add("a");
        row1.add(1);
        list.add(row1);
        List<Object> row2 = new ArrayList<>();
        row2.add("b");
        row2.add(2);
        list.add(row2);
        csvWriter.write(list);
        System.out.println(stringWriter);
    }

}
