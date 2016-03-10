package com.example.xdl.floatwindow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * Created by XDL on 2016-02-03.
 */
public class smallFloatWindowView extends LinearLayout {
    //状态栏的高度
     private float statusBarHeight;
    //记录悬浮窗的高度
      public static float viewHeight;

    //记录悬浮窗的宽度
      public static  float viewWidth;

    //手指按下时在悬浮View上横坐标的值
      private float xInView;

    //手指按下时在悬浮View上纵坐标的值
      private float yInView;

    //手指按下时屏幕的横坐标
      private float xDownInScreenView;

    //手指按下时屏幕的纵坐标
      private float yDownInScreenView;

    //当前手指的横坐标
      private float xInScreen;

    //当前手指的纵坐标
      private float yInScreen;

    //记录悬浮窗的参数
     static WindowManager.LayoutParams params;
    //用于更新悬浮窗的位置
    WindowManager windowManager;

    public smallFloatWindowView(final Context context) {
        super(context);
        windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_small_window,this);
        View view=findViewById(R.id.small_float_layout);
        viewHeight=view.getLayoutParams().height;
        viewWidth=view.getLayoutParams().width;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                 //手指按下记录坐标数据，纵坐标都要减去状态栏的高度
                xInView=event.getX();
                yInView=event.getY();
                xDownInScreenView=event.getRawX();
                yDownInScreenView=event.getRawY()-getStatusbarHeight();
                xInScreen=event.getRawX();
                yInScreen=event.getRawY()-getStatusbarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen=event.getRawX();
                yInScreen=event.getRawY()-getStatusbarHeight();
                //改变悬浮窗口的位置
                updateWindowPosition();
                break;
            case MotionEvent.ACTION_UP:
                //如果手指离开时xDownInScreenView=xInScreen和yDownInScreenView=yInscreen,则触发单击事件
                if(xDownInScreenView==xInScreen&&yDownInScreenView==yInScreen){
                    openBigWindow();
                }
                break;

            default:
                break;
        }

        return true;
    }

    private float getStatusbarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }
    //打开大窗口，关闭小窗口
    public void  openBigWindow(){
         MyWindowManager.createBigWindow(getContext());
         MyWindowManager.removeSmallWindow(getContext());
    }
   //移动悬浮窗
    public void updateWindowPosition(){
        params.x= (int) (xInScreen-xInView);
        params.y= (int) (yInScreen-yInView);
        windowManager.updateViewLayout(this,params);
}
    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     *
}        * @param params
     *            小悬浮窗的参数
     */
        public void setParams(WindowManager.LayoutParams params) {
        this.params = params;
    }
}