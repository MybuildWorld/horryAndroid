package com.example.horry.footbasket.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.ui.Fragment.BaseFragment;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.MatchesFragment;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.TeamSortFragment;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.playerSort.StatisticFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/16.
 */
public class StatActivity extends AppCompatActivity {
    final static String type="League";
    String[] title;
    Map<String,BaseFragment> map=new HashMap<>();
    Map<String,String> maper=new HashMap<>();
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.stat_layout);
        title=getResources().getStringArray(R.array.stat_sort);
        initmap();
    }
    public void initmap(){
        maper.put(title[0], TeamSortFragment.class.getName());
        maper.put(title[1], MatchesFragment.class.getName());
        maper.put(title[2], StatisticFragment.class.getName());

    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=getIntent();
        String title=intent.getStringExtra(type);
        BaseFragment fragment=map.get(title);
        if(fragment==null){
            fragment=getFragment(maper.get(title));
            map.put(title,fragment);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment);
    }

    public BaseFragment getFragment(String name){
        BaseFragment fragment=null;
        try {
            fragment= (BaseFragment) Class.forName(name).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return fragment;
    }
}
