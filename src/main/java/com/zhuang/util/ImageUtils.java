package com.zhuang.util;

import cn.hutool.core.img.FontUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.IoUtil;

import javax.net.ssl.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
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

    public static void addText(InputStream inputStream, OutputStream outputStream, String text) {
        addText(inputStream, outputStream, text, Color.green, "宋体", 0.045d, 1f);
    }

    public static void addText(InputStream inputStream, OutputStream outputStream, String text, Color color, String fontName, double fontSizeScale, float alpha) {
        BufferedImage inputImage = ImgUtil.toImage(IoUtil.readBytes(inputStream));
        double fontHeight = inputImage.getHeight() * fontSizeScale;
        Font sansSerifFont = FontUtil.createFont(fontName, (int) fontHeight);
        int x = (inputImage.getWidth() / 2) - Double.valueOf(fontHeight * 1.5).intValue();
        int y = (inputImage.getHeight() / 2) - Double.valueOf(fontHeight * 1.5).intValue();
        ImgUtil.pressText(inputImage, outputStream, text, color, sansSerifFont, -x, -y, alpha);
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

}
