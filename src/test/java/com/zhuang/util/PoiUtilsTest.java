package com.zhuang.util;

import cn.hutool.core.io.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class PoiUtilsTest {

    @Test
    public void removeSheet() throws IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/excel/test.xlsx");
        //InputStream inputStream = PoiUtils.hiddenSheetByIndexes(resourceAsStream, Arrays.asList(0));
        InputStream inputStream = PoiUtils.hiddenSheetByNames(resourceAsStream, Arrays.asList("sheet1"));
        FileUtil.writeFromStream(inputStream, new File("D:\\temp\\test-out.xlsx"));
    }
}
