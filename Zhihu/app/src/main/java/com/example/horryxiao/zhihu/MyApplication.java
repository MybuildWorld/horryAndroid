package com.example.horryxiao.zhihu;

import android.app.Application;

import com.example.horryxiao.zhihu.Util.FontsUtil;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsUtil.setFonts(this,"DEFAULT","splash.ttf");
        FontsUtil.setFonts(this,"MONOSPACE","splash.ttf");
        FontsUtil.setFonts(this,"SERIF","splash.ttf");
        FontsUtil.setFonts(this,"SANS_SERIF","splash.ttf");
    }
}
