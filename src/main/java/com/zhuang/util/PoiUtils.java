package com.zhuang.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.RowUtil;
import cn.hutool.poi.excel.WorkbookUtil;
import cn.hutool.poi.excel.cell.CellUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.usermodel.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 注意：Workbook需要close释放
 */
public class PoiUtils {

    /**
     * 合并两个excel的数据
     *
     * @param inputStreamA
     * @param inputStreamB
     * @param headerRowCount
     * @return
     */
    public static InputStream merge(InputStream inputStreamA, InputStream inputStreamB, int headerRowCount) {
        try (Workbook workbookA = WorkbookUtil.createBook(inputStreamA); Workbook workbookB = WorkbookUtil.createBook(inputStreamB)) {
            for (Sheet sheetA : workbookA) {
                Sheet sheetB = workbookB.getSheet(sheetA.getSheetName());
                int lastRowIndexA = sheetA.getLastRowNum();
                int totalRowCountB = sheetB.getLastRowNum() + 1;
                for (int i = headerRowCount; i < totalRowCountB; i++) {
                    Row rowA = RowUtil.getOrCreateRow(sheetA, ++lastRowIndexA);
                    Row rowB = sheetB.getRow(i);
                    for (Cell cellB : rowB) {
                        Cell cellA = CellUtil.getOrCreateCell(rowA, cellB.getColumnIndex());
                        CellUtil.setCellValue(cellA, CellUtil.getCellValue(cellB), cellA.getCellStyle());
                    }
                }
            }
            return workbookToInputStream(workbookA);
        } catch (IOException e) {
            throw new RuntimeException("PoiUtils.merge fail!", e);
        }
    }


    /**
     * 根据工作表名称隐藏工作表
     *
     * @param inputStream
     * @param sheetNameList
     * @return
     */
    public static InputStream hiddenSheetByNames(InputStream inputStream, List<String> sheetNameList) {
        if (sheetNameList == null || sheetNameList.size() == 0) {
            return inputStream;
        }
        try (Workbook workbook = WorkbookUtil.createBook(inputStream)) {
            List<Integer> sheetIndexList = sheetNameList.stream().map(c -> workbook.getSheetIndex(c)).collect(Collectors.toList());
            return hiddenSheetByIndexes(workbook, false, sheetIndexList);
        } catch (IOException e) {
            throw new RuntimeException("PoiUtils.hiddenSheetByNames fail!", e);
        }
    }

    /**
     * 根据工作表索引隐藏工作表
     *
     * @param inputStream
     * @param sheetIndexList
     * @return
     */
    public static InputStream hiddenSheetByIndexes(InputStream inputStream, List<Integer> sheetIndexList) {
        if (sheetIndexList == null || sheetIndexList.size() == 0) {
            return inputStream;
        }
        try (Workbook workbook = WorkbookUtil.createBook(inputStream)) {
            return hiddenSheetByIndexes(workbook, false, sheetIndexList);
        } catch (IOException e) {
            throw new RuntimeException("PoiUtils.hiddenSheetByIndexes fail!", e);
        }
    }

    /**
     * 根据工作表名称删除工作表
     *
     * @param inputStream
     * @param sheetNameList
     * @return
     */
    public static InputStream removeSheetByNames(InputStream inputStream, List<String> sheetNameList) {
        if (sheetNameList == null || sheetNameList.size() == 0) {
            return inputStream;
        }
        try (Workbook workbook = WorkbookUtil.createBook(inputStream)) {
            return removeSheetByNames(workbook, sheetNameList);
        } catch (IOException e) {
            throw new RuntimeException("PoiUtils.removeSheetByNames fail!", e);
        }
    }

    /**
     * 根据工作表索引删除工作表
     *
     * @param inputStream
     * @param sheetIndexList
     * @return
     */
    public static InputStream removeSheetByIndexes(InputStream inputStream, List<Integer> sheetIndexList) {
        if (sheetIndexList == null || sheetIndexList.size() == 0) {
            return inputStream;
        }
        try (Workbook workbook = WorkbookUtil.createBook(inputStream)) {
            List<String> sheetNameList = sheetIndexList.stream().map(c -> workbook.getSheetAt(c).getSheetName()).collect(Collectors.toList());
            return removeSheetByNames(workbook, sheetNameList);
        } catch (IOException e) {
            throw new RuntimeException("PoiUtils.removeSheetByIndexes fail!", e);
        }
    }

    /**
     * 将工作簿转为输入流
     *
     * @param workbook
     * @return
     */
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

    /**
     * 根据参数替换Word里的文本内容
     *
     * @param inputStream
     * @param params
     * @return
     */
    public static InputStream replaceWord(InputStream inputStream, Map<String, Object> params) {
        if (params == null && params.isEmpty()) {
            return inputStream;
        }
        try {
            XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
            // 处理段落
            List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
            replaceParagraphs(paragraphs, params);
            // 处理表格
            List<XWPFTable> tables = xwpfDocument.getTables();
            for (XWPFTable table : tables) {
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        replaceParagraphs(cell.getParagraphs(), params);
                    }
                }
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            xwpfDocument.write(byteArrayOutputStream);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            return byteArrayInputStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private static void replaceParagraphs(List<XWPFParagraph> paragraphs, Map<String, Object> params) {
        if (paragraphs == null || paragraphs.isEmpty()) return;
        for (XWPFParagraph paragraph : paragraphs) {
            List<XWPFRun> runs = paragraph.getRuns();
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                params.entrySet().forEach(p -> {
                    run.setText(run.getText(run.getTextPosition()).replace(p.getKey(), p.getValue().toString()), 0);
                });
            }
        }
    }
}
