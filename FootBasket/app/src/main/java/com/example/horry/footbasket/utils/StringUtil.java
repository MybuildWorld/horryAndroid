package com.example.horry.footbasket.utils;

import com.example.horry.footbasket.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/12.
 */
public class StringUtil {
    public static final String key="League";
    public static final String AFTER="after=";
    public static final String PAGE="page=";
    public static final String AFTER1="after";
    public static final String PAGE1="page";
    public static Map<String ,String> map=new HashMap<String ,String>(){{
        put("转会","83.json");
        put("深度","55.json");
        put("中超","56.json");
        put("西甲","5.json");
        put("意甲","4.json");
        put("德甲","6.json");
        put("五洲","57.json");
        put("英超","3.json");
    }
    };
    public static final String URL="http://api.dongqiudi.com/app/tabs/android/";
    public static String subString(String s) {
        int i = s.indexOf(AFTER);
        int d = s.indexOf('&');
        int len=AFTER.length();
        return s.substring(i+len, d);
    }
    public static String lastChar(String s){
        return s.substring(s.length()-1);
    }
    public static String  getType(String s){
        return map.get(s);
    }

}
