package com.example.horry.footbasket.app;

import android.app.Application;
import android.content.Context;

import com.thefinestartist.Base;

public class App extends Application {
    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppService.getInstance().initService();
        Base.initialize(this);
    }
    public static Context getContext() {
        return sInstance.getApplicationContext();
    }
}
