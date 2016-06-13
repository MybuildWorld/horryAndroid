package com.example.horryxiao.zhihu.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.horryxiao.zhihu.R;

/**
 * Created by Administrator on 2016/6/6.
 */
public class Util {
    public static final String theme_reId="swap";
    public static final String SharePreference_name="com.example.horryxiao.zhihu.perfer_key";
    public static final int default_theme= R.style.AppTheme;
    public static final String isNight="isNight";
    public  static void swapTheme(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SharePreference_name,Context.MODE_PRIVATE);
        boolean isNightMode=sharedPreferences.getBoolean(isNight,false);
        if(isNightMode)
            context.setTheme(R.style.nightTheme);
        else
             context.setTheme(R.style.AppTheme);
    }

}
