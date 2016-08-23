package com.example.horry.footbasket.ui.Fragment.FragmentAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.ui.Fragment.BaseFragment;
import com.example.horry.footbasket.ui.Fragment.BasketBallFragment;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.BlogFragment;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.MatchesFragment;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.NbaVideoFragment;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.NewsFragment;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.TeamSortFragment;
import com.example.horry.footbasket.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/12.
 */
public class BasketPagerAdapter extends FragmentPagerAdapter {
    private Map<String ,BaseFragment> BaseFragmentById=new HashMap<>();
    String title[];
    public BasketPagerAdapter(FragmentManager fm) {
        super(fm);
        title= App.getContext().getResources().getStringArray(R.array.tab_basket_title);
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
        Bundle bundle=new Bundle();
        BaseFragment fragment=BaseFragmentById.get(title[position]);
        if(fragment==null){
            switch (position){
                case 0:
                    fragment= NewsFragment.newInstance();
                    break;
                case 1:
                    fragment= BlogFragment.newInstance();
                    break;
                case 2:
                    fragment= NbaVideoFragment.newsInstance();
                    bundle.putString(StringUtil.key,"videos");
                    fragment.setArguments(bundle);
                    break;
                case 3:
                    fragment= NbaVideoFragment.newsInstance();
                    bundle.putString(StringUtil.key,"depth");
                    fragment.setArguments(bundle);
                    break;
                case 4:
                    fragment= NbaVideoFragment.newsInstance();
                    bundle.putString(StringUtil.key,"highlight");
                    fragment.setArguments(bundle);
                    default:
                        break;
            }
            BaseFragmentById.put(title[position],fragment);
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
