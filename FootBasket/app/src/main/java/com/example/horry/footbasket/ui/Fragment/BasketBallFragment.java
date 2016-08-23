package com.example.horry.footbasket.ui.Fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.events.DrawerClickEvent;
import com.example.horry.footbasket.ui.Fragment.FragmentAdapter.BasketPagerAdapter;
import com.example.horry.footbasket.ui.Fragment.FragmentAdapter.FbPagerAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by Administrator on 2016/8/12.
 */
public class BasketBallFragment extends BaseFragment {
    @BindView(R.id.tabs)
    protected TabLayout tabLayout;
    @BindView(R.id.vp_content)
    protected ViewPager pager;
    List<Fragment> fragments=new ArrayList<>();

    public static BasketBallFragment newInstance(){
        return new BasketBallFragment();
    }

    @Override
    protected void initViews() {
        initViewPager();
        initTabLayout();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.football_fragment;
    }



    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
//        toolbar.setTitle(R.string.app_name);
//        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        pager.setAdapter(new BasketPagerAdapter(getChildFragmentManager()));
    }


    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(pager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventMainThread(DrawerClickEvent event){

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
