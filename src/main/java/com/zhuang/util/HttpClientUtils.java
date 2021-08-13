package com.zhuang.util;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {

    /**
     * get请求
     *
     * @return
     */
    public static String doGet(String url) {
        try {
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                //发送get请求
                HttpGet request = new HttpGet(url);
                try (CloseableHttpResponse response = httpclient.execute(request)) {
                    /**请求发送成功，并得到响应**/
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        /**读取服务器返回过来的json字符串数据**/
                        String strResult = EntityUtils.toString(response.getEntity());
                        return strResult;
                    } else {
                        return "fail status code -> " + response.getStatusLine().getStatusCode();
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
    public static String doPost(String url, Map params) {
        try {
            // 定义HttpClient
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                // 实例化HTTP方法
                HttpPost request = new HttpPost();
                request.setURI(new URI(url));
                //设置参数
                List<NameValuePair> nameValuePairList = new ArrayList<>();
                for (Iterator iter = params.keySet().iterator(); iter.hasNext(); ) {
                    String name = (String) iter.next();
                    String value = String.valueOf(params.get(name));
                    nameValuePairList.add(new BasicNameValuePair(name, value));
                }
                request.setEntity(new UrlEncodedFormEntity(nameValuePairList, Consts.UTF_8));
                try (CloseableHttpResponse response = httpclient.execute(request)) {
                    int code = response.getStatusLine().getStatusCode();
                    if (code == 200) {    //请求成功
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
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
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
