package com.zhuang.util;

import cn.hutool.core.io.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

}
