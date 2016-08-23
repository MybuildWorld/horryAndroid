package com.example.horry.footbasket.ui.Fragment.FragmentAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.entity.football.Video;
import com.example.horry.footbasket.ui.Fragment.BaseFragment;
import com.example.horry.footbasket.ui.Fragment.FootBallFragment.LeagueFragment;
import com.example.horry.footbasket.ui.Fragment.FootBallFragment.TopFragment;
import com.example.horry.footbasket.ui.Fragment.FootBallFragment.VideoFragment;
import com.example.horry.footbasket.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/10.
 */
public class FbPagerAdapter extends FragmentPagerAdapter {
    private Map<String ,BaseFragment> BaseFragmentById=new HashMap<>();
    private String[] title;
    public FbPagerAdapter(FragmentManager fm) {
        super(fm);
        title= App.getContext().getResources().getStringArray(R.array.tab_fb_title);
    }

    @Override
    public Fragment getItem(int position) {
        return getFragment(position);
    }

    @Override
    public int getCount() {
        return title.length;
    }
    public BaseFragment getFragment(int position){
        Log.d("position",String.valueOf(position));
        Bundle bundle=new Bundle();
        BaseFragment fragment=BaseFragmentById.get(title[position]);
        if(fragment==null){
        switch (position) {
            case 0:
                fragment = TopFragment.newInstance();
                break;
            case 1:
                fragment = VideoFragment.newInstance();
                break;
            case 2:
                fragment = LeagueFragment.newInstance();
                bundle.putString(StringUtil.key, "转会");
                fragment.setArguments(bundle);
                break;
            case 3:
                fragment = LeagueFragment.newInstance();
                bundle.putString(StringUtil.key, "深度");
                fragment.setArguments(bundle);
                break;
            case 4:
                fragment = LeagueFragment.newInstance();
                bundle.putString(StringUtil.key, "中超");
                fragment.setArguments(bundle);
                break;
            case 5:
                fragment = LeagueFragment.newInstance();
                bundle.putString(StringUtil.key, "英超");
                fragment.setArguments(bundle);
                break;
            case 6:
                fragment = LeagueFragment.newInstance();
                bundle.putString(StringUtil.key, "西甲");
                fragment.setArguments(bundle);
                break;
            case 7:
                fragment = LeagueFragment.newInstance();
                bundle.putString(StringUtil.key, "德甲");
                fragment.setArguments(bundle);
                break;
            case 8:
                fragment = LeagueFragment.newInstance();
                bundle.putString(StringUtil.key, "意甲");
                fragment.setArguments(bundle);
                break;
            case 9:
                fragment = LeagueFragment.newInstance();
                bundle.putString(StringUtil.key, "五洲");
                fragment.setArguments(bundle);
                break;
            default:
                break;
             }
            BaseFragmentById.put(title[position],fragment);
        }

        Log.d("FRAGMENT",fragment.toString());
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
