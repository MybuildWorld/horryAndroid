package com.example.xdl.floatwindow;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class FloatWindowServices extends Service {


public MyWindowManager myWindowManager;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       //创建悬浮窗口
        myWindowManager=new MyWindowManager();
        if (!myWindowManager.isWindowShowing())
             myWindowManager.createSmallWindow(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }
    public MyWindowManager getManager(){
        return myWindowManager;
    }
}
