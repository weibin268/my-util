package com.zhuang.util;

import cn.hutool.core.io.FileUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.Test;

import java.io.*;
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
    public void handleEachCell() throws FileNotFoundException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/excel/test.xlsx");
        OutputStream outputStream = new FileOutputStream("d:\\temp\\test-out.xlsx");
        PoiUtils.handleEachCell(resourceAsStream, outputStream, c -> {
            if (c.getCell().getCellType().equals(CellType.NUMERIC)) {
                double cellValue = Math.abs(c.getCell().getNumericCellValue());
                CellStyle oldCellStyle = c.getCell().getCellStyle();
                CellStyle newCellStyle = c.getWorkbook().createCellStyle();
                newCellStyle.cloneStyleFrom(oldCellStyle);
                c.getCell().setCellStyle(newCellStyle);
                if (cellValue < 1) {
                    c.getCell().getCellStyle().setDataFormat(c.getWorkbook().createDataFormat().getFormat("#0.000"));
                } else if (cellValue < 10) {
                    c.getCell().getCellStyle().setDataFormat(c.getWorkbook().createDataFormat().getFormat("#0.00"));
                } else if (cellValue < 100) {
                    c.getCell().getCellStyle().setDataFormat(c.getWorkbook().createDataFormat().getFormat("#0.0"));
                } else {
                    c.getCell().getCellStyle().setDataFormat(c.getWorkbook().createDataFormat().getFormat("#0"));
                }
            }
        });
    }

}
