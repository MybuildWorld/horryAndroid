package com.example.horry.footbasket.ui.Fragment.FootBallFragment;

import com.example.horry.footbasket.events.LeagueEvent;
import com.example.horry.footbasket.ui.Fragment.FootBallFragment.Base.LeagueBaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2016/8/11.
 */
public class ChinaFragment extends LeagueBaseFragment {
    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void setAdapter() {
        super.setAdapter();
    }

    protected void initcache(){

    }
    public void updateView(LeagueEvent event){

    }
    public void stopAll(){
        stopRefreshing();
        stopLoad();
    }
    public void stopLoad(){

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventMainThread(LeagueEvent event){

    }
}
