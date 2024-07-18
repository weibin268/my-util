package com.zhuang.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PoiUtilsTest {

    @Test
    public void hiddenSheet() {
        InputStream resourceAsStream = getClass().getResourceAsStream("/excel/test.xlsx");
        //InputStream inputStream = PoiUtils.hiddenSheetByIndexes(resourceAsStream, Arrays.asList(0));
        InputStream inputStream = PoiUtils.hiddenSheetByNames(resourceAsStream, Arrays.asList("sheet1"));
        FileUtil.writeFromStream(inputStream, new File("D:\\temp\\test-out.xlsx"));
    }

    @Test
    public void removeSheet() {
        InputStream resourceAsStream = getClass().getResourceAsStream("/excel/test.xlsx");
        //InputStream inputStream = PoiUtils.removeSheetByIndexes(resourceAsStream, Arrays.asList(0));
        InputStream inputStream = PoiUtils.removeSheetByNames(resourceAsStream, Arrays.asList("sheet1"));
        FileUtil.writeFromStream(inputStream, new File("D:\\temp\\test-out.xlsx"));
    }

    @Test
    public void replaceWord() {
        InputStream resourceAsStream = getClass().getResourceAsStream("/word/test.docx");
        Map<String, Object> params = new HashMap<>();
        params.put("{name}", "zwb");
        params.put("{title1}", "姓名");
        params.put("{item1}", "zhuang weibin");
        InputStream inputStream = PoiUtils.replaceWord(resourceAsStream, params);
        FileUtil.writeFromStream(inputStream, new File("D:\\temp\\test-out.docx"));
    }


    @Test
    public void merge() {
        List<String> inputFileNameList = new ArrayList<>();
        inputFileNameList.add(getClass().getResource("/excel/test_merge_1.xlsx").getPath());
        inputFileNameList.add(getClass().getResource("/excel/test_merge_2.xlsx").getPath());
        inputFileNameList.add(getClass().getResource("/excel/test_merge_3.xlsx").getPath());
        String outputFileName = "/Users/zhuang/Documents/test_merge.xlsx";
        PoiUtils.merge(inputFileNameList, outputFileName, 1);
    }

    @Test
    public void handleEachCell() throws IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/excel/test.xlsx");
        OutputStream outputStream = Files.newOutputStream(Paths.get("d:\\temp\\test-out.xlsx"));
        Map<String, CellStyle> cellStyleMap = new HashMap<>();
        PoiUtils.handleEachCell(resourceAsStream, outputStream, c -> {
            String dataFormatType = null;
            String dataFormatTypeFlag = "dataFormatType:";
            if (StrUtil.isNotEmpty(c.getCell().getCellStyle().getDataFormatString()) && c.getCell().getCellStyle().getDataFormatString().contains(dataFormatTypeFlag)) {
                dataFormatType = c.getCell().getCellStyle().getDataFormatString().replace(dataFormatTypeFlag, "").replace("\"", "");
            }
            if (StrUtil.isNotEmpty(dataFormatType) && c.getCell().getCellType().equals(CellType.NUMERIC)) {
                String dataFormat0 = "#0";
                String dataFormat1 = "#0.0";
                String dataFormat2 = "#0.00";
                String dataFormat3 = "#0.000";
                String dataFormat = null;
                if (dataFormatType.contains("water_flow")) {
                    double cellValue = Math.abs(c.getCell().getNumericCellValue());
                    if (cellValue < 1) {
                        dataFormat = dataFormat3;
                    } else if (cellValue < 10) {
                        dataFormat = dataFormat2;
                    } else if (cellValue < 100) {
                        dataFormat = dataFormat1;
                    } else {
                        dataFormat = dataFormat0;
                    }
                }
                if (StrUtil.isNotEmpty(dataFormat)) {
                    fillCellStyle(c, dataFormat, cellStyleMap);
                    c.getCell().getCellStyle().setDataFormat(c.getWorkbook().createDataFormat().getFormat(dataFormat));
                }
            }
        });
    }

    public static void fillCellStyle(PoiUtils.CellContext c, String key, Map<String, CellStyle> cellStyleMap) {
        CellStyle newCellStyle;
        if (cellStyleMap.containsKey(key)) {
            newCellStyle = cellStyleMap.get(key);
        } else {
            // 创建新的单元格样式，不然对当前单元格样式的修改会作用到其他的单元格
            CellStyle oldCellStyle = c.getCell().getCellStyle();
            newCellStyle = c.getWorkbook().createCellStyle();
            newCellStyle.cloneStyleFrom(oldCellStyle);
            cellStyleMap.put(key, newCellStyle);
        }
        c.getCell().setCellStyle(newCellStyle);
    }

}
