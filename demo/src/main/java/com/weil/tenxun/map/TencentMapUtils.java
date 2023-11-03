package com.weil.tenxun.map;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * @Name: TencentMapUtils
 * @Description: 腾讯地图工具类
 * @Author: weil
 * @Date: 2023-11-01 16:48
 * @Version: 1.0
 */
public class TencentMapUtils {

    // 统一访问工具
    public static String getTencentMap(String uri, JSONObject req){
        // 组装url地址
        String urlStr = getUrlByObj(uri, req);
        // 请求的url
        URL url = null;
        // 请求的输入流
        BufferedReader in = null;
        // 输入流的缓冲
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String str = null;
            //一行一行进行读入
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ex) {
            System.out.println("读取出现异常...");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                System.out.println("关闭资源出现异常");
            }finally {
                return sb.toString();
            }
        }
    }

    /**
     * 对象转请求参数拼接str
     */
    public static String getUrlByObj(String uri, JSONObject obj){
        StringBuilder sb = new StringBuilder(uri);
        Set<Map.Entry<String, Object>> entries = obj.entrySet();
        if(entries.size()>0){
            sb.append("?");
            for (Map.Entry<String, Object> entry : entries) {
                if(entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())){
                    sb.append(entry.getKey())
                            .append("=")
                            .append(entry.getValue())
                            .append("&");
                }
            }
            // 移除最后一个&
            sb.deleteCharAt(sb.length()-1);
        }
        System.out.println("url:" + sb.toString());
        return sb.toString();
    }
}
