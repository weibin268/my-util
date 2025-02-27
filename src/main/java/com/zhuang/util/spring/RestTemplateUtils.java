package com.zhuang.util.spring;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;

/**
 * 注意：RestTemplate内部是但线程实现，所以每次请求都需要new新的，或使用异步的AsyncRestTemplate
 */
public class RestTemplateUtils {

    public static RestTemplate getRestTemplate() {
        // 注意：这里的MyRestTemplate是线程不安全的
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
        public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
            try {
                return super.getForObject(url, responseType, uriVariables);
            } finally {
                destroy();
            }
        }

        @Override
        public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables)
                throws RestClientException {
            try {
                return super.getForEntity(url, responseType, uriVariables);
            } finally {
                destroy();
            }
        }


        @Override
        public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables)
                throws RestClientException {
            try {
                return super.postForEntity(url, request, responseType, uriVariables);
            } finally {
                destroy();
            }
        }

        @Override
        public <T> ResponseEntity<T> exchange(String url, HttpMethod method,
                                              HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {

            try {
                return super.exchange(url, method, requestEntity, responseType, uriVariables);
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
