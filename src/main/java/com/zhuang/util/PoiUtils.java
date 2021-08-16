package com.zhuang.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.WorkbookUtil;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PoiUtils {

    public InputStream removeSheet(InputStream inputStream, Integer... sheetIndexes) {
        Workbook workbook = WorkbookUtil.createBook(inputStream);
        for (int i = 0; i < sheetIndexes.length; i++) {
            workbook.removeSheetAt(i);
        }
        return workbookToInputStream(workbook);
    }

    public InputStream workbookToInputStream(Workbook workbook) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayInputStream result = IoUtil.toStream(byteArrayOutputStream.toByteArray());
        return result;
    }
    
}
