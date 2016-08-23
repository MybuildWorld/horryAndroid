package com.example.horry.footbasket.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.horry.footbasket.app.AppService;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {
    protected abstract void initViews();
    protected abstract int getContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        AppService.getInstance().addCompositeSub(getTaskId());
        AppService.getsBus().register(this);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppService.getInstance().removeCompositeSub(getTaskId());
        AppService.getsBus().unregister(this);
    }
}




