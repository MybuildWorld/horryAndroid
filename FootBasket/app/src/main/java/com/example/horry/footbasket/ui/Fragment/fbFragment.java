package com.example.horry.footbasket.ui.Fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.events.DrawerClickEvent;
import com.example.horry.footbasket.ui.Fragment.FragmentAdapter.FbPagerAdapter;
import com.example.horry.footbasket.ui.Fragment.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/10.
 */
public class fbFragment extends BaseFragment{

    @BindView(R.id.tabs)
    protected TabLayout tabLayout;
    @BindView(R.id.vp_content)
    protected ViewPager pager;
    List<Fragment> fragments=new ArrayList<>();

    public static fbFragment newInstance(){
        return new fbFragment();
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
     * 初始化ViewPager
     */
    private void initViewPager() {
        pager.setAdapter(new FbPagerAdapter(getChildFragmentManager()));
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
}
