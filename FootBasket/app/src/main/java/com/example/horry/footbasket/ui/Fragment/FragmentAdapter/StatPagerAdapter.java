package com.example.horry.footbasket.ui.Fragment.FragmentAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.horry.footbasket.ui.Fragment.BasketFragment.playerSort.BarFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class StatPagerAdapter extends FragmentPagerAdapter {
    private List<BarFragment> mBarFragments;

    public StatPagerAdapter(FragmentManager fm, List<BarFragment> barFragments) {
        super(fm);
        this.mBarFragments=barFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mBarFragments.get(position);
    }

    @Override
    public int getCount() {
        return mBarFragments.size();
    }
}
