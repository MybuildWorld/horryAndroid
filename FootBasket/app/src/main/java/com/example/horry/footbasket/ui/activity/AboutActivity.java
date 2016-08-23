package com.example.horry.footbasket.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.ui.widgets.RevealBackgroundView;
import com.example.horry.footbasket.ui.widgets.SwipeBackLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/17.
 */
public class AboutActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener {

    @BindView(R.id.swipebacklayout)
    SwipeBackLayout mSwipeBackLayout;
    @BindView(R.id.revealBackgroundView)
    RevealBackgroundView mRevealBackgroundView;
    @BindView(R.id.aboutView)
    View aboutView;
    public static void navigateFrom(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        mSwipeBackLayout.setCallBack(new SwipeBackLayout.CallBack() {
            @Override
            public void onFinish() {
                finish();
            }
        });
       setupRevealBackground();
   }

    private void setupRevealBackground() {
        mRevealBackgroundView.setOnStateChangeListener(this);
        int screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        final int[] startingLocation = {screenWidth,0};
        mRevealBackgroundView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mRevealBackgroundView.getViewTreeObserver().removeOnPreDrawListener(this);
                mRevealBackgroundView.startFromLocation(startingLocation);
                return true;
            }
        });
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            aboutView.setVisibility(View.VISIBLE);
        }else {
            aboutView.setVisibility(View.INVISIBLE);
        }
    }

}
