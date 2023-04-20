package com.zhuang.util.spring;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

public class SpringWebUtils {

    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 获取当前会话的 request
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            throw new RuntimeException("非Web上下文无法获取Request");
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * 设置子进程也可以获取到该对象，在创建子进程之前调用
     */
    public static void setInheritable() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(servletRequestAttributes, true);
    }

    /**
     * 获取当前会话的 response
     *
     * @return response
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            throw new RuntimeException("非Web上下文无法获取Request");
        }
        return servletRequestAttributes.getResponse();
    }

    /**
     * 获取上传文件的输入流
     *
     * @param request
     * @return
     */
    public static InputStream getFileInputStream(HttpServletRequest request) {
        InputStream result = null;
        StandardMultipartHttpServletRequest multipartRequest = new StandardMultipartHttpServletRequest(request);
        for (Map.Entry<String, List<MultipartFile>> entry : multipartRequest.getMultiFileMap().entrySet()) {
            for (MultipartFile file : entry.getValue()) {
                try {
                    result = file.getInputStream();
                    break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

    /**
     * 获取下载文件的输出流
     *
     * @param fileName
     * @param response
     * @return
     */
    public static OutputStream getFileOutputStream(String fileName, HttpServletResponse response) {
        try {
            toFileResponse(response, fileName);
            return response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取资源文件的输入流
     *
     * @param resourceFilePath
     * @return
     */
    public static InputStream getFileInputStream(String resourceFilePath) {
        return SpringWebUtils.class.getResourceAsStream(resourceFilePath);
    }

    /**
     * 通过输入流下载文件
     *
     * @param inputStream
     * @param fileName
     * @param response
     */
    public static void downloadFile(InputStream inputStream, String fileName, HttpServletResponse response) {
        try {
            toFileResponse(response, fileName);
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void toExcelFileResponse(HttpServletResponse response, String fileName) {
        toFileResponse(response, fileName, "application/vnd.ms-excel");
    }

    public static void toFileResponse(HttpServletResponse response, String fileName) {
        toFileResponse(response, fileName, null);
    }

    public static void toFileResponse(HttpServletResponse response, String fileName, String contentType) {
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        response.setContentType(contentType);
        response.setCharacterEncoding(DEFAULT_CHARSET);
        response.setHeader("Content-disposition", "attachment;filename*=" + DEFAULT_CHARSET + "''" + encodeFileName(fileName, DEFAULT_CHARSET));
    }

    public static String encodeFileName(String fileName, String charset) {
        try {
            // 这里URLEncoder.encode可以防止中文乱码
            fileName = URLEncoder.encode(fileName, charset);
            // “+”替换为“%20”，encode后的空格变为了“+”，需替换为html的空格转义符“%20”
            fileName = fileName.replaceAll("\\+", "%20");
            return fileName;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
