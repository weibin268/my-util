package com.zhuang.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.WorkbookUtil;
import org.apache.poi.ss.usermodel.SheetVisibility;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PoiUtils {

    public static InputStream hiddenSheet(InputStream inputStream, List<Integer> sheetIndexList) {
        return hiddenSheet(inputStream, sheetIndexList, false);
    }

    public static InputStream hiddenSheet(InputStream inputStream, List<Integer> sheetIndexList, boolean veryHidden) {
        if (sheetIndexList == null || sheetIndexList.size() == 0) {
            return inputStream;
        }
        Workbook workbook = WorkbookUtil.createBook(inputStream);
        sheetIndexList.forEach(index -> {
            if (veryHidden) {
                //该方法隐藏后用户不可以取消隐藏
                workbook.setSheetVisibility(index, SheetVisibility.VERY_HIDDEN);
            } else {
                //该方法第一个sheet隐藏不了
                workbook.setSheetHidden(index, true);
            }
        });
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
