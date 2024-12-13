package com.zhuang.util.spring;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;

/**
 * 注意：RestTemplate内部是但线程实现，所以每次请求都需要new新的，或使用异步的AsyncRestTemplate
 */
public class RestTemplateUtils {

    public static RestTemplate getRestTemplate() {
        return new MyRestTemplate(getHttpsRequestFactory());
    }

    public static void setStringHttpMessageConverterAsUtf8(RestTemplate restTemplate) {
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    private static HttpComponentsClientHttpRequestFactory getHttpsRequestFactory() {
        try {
            TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
            SSLContextBuilder sslContextBuilder = SSLContexts.custom();
            SSLContext sslContext = sslContextBuilder
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
            SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            CloseableHttpClient httpClient = httpClientBuilder
                    .setSSLSocketFactory(connectionSocketFactory)
                    .build();
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setHttpClient(httpClient);
            factory.setConnectTimeout(30 * 1000);
            factory.setReadTimeout(10 * 60 * 1000);
            return factory;
        } catch (Exception e) {
            throw new RuntimeException("getHttpsRequestFactory fail!", e);
        }
    }

    public static class MyRestTemplate extends RestTemplate {

        public MyRestTemplate(ClientHttpRequestFactory requestFactory) {
            super(requestFactory);
        }

        @Override
        @Nullable
        public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
            try {
                return super.getForObject(url, responseType, uriVariables);
            } finally {
                destroy();
            }
        }

        public void destroy() {
            try {
                ((HttpComponentsClientHttpRequestFactory) this.getRequestFactory()).destroy();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
