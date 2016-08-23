package com.example.horry.footbasket.ui.Fragment.FragmentAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.ui.Fragment.AssitFragment;
import com.example.horry.footbasket.ui.Fragment.BaseFragment;
import com.example.horry.footbasket.ui.Fragment.RankFragment;
import com.example.horry.footbasket.ui.Fragment.ScoreFragment;
import com.example.horry.footbasket.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/16.
 */
public class SortAdapter extends FragmentPagerAdapter {
    public final static String Key_Assit="助攻榜";
    public final static String Key_Rank="积分榜";
    public final static String Key_Score="射手榜";
    public String TYPE =null;
    public Map<String,BaseFragment> mapAssit=new HashMap<>();
    public Map<String,BaseFragment> mapRank=new HashMap<>();
    public Map<String,BaseFragment> mapScore=new HashMap<>();
    String[] title ;
    public SortAdapter(FragmentManager fm,String type) {
        super(fm);
        TYPE =type;
        title= App.getContext().getResources().getStringArray(R.array.tab_league_title);
    }

    @Override
    public Fragment getItem(int position) {
        if(Key_Assit.equals(TYPE)){
            BaseFragment fragment=mapAssit.get(title[position]);
            if(fragment==null) {
                Bundle bundle=new Bundle();
                switch (position) {
                    case 0:
                        fragment= AssitFragment.newInstance();
                        bundle.putString(StringUtil.key,title[position]);
                        fragment.setArguments(bundle);
                        break;
                    case 1:
                        fragment= AssitFragment.newInstance();
                        bundle.putString(StringUtil.key,title[position]);
                        fragment.setArguments(bundle);
                        break;
                    case 2:
                        fragment= AssitFragment.newInstance();
                        bundle.putString(StringUtil.key,title[position]);
                        fragment.setArguments(bundle);
                        break;
                    case 3:
                        fragment= AssitFragment.newInstance();
                        bundle.putString(StringUtil.key,title[position]);
                        fragment.setArguments(bundle);
                        break;
                    case 4:
                        fragment= AssitFragment.newInstance();
                        bundle.putString(StringUtil.key,title[position]);
                        fragment.setArguments(bundle);
                        break;
                    default:
                        break;
                }
                mapAssit.put(title[position],fragment);
            }
            return fragment;
        }
        else if(Key_Rank.equals(TYPE)){
            BaseFragment fragment=mapRank.get(title[position]);
            if(fragment==null) {
                Bundle bundle=new Bundle();
                switch (position) {
                    case 0:
                        fragment= RankFragment.newInstance();
                        bundle.putString(StringUtil.key,title[position]);
                        fragment.setArguments(bundle);
                        break;
                    case 1:
                        fragment= RankFragment.newInstance();
                        bundle.putString(StringUtil.key,title[position]);
                        fragment.setArguments(bundle);
                        break;
                    case 2:
                        fragment= RankFragment.newInstance();
                        bundle.putString(StringUtil.key,title[position]);
                        fragment.setArguments(bundle);
                        break;
                    case 3:
                        fragment= RankFragment.newInstance();
                        bundle.putString(StringUtil.key,title[position]);
                        fragment.setArguments(bundle);
                        break;
                    case 4:
                        fragment= RankFragment.newInstance();
                        bundle.putString(StringUtil.key,title[position]);
                        fragment.setArguments(bundle);
                        break;
                }
                mapRank.put(StringUtil.key,fragment);
            }
            return fragment;
        }
        else {
            BaseFragment fragment=mapScore.get(title[position]);
            if(fragment==null) {
                Bundle bundle=new Bundle();
                bundle.putString(StringUtil.key,title[position]);
                switch (position) {
                    case 0:
                        fragment= ScoreFragment.newInstance();
                        fragment.setArguments(bundle);
                        break;
                    case 1:
                        fragment= ScoreFragment.newInstance();
                        fragment.setArguments(bundle);
                        break;
                    case 2:
                        fragment= ScoreFragment.newInstance();
                        fragment.setArguments(bundle);
                        break;
                    case 3:
                        fragment= ScoreFragment.newInstance();
                        fragment.setArguments(bundle);
                        break;
                    case 4:
                        fragment= ScoreFragment.newInstance();
                        fragment.setArguments(bundle);
                        break;
                }
                mapScore.put(title[position],fragment);
            }
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
