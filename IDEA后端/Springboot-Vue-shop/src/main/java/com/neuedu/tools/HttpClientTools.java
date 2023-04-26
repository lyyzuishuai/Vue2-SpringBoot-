package com.neuedu.tools;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientTools {
    public static JSONObject getJSONObject(String url) throws IOException {
        //存放数据
        String data = "";

        //创建对接接口对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建Get请求
        HttpGet httpGet = new HttpGet(url);

        //设置头部信息为JSON格式
        httpGet.addHeader("Content-type", "application/json");


        // 通过请求对象获取响应对象
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // 获取结果实体
        // 判断网络连接状态码是否正常(0--200都是正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            data = EntityUtils.toString(response.getEntity(), "utf-8");
        }

        // 释放链接
        response.close();
        //将数据转化为JSON
        JSONObject jsonObject = JSONObject.parseObject(data);

        return jsonObject;
    }
}
