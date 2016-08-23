package com.example.horry.footbasket.ui.Fragment.BasketFragment.base;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.ui.Fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/4.
 */

public abstract class swipefreshFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rv_news)
    protected RecyclerView recyclerView;
    @BindView(R.id.newsContainer)
    protected CoordinatorLayout newsContainer;
    protected void setRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    protected boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    protected void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void initViews() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryLight);
    }

}
