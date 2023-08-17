package com.ethereal.closeai.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class HttpPoolClientUtil {
    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig requestConfig;

    //httpClientConnectionManager post 线程池请求 applicationo/json
    public String doPost(String url, Map<String, String> headerMap, String cookie, String contentMap) {

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        //添加请求头
        Iterator<Map.Entry<String, String>> headIterator = headerMap.entrySet().iterator();
        while (headIterator.hasNext()) {
            Map.Entry<String, String> next = headIterator.next();
            httpPost.addHeader(next.getKey(), next.getValue());
        }
        //加入cookie
        if (StringUtils.isNotBlank(cookie)) {
            httpPost.setHeader("Cookie", cookie);
        }
        try {
            if (StringUtils.isNotBlank(contentMap)) {
                StringEntity entity = new StringEntity(contentMap, "utf-8");
                httpPost.setEntity(entity);
            }
            //执行返回结果
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                return result;
            }
            httpPost.abort();
        } catch (Exception e) {
            httpPost.abort();
            return "fail";
        }
        return null;
    }

    //httpClientConnectionManager post 线程池请求 application/x-www-form-urlencoded
    public String doPost(String url, Map<String, String> headerMap, String cookie, Map<String, String> contentMap) {

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        //添加请求头
        Iterator<Map.Entry<String, String>> headIterator = headerMap.entrySet().iterator();
        while (headIterator.hasNext()) {
            Map.Entry<String, String> next = headIterator.next();
            httpPost.addHeader(next.getKey(), next.getValue());
        }
        //加入cookie
        if (StringUtils.isNotBlank(cookie)) {
            httpPost.setHeader("Cookie", cookie);
        }
        //设置body
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
            //执行返回结果
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                return result;
            }
            httpPost.abort();
        } catch (Exception e) {
            httpPost.abort();
            return "fail";
        }
        return null;
    }

    //文件上传请求
    public String uploadFile(MultipartFile file, String url) {
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setConfig(requestConfig);

            // 创建待处理的文件
            String fileName = file.getOriginalFilename();
            ContentBody files = new ByteArrayBody(file.getBytes(), fileName);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("file", files);
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null && response.getStatusLine().getStatusCode() == 200) {
                // 将响应内容转换为字符串
                String result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
                return result;
            }
            httpPost.abort();
        } catch (Exception e) {
            httpPost.abort();
        }
        return null;
    }
}