package com.zhuang.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.RowUtil;
import cn.hutool.poi.excel.WorkbookUtil;
import cn.hutool.poi.excel.cell.CellUtil;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 注意：Workbook需要close释放
 */
public class PoiUtils {

    /**
     * 合并多个个excel的数据
     *
     * @param inputFileNameList
     * @param outputFileName
     * @param headerRowCount
     */
    public static void merge(List<String> inputFileNameList, String outputFileName, int headerRowCount) {
        merge(inputFileNameList, outputFileName, headerRowCount, false);
    }

    /**
     * 合并多个excel的数据
     *
     * @param inputFileNameList
     * @param outputFileName
     * @param headerRowCount
     * @param deleteOldFiles
     */
    public static void merge(List<String> inputFileNameList, String outputFileName, int headerRowCount, boolean deleteOldFiles) {
        String tempOutputFileName = outputFileName + ".temp";
        if (CollectionUtils.isEmpty(inputFileNameList)) return;
        inputFileNameList = inputFileNameList.stream().filter(FileUtil::exist).collect(Collectors.toList());
        FileUtil.mkParentDirs(outputFileName);
        // 只有一个文件时的处理
        if (inputFileNameList.size() == 1) {
            String inputFileName = inputFileNameList.get(0);
            try (InputStream fileInputStream = new FileInputStream(inputFileName); Workbook workbook = WorkbookUtil.createBook(fileInputStream)) {
                for (Sheet sheet : workbook) {
                    int sheetIndex = workbook.getSheetIndex(sheet);
                    if (sheet.getSheetName().contains("_")) {
                        String[] sheetNameArr = sheet.getSheetName().split("\\_");
                        String sheetNewName = sheetNameArr[0];
                        workbook.setSheetName(sheetIndex, sheetNewName);
                    }
                }
                try (OutputStream outputStream = new FileOutputStream(outputFileName)) {
                    writeWorkbookToOutputStream(workbook, outputStream);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (deleteOldFiles) {
                FileUtil.del(inputFileName);
            }
            return;
        }
        // 有多个文件多处理
        if (CollectionUtils.isEmpty(inputFileNameList)) return;
        FileUtil.copy(new File(inputFileNameList.get(0)), new File(tempOutputFileName), true);
        for (int i = 1; i < inputFileNameList.size(); i++) {
            String inputFileNameB = inputFileNameList.get(i);
            boolean renameSheetName = false;
            if (i == (inputFileNameList.size() - 1)) {
                renameSheetName = true;
            }
            try (InputStream inputStreamA = new FileInputStream(tempOutputFileName); InputStream inputStreamB = new FileInputStream(inputFileNameB); OutputStream outputStream = new FileOutputStream(outputFileName)) {
                merge(inputStreamA, inputStreamB, outputStream, headerRowCount, renameSheetName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            FileUtil.copy(outputFileName, tempOutputFileName, true);
        }
        if (deleteOldFiles) {
            inputFileNameList.forEach(fileName -> FileUtil.del(new File(fileName)));
        }
        FileUtil.del(new File(tempOutputFileName));
    }

    /**
     * 合并两个excel的数据
     *
     * @param inputStreamA
     * @param inputStreamB
     * @param outputStream
     * @param headerRowCount
     */
    public static void merge(InputStream inputStreamA, InputStream inputStreamB, OutputStream outputStream, int headerRowCount, boolean renameSheetName) {
        try (Workbook workbookA = WorkbookUtil.createBook(inputStreamA); Workbook workbookB = WorkbookUtil.createBook(inputStreamB)) {
            Map<Integer, String> sheetANewNameMap = new HashMap<>();
            for (Sheet sheetA : workbookA) {
                int sheetAIndex = workbookA.getSheetIndex(sheetA);
                String sheetAOldName = sheetA.getSheetName();
                String sheetANewName = sheetA.getSheetName();
                if (sheetAOldName.contains("_")) {
                    String[] sheetNameArr = sheetAOldName.split("\\_");
                    sheetANewName = sheetNameArr[0];

                    headerRowCount = Integer.parseInt(sheetNameArr[1].trim());
                }
                sheetANewNameMap.put(workbookA.getSheetIndex(sheetA), sheetANewName);
                Sheet sheetB = workbookB.getSheetAt(sheetAIndex);
                int lastRowIndexA = sheetA.getLastRowNum();
                int totalRowCountB = sheetB.getLastRowNum() + 1;
                Row templateRowA = null;
                if (lastRowIndexA > headerRowCount) {
                    templateRowA = sheetA.getRow(lastRowIndexA);
                }
                for (int i = headerRowCount; i < totalRowCountB; i++) {
                    Row rowA = RowUtil.getOrCreateRow(sheetA, ++lastRowIndexA);
                    Row rowB = sheetB.getRow(i);
                    for (Cell cellB : rowB) {
                        Cell cellA = CellUtil.getOrCreateCell(rowA, cellB.getColumnIndex());
                        CellStyle cellStyle;
                        if (templateRowA != null) {
                            cellStyle = CellUtil.getOrCreateCell(templateRowA, cellB.getColumnIndex()).getCellStyle();
                        } else {
                            cellStyle = cellA.getCellStyle();
                        }
                        CellUtil.setCellValue(cellA, CellUtil.getCellValue(cellB), cellStyle);
                    }
                }
            }
            if (renameSheetName) {
                for (Sheet sheetA : workbookA) {
                    Integer sheetAIndex = workbookA.getSheetIndex(sheetA);
                    String sheetANewName = sheetANewNameMap.get(sheetAIndex);
                    workbookA.setSheetName(sheetAIndex, sheetANewName);
                }
            }
            writeWorkbookToOutputStream(workbookA, outputStream);
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

    public static void writeWorkbookToOutputStream(Workbook workbook, OutputStream outputStream) {
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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


    public static void handleEachCell(String inputFileName, String outputFileName, Consumer<CellContext> cellHandler) {
        try (InputStream inputStream = new FileInputStream(inputFileName); OutputStream outputStream = new FileOutputStream(outputFileName)) {
            handleEachCell(inputStream, outputStream, cellHandler);
        } catch (IOException e) {
            throw new RuntimeException("PoiUtils.handleEachCells fail!", e);
        }
    }

    public static void handleEachCell(InputStream inputStream, OutputStream outputStream, Consumer<CellContext> cellHandler) {
        try (Workbook workbook = WorkbookUtil.createBook(inputStream)) {
            handleEachCell(workbook, cellHandler);
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("PoiUtils.handleEachCells fail!", e);
        }
    }

    /**
     * 对每个单元格进行处理
     *
     * @param workbook
     * @param cellHandler
     */
    public static void handleEachCell(Workbook workbook, Consumer<CellContext> cellHandler) {
        for (Sheet sheet : workbook) {
            for (Row row : sheet) {
                for (Cell cell : row) {
                    CellContext cellContext = new CellContext();
                    cellContext.setCell(cell);
                    cellContext.setRow(row);
                    cellContext.setSheet(sheet);
                    cellContext.setWorkbook(workbook);
                    cellHandler.accept(cellContext);
                }
            }
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

    @Data
    public static class CellContext {
        private Cell cell;
        private Row row;
        private Sheet sheet;
        private Workbook workbook;
    }
}
