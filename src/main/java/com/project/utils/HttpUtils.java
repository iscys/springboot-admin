package com.project.utils;

import com.project.model.JiaoXiaoApiModel;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    /**
     * http请求连接超时时间.
     */
    public  int connectionTimeout =4000;
    /**
     * http请求数据读取等待时间.
     */
    private int httpTimeout = 5000;



    private HttpUtils(){}

    public static HttpUtils INSTANCE = new HttpUtils();

    /**
     * 发送Get请求
     * @param url
     * @return
     * -1 是错误码
     * @throws Exception
     */
    public String doGet(String url) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget =new HttpGet(url);
        try {
            CloseableHttpResponse response = httpclient.execute(httpget);
            String result = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            return result;
        }catch (Exception e){
            return "-1";
        }finally {
            httpget.releaseConnection();;
        }

    }

    /**
     * post请求
     * @param url
     * @param param post参数是json字符串
     * @return
     * @throws Exception
     */
    public String doPost(String url,String param){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost =createHttpPost(url);
        try {
            httppost.addHeader("accept", "*/*");
            httppost.addHeader("connection", "Keep-Alive");
            httppost.addHeader("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.setEntity(new StringEntity(param, "UTF-8"));
            CloseableHttpResponse resp = httpclient.execute(httppost);
            String result = Utf8ResponseHandler.INSTANCE.handleResponse(resp);
            return result;
        }catch (Exception e){
            return "-1";
        }finally {
            httppost.releaseConnection();
        }


    }


    /**
     * post请求
     * @param url
     * @param param post参数是form表单的形式
     * @return
     * @throws Exception
     */
    public String doPost(String url, HashMap<String,String> param) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost =createHttpPost(url);
        //httppost.set
        try {
            //httppost.addHeader("accept", "*/*");
            //httppost.addHeader("connection", "Keep-Alive");
            //httppost.addHeader("user-agent",
             //       "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
           // httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");

            List<BasicNameValuePair> pair = new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                pair.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httppost.setEntity(new UrlEncodedFormEntity(pair));
            CloseableHttpResponse response = httpclient.execute(httppost);
            String result = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            return result;
        }catch (Exception e){
            return "-1";
        }finally {
            httppost.releaseConnection();
        }

    }

    public static void main(String[] args)throws Exception {
        String s = HttpUtils.INSTANCE.doGet("http://api.avatardata.cn/Jztk/Query?key=d1c4e14cf0ef47b1b40284f46be9cd99&model=c1&subject=1&format=true&testType=order");
        JiaoXiaoApiModel apiResult = GsonUtils.fromJson(s, JiaoXiaoApiModel.class);
        System.out.println(apiResult.getResult().size());

    }

    public HttpPost createHttpPost(String url){
        HttpPost httpPost=new HttpPost(url);
        httpPost.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(connectionTimeout)
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(httpTimeout)
                .build());
        return httpPost;
    }

}
