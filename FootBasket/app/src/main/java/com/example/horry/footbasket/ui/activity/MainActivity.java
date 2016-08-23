package com.example.horry.footbasket.ui.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.events.DrawerClickEvent;
import com.example.horry.footbasket.ui.Fragment.BasketBallFragment;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.BlogFragment;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.NewsFragment;
import com.example.horry.footbasket.ui.Fragment.BaseFragment;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.playerSort.StatisticFragment;
import com.example.horry.footbasket.ui.Fragment.StatTabFragment;
import com.example.horry.footbasket.ui.Fragment.fbFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


public class MainActivity extends BaseActivity{
    @BindView(R.id.group)
    RadioGroup group;
    @BindView(R.id.fb)
    RadioButton fbModel;
    @BindView(R.id.basket)
    RadioButton basketModel;
    private static  long DOUBLE_CLICK_TIME=0;
    private BaseFragment mCurrentFragment;
    private String mCurrentDrawName;
    private Map<String,BaseFragment> mBaseFragmentByName= new HashMap<>();
    private Map<Integer,String> mFragmentNameByRbId = new HashMap<>();
    private static final int[] name_id = {R.string.News, R.string.blog,R.string.match,R.string.statictis, R.string.teamsort};
    public static String key="TAB";
    @Override
    protected void onStart() {
        super.onStart();
//        group.check(R.id.fb);
    }
    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        //抽屉布局
        initDrawerMap();
        initToolbar();
        mCurrentFragment= fbFragment.newInstance();
        transactionSupportFragment(mCurrentFragment);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int type = 0;
                switch (checkedId) {
                    case R.id.fb:
                        type = R.string.FootBall;
                        break;
                    case R.id.basket:
                        type = R.string.BasketBall;
                        break;
                    case R.id.stat:
                        type=R.string.Stat;
                        break;
                    default:
                        break;
                }
                mCurrentFragment = getFragment(mFragmentNameByRbId.get(type));
                transactionSupportFragment(mCurrentFragment);
            }
        });
    }
    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
    }
    private void initDrawerMap() {
        mFragmentNameByRbId.put(R.string.FootBall, fbFragment.class.getName());
        mFragmentNameByRbId.put(R.string.BasketBall, BasketBallFragment.class.getName());
        mFragmentNameByRbId.put(R.string.Stat, StatTabFragment.class.getName());
    }

    private BaseFragment getFragment(String fragmentName) {
        BaseFragment baseFragment = mBaseFragmentByName.get(fragmentName);
        if(mBaseFragmentByName.get(fragmentName)==null) {
            try {
                baseFragment=(BaseFragment)Class.forName(fragmentName).newInstance();
            } catch (Exception e) {
                baseFragment=NewsFragment.newInstance();
            }
            mBaseFragmentByName.put(fragmentName,baseFragment);
        }
        return baseFragment;
    }

    private void transactionSupportFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DrawerClickEvent drawerClickEvent) {
        if(constant.Result.FAIL.equals(drawerClickEvent.getEventResult())||drawerClickEvent.getDrawItemName()==mCurrentDrawName) {
            return;
        }
        mCurrentDrawName=drawerClickEvent.getDrawItemName();
        if(mCurrentDrawName==getResources().getString(R.string.statictis)) {
            mCurrentFragment= new StatisticFragment().newInstance();
        }else {
            mCurrentFragment = getFragment(mCurrentDrawName);
        }
        transactionSupportFragment(mCurrentFragment);
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.about:
//                AboutActivity.navigateFrom(MainActivity.this);
//                break;
//            case R.id.setting:
//                setttingActivity.navigateFrom(MainActivity.this);
//                break;
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    //处理Back键
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            DOUBLE_CLICK_TIME = System.currentTimeMillis();
        } else {
            if (JCVideoPlayer.backPress()) {
                finish();
                return;
            }
            finish();
            super.onBackPressed();

        }
    }

}
