package com.example.horry.footbasket.utils;

import android.view.View;

import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.app.AppService;

/**
 * Created by Administrator on 2016/8/10.
 */
public class ResUtils {
    //将布局文件转化为View对象
    public static View inflate(int layoutId) {
        return View.inflate(App.getContext(), layoutId, null);
    }
}
