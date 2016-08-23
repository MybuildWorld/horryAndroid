package com.example.horry.footbasket.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.entity.Base;
import com.example.horry.footbasket.ui.widgets.SwipeBackLayout;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/16.
 */
public abstract class SwipeBackActivity extends BaseActivity {
    @BindView(R.id.swipBackLayout)
    SwipeBackLayout mSwipeBackLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;


    abstract void setTitle();

    @Override
    protected void initViews() {
        initToolBar();
        mSwipeBackLayout.setCallBack(new SwipeBackLayout.CallBack() {
            @Override
            public void onFinish() {
                finish();
            }
        });
    }

    protected void initToolBar() {
        setTitle();
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
