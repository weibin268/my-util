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

    private static String host = SocketOpenOfficeConnection.DEFAULT_HOST;

    private static Integer port = SocketOpenOfficeConnection.DEFAULT_PORT;

    public static void setHost(String host) {
        OpenOfficeUtils.host = host;
    }

    public static void setPort(Integer port) {
        OpenOfficeUtils.port = port;
    }

    public static void convert(InputStream inputStream, String inputFileExtension, OutputStream outputStream, String outputFileExtension) {
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(host, port);
        try {
            // 打开连接
            connection.connect();
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);

            BasicDocumentFormatRegistry documentFormatRegistry = new DefaultDocumentFormatRegistry();
            // 添加xlsx格式
            DocumentFormat xls = new DocumentFormat("Microsoft Excel 2007-2013 XML", DocumentFamily.SPREADSHEET, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
            xls.setExportFilter(DocumentFamily.SPREADSHEET, "Calc MS Excel 2007 XML");
            documentFormatRegistry.addDocumentFormat(xls);
            // 获取Format
            DocumentFormat inputDocumentFormat = documentFormatRegistry.getFormatByFileExtension(inputFileExtension);
            DocumentFormat outputDocumentFormat = documentFormatRegistry.getFormatByFileExtension(outputFileExtension);
            // 执行转换
            converter.convert(inputStream, inputDocumentFormat, outputStream, outputDocumentFormat);

        } catch (ConnectException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection.isConnected()) {
                connection.disconnect();
            }
        }
    }

    public static void convert(File inputFile, File outputFile) {
        try (FileInputStream fileInputStream = new FileInputStream(inputFile); FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            convert(fileInputStream, getFileExtension(inputFile.getName()), fileOutputStream, getFileExtension(outputFile.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
