package com.zhuang.util.spring;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;


public class RestTemplateUtils {

    public static RestTemplate getRestTemplate() {
        return new RestTemplate(generateHttpsRequestFactory());
    }

    private static HttpComponentsClientHttpRequestFactory generateHttpsRequestFactory() {
        try {
            TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
            CloseableHttpClient httpClient = httpClientBuilder.build();
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setHttpClient(httpClient);
            factory.setConnectTimeout(30 * 1000);
            factory.setReadTimeout(10 * 60 * 1000);
            return factory;
        } catch (Exception e) {
            throw new RuntimeException("创建HttpsRestTemplate失败", e);
        }
    }
}
