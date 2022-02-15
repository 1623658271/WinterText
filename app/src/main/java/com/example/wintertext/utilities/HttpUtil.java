package com.example.wintertext.utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * description ： TODO:网络请求工具类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/9 16:47
 */
//发送网络请求的工具类
public class HttpUtil {
    public static String sendHttpRequest(String address){
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = null;
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            response = new StringBuilder();
            String line;
            while((line = reader.readLine())!=null){
                response.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return response.toString();
    }
    public static String sendHttpPostRequest(String address,String data){
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = null;
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(data);
            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            response = new StringBuilder();
            String line;
            while ((line = reader.readLine())!=null){
                response.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return response.toString();
    }
}
