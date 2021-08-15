package com.zhuang.util;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * http 请求工具（可以选择使用Hutool的HttpUtil来替代该类功能）
 */
public class HttpClientUtils {

    /**
     * get请求
     *
     * @return
     */
    public static String doGet(String url) {
        try {
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(url);
                try (CloseableHttpResponse response = httpclient.execute(request)) {
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        String strResult = EntityUtils.toString(response.getEntity());
                        return strResult;
                    } else {
                        return getFailResult(response);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * post请求(用于key-value格式的参数)
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map<String, Object> params) {
        try {
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpPost request = new HttpPost();
                request.setURI(new URI(url));
                List<NameValuePair> nameValuePairList = new ArrayList<>();
                for (Iterator iter = params.keySet().iterator(); iter.hasNext(); ) {
                    String name = (String) iter.next();
                    String value = String.valueOf(params.get(name));
                    nameValuePairList.add(new BasicNameValuePair(name, value));
                }
                request.setEntity(new UrlEncodedFormEntity(nameValuePairList, Consts.UTF_8));
                try (CloseableHttpResponse response = httpclient.execute(request)) {
                    int code = response.getStatusLine().getStatusCode();
                    if (code == 200) {
                        BufferedReader in = null;
                        in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), Consts.UTF_8));
                        StringBuffer sb = new StringBuffer("");
                        String line = "";
                        String NL = System.getProperty("line.separator");
                        while ((line = in.readLine()) != null) {
                            sb.append(line + NL);
                        }
                        in.close();
                        return sb.toString();
                    } else {
                        return getFailResult(response);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * post请求（用于请求json格式的参数）
     *
     * @param url
     * @param json
     * @return
     */
    public static String doPostJson(String url, String json) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(json, charSet);
        httpPost.setEntity(entity);
        try {
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                StatusLine status = response.getStatusLine();
                int state = status.getStatusCode();
                if (state == HttpStatus.SC_OK) {
                    HttpEntity responseEntity = response.getEntity();
                    String jsonString = EntityUtils.toString(responseEntity);
                    return jsonString;
                } else {
                    return getFailResult(response);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFailResult(HttpResponse response) {
        return "fail status code -> " + response.getStatusLine().getStatusCode();
    }
}
