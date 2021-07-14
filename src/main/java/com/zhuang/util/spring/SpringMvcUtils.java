package com.zhuang.util.spring;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class SpringMvcUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

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

    public static OutputStream getFileOutputStream(String fileName, HttpServletResponse response) {
        try {
            toFileResponse(response, fileName);
            return response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getFileInputStream(String resourceFilePath) {
        return SpringMvcUtils.class.getResourceAsStream(resourceFilePath);
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
