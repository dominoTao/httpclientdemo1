package icu.bibilailai.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import icu.bibilailai.bean.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientDemo1 {
    public static void main(String[] args) {
        String getUrl = "http://localhost/get";
        String postUrl = "http://localhost/post";
        long start  = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
//            String getResp = getType(getUrl, new User());
            String postResp = postType(postUrl, new User());
//            System.out.println(getResp);
        }
        long end  = System.currentTimeMillis();
        System.out.println(end-start);
        for (int i = 0; i < 10770; i++) {
            String postResp = postType(postUrl, new User());
//            System.out.println(postResp);
//            一月 09, 2021 8:55:50 下午 org.apache.http.impl.execchain.RetryExec execute
//            信息: I/O exception (java.net.BindException) caught when processing request to {}->http://localhost:80: Address already in use: connect
//            一月 09, 2021 8:55:50 下午 org.apache.http.impl.execchain.RetryExec execute
        }
        long end1  = System.currentTimeMillis();
        System.out.println(end1-end);
    }

    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
            .setConnectionRequestTimeout(1000)
            .setSocketTimeout(1000)
            .setConnectTimeout(2000)
            .build();


    public static String postType(String url, Object obj){
        String respEntity = StringUtils.EMPTY;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(REQUEST_CONFIG);
        httpPost.setHeader("content-type", "application/json");
        try {
            if (!isJson(JSON.toJSONString(obj))) {
                return respEntity;
            }
            StringEntity stringEntity = new StringEntity(JSON.toJSONString(obj));
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = null;
            CloseableHttpClient httpClient = HttpClients.createDefault();
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                respEntity = EntityUtils.toString(entity);
            } else {
                response.close();
            }
//            entity.isStreaming()?entity.getContent(): new InputStream(new File("")) {
//                @Override
//                public int read() throws IOException {
//                    return 0;
//                }
//            };

//            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
            return respEntity;
        }
        return respEntity;
    }
    public static String getType(String url, Object obj) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(REQUEST_CONFIG);
        httpGet.setHeader("content-type", "application/json");
        String respEntity = StringUtils.EMPTY;
        try {
            if (!isJson(JSON.toJSONString(obj))) {
                return respEntity;
            }
            CloseableHttpResponse response = null;
            CloseableHttpClient httpClient = HttpClients.createDefault();
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                respEntity = EntityUtils.toString(entity);
            } else {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return respEntity;
        }
        return respEntity;
    }

    public static final boolean isJson(String str){
        try {
            JSONObject.parseObject(str);
        }catch (Exception e) {
            try {
                JSONObject.parseArray(str);
            }catch (Exception eq) {
                return false;
            }
        }
        return true;
    }
}
