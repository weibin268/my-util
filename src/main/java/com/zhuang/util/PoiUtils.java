package com.zhuang.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.WorkbookUtil;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PoiUtils {

    public static InputStream removeSheet(InputStream inputStream, List<Integer> sheetIndexList) {
        if (sheetIndexList == null || sheetIndexList.size() == 0) {
            return inputStream;
        }
        Workbook workbook = WorkbookUtil.createBook(inputStream);
        sheetIndexList.forEach(index -> workbook.removeSheetAt(index));
        return workbookToInputStream(workbook);
    }

    public static InputStream workbookToInputStream(Workbook workbook) {
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
