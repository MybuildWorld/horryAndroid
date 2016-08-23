package com.example.horry.footbasket.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.events.event;
import com.example.horry.footbasket.ui.Fragment.FragmentAdapter.SortAdapter;
import com.example.horry.footbasket.utils.StringUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/16.
 */
public class AssitActivity extends BaseActivity{
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager pager;


    private String type;
    @Override
    protected void initViews() {
        type=getIntent().getStringExtra(StringUtil.key);
        initToolbar();   //初始化Toolbar
        initViewPager(); //初始化ViewPager
        initTabLayout(); //初始化TabLayout
    }

    @Override
    protected int getContentViewId() {
        return R.layout.assit_layout;
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
//        toolbar.setTitle(type);
//        toolbar.setTitleTextColor(App.getContext().getResources().getColor(R.color.white));
//        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
//        setSupportActionBar(toolbar);
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        pager.setAdapter(new SortAdapter(getSupportFragmentManager(), type));
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(pager);
    }

    /**
     * 监听Toolbar的点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: //侧边栏
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventMain(event event){

    }
}
