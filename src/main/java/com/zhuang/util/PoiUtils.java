package com.zhuang.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.WorkbookUtil;
import org.apache.poi.ss.usermodel.SheetVisibility;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PoiUtils {

    public static InputStream hiddenSheetByNames(InputStream inputStream, List<String> sheetNameList) {
        if (sheetNameList == null || sheetNameList.size() == 0) {
            return inputStream;
        }
        Workbook workbook = WorkbookUtil.createBook(inputStream);
        List<Integer> sheetIndexList = sheetNameList.stream().map(c -> workbook.getSheetIndex(c)).collect(Collectors.toList());
        return hiddenSheetByIndexes(workbook, false, sheetIndexList);
    }

    public static InputStream hiddenSheetByIndexes(InputStream inputStream, List<Integer> sheetIndexList) {
        if (sheetIndexList == null || sheetIndexList.size() == 0) {
            return inputStream;
        }
        Workbook workbook = WorkbookUtil.createBook(inputStream);
        return hiddenSheetByIndexes(workbook, false, sheetIndexList);
    }

    public static InputStream removeSheetByNames(InputStream inputStream, List<String> sheetNameList) {
        if (sheetNameList == null || sheetNameList.size() == 0) {
            return inputStream;
        }
        Workbook workbook = WorkbookUtil.createBook(inputStream);

        return removeSheetByNames(workbook, sheetNameList);
    }

    public static InputStream removeSheetByIndexes(InputStream inputStream, List<Integer> sheetIndexList) {
        if (sheetIndexList == null || sheetIndexList.size() == 0) {
            return inputStream;
        }
        Workbook workbook = WorkbookUtil.createBook(inputStream);
        List<String> sheetNameList = sheetIndexList.stream().map(c -> workbook.getSheetAt(c).getSheetName()).collect(Collectors.toList());
        return removeSheetByNames(workbook, sheetNameList);
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

    private static InputStream hiddenSheetByIndexes(Workbook workbook, boolean veryHidden, List<Integer> sheetIndexList) {
        sheetIndexList.sort(Integer::compareTo);
        sheetIndexList.forEach(index -> {
            if (veryHidden) {
                //隐藏后用户不可以取消隐藏
                workbook.setSheetVisibility(index, SheetVisibility.VERY_HIDDEN);
            } else {
                if (index == workbook.getActiveSheetIndex()) {
                    int activeIndex = 0;
                    if (index < (workbook.getNumberOfSheets() - 1)) {
                        activeIndex = index + 1;
                    }
                    workbook.setActiveSheet(activeIndex);
                }
                //隐藏后用户可以取消隐藏（注：当sheet为活动的时隐藏不了）
                workbook.setSheetVisibility(index, SheetVisibility.HIDDEN);
            }
        });
        return workbookToInputStream(workbook);
    }

    private static InputStream removeSheetByNames(Workbook workbook, List<String> sheetNameList) {
        sheetNameList.forEach(sheetName -> {
            int sheetIndex = workbook.getSheetIndex(sheetName);
            workbook.removeSheetAt(sheetIndex);
        });
        return workbookToInputStream(workbook);
    }

}
