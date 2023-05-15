package com.zhuang.util;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class ImageUtils {

    public static String getBase64ByUrl(String path) {
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
        return Base64.getEncoder().encodeToString(out.toByteArray());
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
