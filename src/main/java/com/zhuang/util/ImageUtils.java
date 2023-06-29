package com.zhuang.util;

import cn.hutool.core.img.FontUtil;
import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.IoUtil;
import sun.font.FontDesignMetrics;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class ImageUtils {

    public static byte[] getBytesByUrl(String path) {
        InputStream in = null;
        ByteArrayOutputStream out;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection instanceof HttpsURLConnection) {
                SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");//第一个参数为协议,第二个参数为提供者(可以缺省)
                TrustManager[] tm = {new MyX509TrustManager()};
                sslcontext.init(null, tm, new SecureRandom());

                HttpsURLConnection connection4Https = (HttpsURLConnection) connection;
                connection4Https.setHostnameVerifier((hostname, session) -> true);
                connection4Https.setSSLSocketFactory(sslcontext.getSocketFactory());
            }
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            in = connection.getInputStream();
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
        return out.toByteArray();
    }

    public static String getBase64ByUrl(String path) {
        return Base64.getEncoder().encodeToString(getBytesByUrl(path));
    }

    public static void addText(InputStream inputStream, OutputStream outputStream, String text, Position position) {
        addText(inputStream, outputStream, text, position, null, "黑体", 0.04d, 1f);
    }

    public static void addText(InputStream inputStream, OutputStream outputStream, String text, Position position, Color color, String fontName, double fontSizeScale, float alpha) {
        BufferedImage inputImage = ImgUtil.toImage(IoUtil.readBytes(inputStream));
        Double fontSize = inputImage.getHeight() * fontSizeScale;
        Font font = FontUtil.createFont(fontName, fontSize.intValue());
        int x;
        int y;
        if (position == Position.left_top) {
            x = fontSize.intValue();
            y = fontSize.intValue() * 2;
        } else if (position == Position.right_bottom) {
            x = inputImage.getWidth() - getTextWidth(font, text) - fontSize.intValue();
            y = inputImage.getHeight() - fontSize.intValue();
        } else {
            x = 0;
            y = 0;
        }
        if (color == null) {
            int argb = inputImage.getRGB(x, y);
            int[] rgbArr = intToArgb(argb);
            int a = 255 - rgbArr[0];
            int r = 255 - rgbArr[1];
            int g = 255 - rgbArr[2];
            int b = 255 - rgbArr[3];
            int newArgb = argbToInt(a, r, g, b);
            color = new Color(newArgb);
        }
        ImgUtil.writeJpg(Img.from(inputImage).setPositionBaseCentre(position == Position.center).pressText(text, color, font, x, y, alpha).getImg(), ImgUtil.getImageOutputStream(outputStream));
    }

    public static int argbToInt(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | (b << 0);
    }

    public static int[] intToArgb(int rgb) {
        int a = (rgb >> 24) & 0xFF;
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = (rgb >> 0) & 0xFF;
        return new int[]{a, r, g, b};
    }

    public static class MyX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    public static int getTextWidth(Font font, String text) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int width = 0;
        for (int i = 0; i < text.length(); i++) {
            width += metrics.charWidth(text.charAt(i));
        }
        return width;
    }

    public enum Position {

        center("center", "中间"),
        left_top("left_top", "左上"),
        right_bottom("right_bottom", "右下");

        private String value;
        private String name;

        Position(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

}
