package com.example.horry.footbasket.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.NewsDetail;
import com.example.horry.footbasket.events.NewsDetailEvent;
import com.example.horry.footbasket.events.NewsDtailEvent;
import com.example.horry.footbasket.utils.AppUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/16.
 */
public class NewsDetailActivity extends SwipeBackActivity {
    @BindView(R.id.webLayout)
    FrameLayout mWebLayout;
    WebView mWebView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Intent mGetIntent;
    private ImageView mTitleImage;
    public static final String TITLE ="TITLE";
    public static final String DETILE_DATE="DETILE_DATE";
    public static final String DETILE_ID="DETILE_ID";
    public static final String IMAGE_URL="IMAGE_URL";
    public static final String IMAGE_EXIST="IMAGE_EXIST";


    @Override
    protected int getContentViewId() {
        return hasTitleImage()? R.layout.activity_detail : R.layout.activity_detail_nopic;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NewsDtailEvent event) {
        if(event!=null) {
            if(constant.Result.FAIL.equals(event.getEventResult())) {
                AppUtil.showSnackBar(mSwipeBackLayout, R.string.load_fail);
                return;
            }
            mWebView.loadDataWithBaseURL(null,event.getContent(), "text/html", "UTF-8", null);
        }
    }


    private boolean hasTitleImage() {
        return getIntent().getBooleanExtra(IMAGE_EXIST, false);
    }

    @Override
    void setTitle() {
        mToolBar.setTitleTextColor(Color.BLACK);
        mToolBar.setTitle(R.string.app_name);
    }
    @Override
    protected void initViews() {
        super.initViews();
        mGetIntent=getIntent();
        if(hasTitleImage()) {

            mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
            mCollapsingToolbarLayout.setTitle(mGetIntent.getStringExtra(TITLE));
            mTitleImage = (ImageView)findViewById(R.id.titleImage);
            mTitleImage.post(new Runnable() {
                @Override
                public void run() {
                    Glide.with(NewsDetailActivity.this).load(mGetIntent.getStringExtra(IMAGE_URL))
                            .placeholder(R.color.colorPrimary)
                            .centerCrop()
                            .into(mTitleImage);
                }
            });

        } else {
            mToolBar.setBackgroundResource(R.color.colorPrimary);

        }
        mWebView = new WebView(getApplicationContext());
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setBackgroundColor(0);
        mWebLayout.addView(mWebView);

        getNewsDetail();
    }

    private void getNewsDetail(){
        AppService.getInstance().getNewsDetail(getTaskId(), mGetIntent.getStringExtra(DETILE_DATE), mGetIntent.getStringExtra(DETILE_ID));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWebLayout!=null) {
            mWebLayout.removeAllViews();
            mWebView.destroy();
        }
    }
}
