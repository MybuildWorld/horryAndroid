package com.example.horry.footbasket.utils;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.horry.footbasket.entity.Base;
import com.example.horry.footbasket.entity.nbaVideoItem;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/15.
 */
public class ParseUtil {
    public static nbaVideoItem parseNewsItem(String jsonStr) {
        nbaVideoItem newsItem = new nbaVideoItem();
        com.alibaba.fastjson.JSONObject data = JSON.parseObject(parseBase(newsItem, jsonStr)); // articleIds=    NullPoint
        List<nbaVideoItem.NewsItemBean> list = new ArrayList<nbaVideoItem.NewsItemBean>();
        //Set<String> keySet = data.keySet();
        for (Map.Entry<String, Object> item : data.entrySet()) {
            Gson gson = new Gson();
            try {
                nbaVideoItem.NewsItemBean bean = gson.fromJson(item.getValue().toString(), nbaVideoItem.NewsItemBean.class);
                bean.index = item.getKey();
                list.add(bean);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        // 由于fastjson获取出来的entrySet是乱序的  所以这边重新排序
        Collections.sort(list, new Comparator<nbaVideoItem.NewsItemBean>() {
            @Override
            public int compare(nbaVideoItem.NewsItemBean lhs, nbaVideoItem.NewsItemBean rhs) {
                return rhs.index.compareTo(lhs.index);
            }
        });
        newsItem.data = list;
        return newsItem;
    }
    public static String parseBase(Base base, String jsonStr) {
        com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(jsonStr);
        String data = "{}";
        for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
            if (entry.getKey().equals("code")) {
                base.code = Integer.parseInt(entry.getValue().toString());
            } else if (entry.getKey().equals("version")) {
                base.version = entry.getValue().toString();
            } else {
                data = entry.getValue().toString();
            }
        }
        Log.d("DATA",data);
        return data;
    }

}
