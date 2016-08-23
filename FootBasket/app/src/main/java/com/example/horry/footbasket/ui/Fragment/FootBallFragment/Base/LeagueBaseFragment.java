package com.example.horry.footbasket.ui.Fragment.FootBallFragment.Base;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.ui.listener.LoadMoreOnScrollListener;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/11.
 */
public abstract  class LeagueBaseFragment  extends SwipefreshFragment implements LoadMoreOnScrollListener.OnLoadMoreListener {
    @BindView(R.id.rv_news)
   protected  RecyclerView recyclerView;
    @BindView(R.id.newsContainer)
    protected CoordinatorLayout newsContainer;
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_news;
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
                if(mSwipeRefreshLayout.isRefreshing())
                    return true;
                else
                    return false;
            }
        });
        setAdapter();
    }
    public void setAdapter(){
        mSwipeRefreshLayout.setBackgroundResource(R.color.grey50);

    }
}
