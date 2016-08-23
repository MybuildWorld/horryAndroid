package com.example.horry.footbasket.ui.Fragment.FragmentAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.horry.footbasket.ui.Fragment.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class FragmentSortAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    public FragmentSortAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
