package com.example.horry.footbasket.ui.Fragment.FootBallFragment.Base;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.ui.Fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/10.
 */
public abstract class SwipefreshFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipeRefreshLayout) protected SwipeRefreshLayout mSwipeRefreshLayout;

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
