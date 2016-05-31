package com.example.administrator.dt.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTitleStrip;

import com.example.administrator.dt.UI.ErrorFragment;
import com.example.administrator.dt.UI.FindFragment;
import com.example.administrator.dt.UI.HomeFragment;
import com.example.administrator.dt.UI.MovementFragment;
import com.example.administrator.dt.UI.MsgFragment;
import com.example.administrator.dt.UI.PersonFragment;
import com.example.administrator.dt.UI.ShopFragment;


public class pagerAdapter extends FragmentStatePagerAdapter {
    private String[] pagerTitle={"首页","发现","商店","我"};

    public pagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new HomeFragment();
            case 1:return new FindFragment();
            case 2:return new MovementFragment();
            case 3 :return new MsgFragment();
            default:return ErrorFragment.newInstance(position+"");
        }
    }
    @Override
    public int getCount() {
        return pagerTitle.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return pagerTitle[position];
    }

}
