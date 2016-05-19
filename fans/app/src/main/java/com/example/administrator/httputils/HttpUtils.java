package com.example.administrator.httputils;

import com.example.administrator.bean.ChatMessage;
import com.example.administrator.bean.Result;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/1
 */

public class HttpUtils  {

    private final static String Api="http://www.tuling123.com/openapi/api";
    private final static String APIKEY="877c6566a65cfd6ddd9e50313f070079";

    public static ChatMessage sendMessage(String msg){
        String jsonData=doGet(msg);
        Gson gson=new Gson();
        Result result=gson.fromJson(jsonData,Result.class);
        ChatMessage chatMessege=new ChatMessage();
        chatMessege.setDate(new Date());
        chatMessege.setMsg(result.getText());
        chatMessege.setType(ChatMessage.Type.Incoming);
        return chatMessege;
    }

    public static String doGet(String msg){
        StringBuilder stringBuilder=new StringBuilder();
        try {
            URL url=new URL(setUrl(msg));
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in=connection.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            String line;
            while((line=br.readLine())!=null){
                stringBuilder.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    public static String setUrl(String msg){
        String  url="";
        try {
            url=Api+"?key="+APIKEY+"&info="+ URLEncoder.encode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  url;
    }
}
