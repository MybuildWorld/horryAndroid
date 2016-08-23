package com.example.horry.footbasket.ui.Fragment.FootBallFragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.football.League;
import com.example.horry.footbasket.events.LeagueEvent;
import com.example.horry.footbasket.ui.Fragment.FootBallFragment.Base.LeagueBaseFragment;
import com.example.horry.footbasket.ui.Fragment.FootBallFragment.Base.SwipefreshFragment;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.FootBall.LeagueAdapter;
import com.example.horry.footbasket.ui.listener.LoadMoreOnScrollListener;
import com.example.horry.footbasket.utils.AppUtil;
import com.example.horry.footbasket.utils.StringUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/11.
 */
public class LeagueFragment extends LeagueBaseFragment {
    String type=null;
    List<League.Articles> leagues=new ArrayList<>();
    LeagueAdapter adapter;
    Map<String ,String> next=new HashMap<>();

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        type=StringUtil.getType(args.getString(StringUtil.key));
        Log.d("League",type);
    }

    public static LeagueFragment newInstance(){
        return new LeagueFragment();
    }

    @Override
    public void onRefresh() {
        AppService.getInstance().updateLeague(getTaskId(), type);
    }

    @Override
    protected void initViews() {
        super.initViews();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new LoadMoreOnScrollListener(linearLayoutManager, this, 0));
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mSwipeRefreshLayout.isRefreshing())
                    return true;
                else
                    return false;
            }
        });
        setAdapter();
    }

    @Override
    public void onLoadMore() {
        if(adapter.canLoadMore()){
            adapter.setLoading(true);
            adapter.notifyItemChanged(adapter.getItemCount() - 1);
            AppService.getInstance().loadMoreLeague(getTaskId(),type,next);
        }
    }

    @Override
    public void setAdapter() {
        super.setAdapter();
        adapter=new LeagueAdapter(getActivity(),leagues);
        recyclerView.setAdapter(adapter);
        initcache();
    }

    protected void initcache(){
        AppService.getInstance().initLeague(getTaskId(),type);
    }

    public void updateView(LeagueEvent event){
        if(constant.Result.FAIL.equals(event.getEventResult())){
            if(constant.GETNEWSWAY.INIT.equals(event.getGetLeagueWay()))
                setRefreshing();
            else {
                stopAll();
                AppUtil.showSnackBar(newsContainer, R.string.load_fail);
            }
        }
        else {
            League league=event.getLeague();
            String nexturl=league.next;
            next.put(StringUtil.AFTER1,StringUtil.subString(nexturl));
            next.put(StringUtil.PAGE1,StringUtil.lastChar(nexturl));
            Log.d("next",next.toString());
            switch (event.getGetLeagueWay()){
                case INIT:
                    leagues.clear();
                    leagues.addAll(league.articles);
                    break;
                case UPDATE:
                    leagues.clear();
                    leagues.addAll(league.articles);
                    stopRefreshing();
                    break;
                case LOADMORE:
                    leagues.addAll(league.articles);
                    stopLoad();
                    default:
                        break;
            }
            adapter.updateItem();
            if(constant.GETNEWSWAY.UPDATE.equals(event.getGetLeagueWay())){
                AppUtil.showSnackBar(newsContainer,R.string.load_success);
            }
        }
    }

    public void stopAll(){
        stopRefreshing();
        stopLoad();
    }

    public void stopLoad(){
        adapter.notifyItemChanged(adapter.getItemCount()-1);
        adapter.setLoading(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventMainThread(LeagueEvent event){
        if(event!=null&&type.equals(event.getType())){
            updateView(event);
        }
    }
}
