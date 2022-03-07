package com.zhuang.util;

import com.artofsolving.jodconverter.*;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.google.common.io.Files;

import java.io.*;
import java.net.ConnectException;

/*
启动OpenOffice服务
1、进入目录
cd C:\Program Files (x86)\OpenOffice 4\program
2、执行
soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
3、查看是否启动成功
netstat -ano|findstr "8100"
*/
public class OpenOfficeUtils {

    public static void convert(InputStream inputStream, String inputFileExtension, OutputStream outputStream, String outputFileExtension) {
        try {
            // 打开连接
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
            connection.connect();
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);

            BasicDocumentFormatRegistry documentFormatRegistry = new DefaultDocumentFormatRegistry();
            // 添加xlsx格式
            DocumentFormat xls = new DocumentFormat("Microsoft Excel", DocumentFamily.SPREADSHEET, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" , "xlsx");
            xls.setExportFilter(DocumentFamily.SPREADSHEET, "MS Excel 2003");
            documentFormatRegistry.addDocumentFormat(xls);
            // 获取Format
            DocumentFormat inputDocumentFormat = documentFormatRegistry.getFormatByFileExtension(inputFileExtension);
            DocumentFormat outputDocumentFormat = documentFormatRegistry.getFormatByFileExtension(outputFileExtension);
            // 执行转换
            converter.convert(inputStream, inputDocumentFormat, outputStream, outputDocumentFormat);
        } catch (ConnectException e) {
            throw new RuntimeException(e);
        }
    }

    public static void convert(File inputFile, File outputFile) {
        try {
            convert(new FileInputStream(inputFile), getFileExtension(inputFile.getName()), new FileOutputStream(outputFile), getFileExtension(outputFile.getName()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
