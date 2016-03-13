package com.example.xdl.floatwindow;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.pieview.CircleView;
import com.android.pieview.PieItem;

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
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public bigFloatWindowView(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.circleview_layout, this);
        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.circleview);
        //添加CiecleView
        CircleView circleView= new CircleView(context);
//        circleView.setCenter(viewWidth,viewHeight);
        circleView.setCenter(240,240);
        circleView.setLayoutParams(relativeLayout.getLayoutParams());
        circleView.addItem(circleView.makeItem(R.drawable.item_camara, 0));
        circleView.addItem(circleView.makeItem(R.drawable.item_with, 0));
        circleView.addItem(circleView.makeItem(R.drawable.item_message, 0));
        circleView.addItem(circleView.makeItem(R.drawable.items_call, 0));
        circleView.addItem(circleView.makeItem(R.drawable.items_picture,0));
        circleView.addItem(circleView.makeItem(R.drawable.item_settings,0));
        circleView.setOnItemClickListener(new CircleView.OnItemClickListener() {
            @Override
            public void onItemClick(PieItem item) {
                System.out.println("hello");
            }
        });
        relativeLayout.addView(circleView);

        viewWidth = relativeLayout.getLayoutParams().width;
        viewHeight = relativeLayout.getLayoutParams().height;
        }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果点击在界面外，创建小窗口，关闭大窗口
        int xInscreen= (int) event.getRawX();
        int yInscreen= (int) event.getRawY();
        if(!isTouchInView(this,xInscreen,yInscreen)) {
            MyWindowManager.removeBigWindow(getContext());
            MyWindowManager.createSmallWindow(getContext());
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
