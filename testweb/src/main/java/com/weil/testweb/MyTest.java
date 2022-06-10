package com.weil.testweb;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName MyTest
 * @Author weil
 * @Description //爬虫爬试题和答案
 * @Date 2021/7/16 13:43
 * @Version 1.0.0
 **/
public class MyTest {
    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <=29; i++) {
            v2(v1(i, sb), sb);
            System.out.println();
            sb.append("\r\n");
        }
        File file = new File("d:/问答题.txt");
        OutputStream outputStream = new FileOutputStream(file, true);
        outputStream.write(sb.toString().getBytes(StandardCharsets.UTF_8));

    }

    /**
     * 单选、多选题、判断题目
     */
    public static String v1(int i, StringBuilder sb2) throws Exception {
        URL url = new URL("http://wts.shipinginfo.com:8988/websubject/PubRandomSubject.do");
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setRequestMethod("GET");
//        httpUrlConnection.setRequestProperty("index","1");
//        httpUrlConnection.setRequestProperty("testid","e1d8ed2f6ea84129a876d4661d138e02");
        httpUrlConnection.setRequestProperty("Host", "wts.shipinginfo.com:8988");
        httpUrlConnection.setRequestProperty("Connection", "keep-alive");
        httpUrlConnection.setRequestProperty("Cache-Control", "max-age=0");
        httpUrlConnection.setRequestProperty("Upgrade-Insecure-Requests", " 1");
        httpUrlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36");
        httpUrlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpUrlConnection.setRequestProperty("Referer", "http://wts.shipinginfo.com:8988/websubject/PubRandomSubject.do?paperid=402a974b78060bf101780661c5860292&roomid=402a974b78060bf10178066d481a05b4");
        httpUrlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
        httpUrlConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
        httpUrlConnection.setRequestProperty("Cookie", "UM_distinctid=17be73c4c9d1e3-097cc43657d0fd-c343365-144000-17be73c4c9e1059; JSESSIONID=66BECD32844A489241889F3259383132");
        OutputStream outputStream = httpUrlConnection.getOutputStream();

        outputStream.write(("?index="+i+"&testid=55bdbc0d28c841569ab834e13aeda053").getBytes(StandardCharsets.UTF_8));
        InputStream inputStream = null;
        BufferedReader reader = null;
        String substring = null;
        try {
            if (httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpUrlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer sb = new StringBuffer();
                String strRead = null;
                while ((strRead = reader.readLine()) != null) {
                    sb.append(strRead);
                }
                String s = sb.toString();
                String s1 = "<div class=\"subjectOrder\">1</div>";
                String s2 = "<span class=\"subjectPoint\">本题1分</span>";
                String s3 = "<div class=\"subjectOrder\">1</div>\t\t<div>\t\t\t\t\t\t\t<div>";
                String s4 = "var versionId = ";

                int a = s.indexOf(s1);
                int j = s.indexOf(s2);
                String x = "问题" + i + "：" + s.substring(a + s3.length(), j);
                System.out.println(x);
                sb2.append(x);
                sb2.append("\r\n");
                int i1 = s.indexOf(s4);
                substring = s.substring(i1 + s4.length() + 1, i1 + s4.length() + 33);
//                System.out.println(substring);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        return substring;
    }
    /**
     * 问答题答案
     */
    public static void v2(String id, StringBuilder sb2) throws Exception {
        URL url = new URL("http://wts.shipinginfo.com:8988/websubject/PubAnalysis.do");
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setRequestMethod("POST");
//        httpUrlConnection.setRequestProperty("index","1");
//        httpUrlConnection.setRequestProperty("testid","e1d8ed2f6ea84129a876d4661d138e02");
        httpUrlConnection.setRequestProperty("Host", "wts.shipinginfo.com:8988");
        httpUrlConnection.setRequestProperty("Connection", "keep-alive");
        httpUrlConnection.setRequestProperty("Content-Length", "42");
        httpUrlConnection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        httpUrlConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        httpUrlConnection.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36");
        httpUrlConnection.setRequestProperty("Content-Type", " application/x-www-form-urlencoded; charset=UTF-8");
        httpUrlConnection.setRequestProperty("Origin", "http://wts.shipinginfo.com:8988");
        httpUrlConnection.setRequestProperty("Referer", "http://wts.shipinginfo.com:8988/websubject/PubRandomSubject.do?index=2&testid=55bdbc0d28c841569ab834e13aeda053");
        httpUrlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
        httpUrlConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
        httpUrlConnection.setRequestProperty("Cookie", "UM_distinctid=17be73c4c9d1e3-097cc43657d0fd-c343365-144000-17be73c4c9e1059; JSESSIONID=66BECD32844A489241889F3259383132");
        OutputStream outputStream = httpUrlConnection.getOutputStream();

        outputStream.write(("versionId="+id).getBytes(StandardCharsets.UTF_8));
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            if (httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpUrlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer sb = new StringBuffer();
                String strRead = null;
                while ((strRead = reader.readLine()) != null) {
                    sb.append(strRead);
                }
                String s = sb.toString();
                JSONObject object = JSONObject.parseObject(s);
                JSONArray answers = (JSONArray) object.get("analyses");
                System.out.println("答案：");
                sb2.append("答案：");
                for (Object answer : answers) {
                    JSONObject obj = (JSONObject) answer;
                    Object answer2 = obj.get("text");
                    System.out.println(answer2);
                    sb2.append(answer2);
                    sb2.append("\r\n");
                }

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }
    /**
     * 单选、多选题、判断 答案
     */
    public static void v3(String id, StringBuilder sb2) throws Exception {
        URL url = new URL("http://wts.shipinginfo.com:8988/websubject/PubAnalysis.do");
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setRequestMethod("POST");
//        httpUrlConnection.setRequestProperty("index","1");
//        httpUrlConnection.setRequestProperty("testid","e1d8ed2f6ea84129a876d4661d138e02");
        httpUrlConnection.setRequestProperty("Host", "wts.shipinginfo.com:8988");
        httpUrlConnection.setRequestProperty("Connection", "keep-alive");
        httpUrlConnection.setRequestProperty("Content-Length", "42");
        httpUrlConnection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        httpUrlConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        httpUrlConnection.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36");
        httpUrlConnection.setRequestProperty("Content-Type", " application/x-www-form-urlencoded; charset=UTF-8");
        httpUrlConnection.setRequestProperty("Origin", "http://wts.shipinginfo.com:8988");
        httpUrlConnection.setRequestProperty("Referer", "http://wts.shipinginfo.com:8988/websubject/PubRandomSubject.do?index=3&testid=c8f3eaf799f344eb9622d7dfd5240095");
        httpUrlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
        httpUrlConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
        httpUrlConnection.setRequestProperty("Cookie", "UM_distinctid=179e48105c0e4d-0387e5ac06206-2363163-1fa400-179e48105c1e7a; JSESSIONID=16B526AAD51D3AC1273FE1080F85FC98");
        OutputStream outputStream = httpUrlConnection.getOutputStream();

        outputStream.write(("versionId="+id).getBytes(StandardCharsets.UTF_8));
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            if (httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpUrlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer sb = new StringBuffer();
                String strRead = null;
                while ((strRead = reader.readLine()) != null) {
                    sb.append(strRead);
                }
                String s = sb.toString();
                JSONObject object = JSONObject.parseObject(s);
                JSONArray answers = (JSONArray) object.get("answers");
                System.out.println("答案：");
                sb2.append("答案：");
                for (Object answer : answers) {
                    JSONObject obj = (JSONObject) answer;
                    JSONObject answer1 = (JSONObject) obj.get("answer");
                    Object answer2 = answer1.get("answer");
                    System.out.println(answer2);
                    sb2.append(answer2);
                    sb2.append("\r\n");
                }
//                System.out.println("答案："+s.substring(s1+str.length()+5, s2-3));

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }
}
