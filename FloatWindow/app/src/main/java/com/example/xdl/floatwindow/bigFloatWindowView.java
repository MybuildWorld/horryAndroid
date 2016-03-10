package com.example.xdl.floatwindow;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by XDL on 2016-02-03.
 */
public class bigFloatWindowView extends LinearLayout {
    /**
     * 记录大悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录大悬浮窗的高度
     */
    public static int viewHeight;
    private Button btnCamera;
    private Button btnMessages;
    public bigFloatWindowView(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.float_big_window, this);
        View view = findViewById(R.id.big_float_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        btnCamera= (Button) findViewById(R.id.btn_camera);
        btnMessages= (Button) findViewById(R.id.btn_thought);
        OnClickListener onClickListener=new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_camera:
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    case R.id.btn_thought:
                        Intent intent1 = new Intent("/");
                        ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.DeviceInfoSettings");
                        intent1.setComponent(cn);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                }
            }

        };
        btnMessages.setOnClickListener(onClickListener);
        btnCamera.setOnClickListener(onClickListener);
        }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果点击在界面外，创建小窗口，关闭大窗口
        int xInscreen= (int) event.getRawX();
        int yInscreen= (int) event.getRawY();
        if(!isTouchInView(this,xInscreen,yInscreen)) {
            MyWindowManager.createSmallWindow(getContext());
            MyWindowManager.removeBigWindow(getContext());
        }
        return true;
    }

    //判断是否单击在大窗口中
    public boolean isTouchInView(View view,int x,int y){
        int location[]=new int [2];
        view.getLocationOnScreen(location);
        int left=location[0];
        int top=location[1];
        int right=location[0]+view.getMeasuredWidth();
        int bottom=location[1]+view.getMeasuredHeight();
        if( y >= top && y <= bottom && x >= left && x <= right){
            return true;
        }
       return false;
    }
}
