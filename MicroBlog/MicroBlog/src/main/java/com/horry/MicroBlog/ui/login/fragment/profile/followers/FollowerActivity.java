package com.horry.MicroBlog.ui.login.fragment.profile.followers;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenming.MicroBlog.R;
import com.horry.MicroBlog.api.StatusesAPI;
import com.horry.MicroBlog.entity.User;
import com.horry.MicroBlog.mvp.presenter.FollowerActivityPresent;
import com.horry.MicroBlog.mvp.presenter.imp.FollowerActivityPresentImp;
import com.horry.MicroBlog.mvp.view.FollowActivityView;
import com.horry.MicroBlog.ui.common.BaseActivity;
import com.horry.MicroBlog.ui.common.FillContent;
import com.horry.MicroBlog.ui.common.login.AccessTokenKeeper;
import com.horry.MicroBlog.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.horry.MicroBlog.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.horry.MicroBlog.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.horry.MicroBlog.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;

/**
 * Created by wenmingvs on 16/5/1.
 */
public class FollowerActivity extends BaseActivity implements FollowActivityView {

    public FollowerAdapter mAdapter;
    private ArrayList<User> mDatas;
    public Context mContext;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public RecyclerView mRecyclerView;
    public StatusesAPI mStatusesAPI;
    public boolean mRefrshAllData;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private FollowerActivityPresent mFollowerActivityPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_follower_layout);
        mContext = this;
        mFollowerActivityPresent = new FollowerActivityPresentImp(this);
        initRefreshLayout();
        initRecyclerView();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mFollowerActivityPresent.pullToRefreshData(Long.valueOf(AccessTokenKeeper.readAccessToken(mContext).getUid()), mContext);
            }
        });
    }

    protected void initRefreshLayout() {
        mRefrshAllData = true;
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.base_swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFollowerActivityPresent.pullToRefreshData(Long.valueOf(AccessTokenKeeper.readAccessToken(mContext).getUid()), mContext);
            }
        });
    }


    public void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.base_RecyclerView);
        mAdapter = new FollowerAdapter(mDatas, mContext) {
            @Override
            public void followerLayoutClick(User user, int position, ImageView follwerIcon, TextView follwerText) {
                follwerIcon.setImageResource(R.drawable.bga_refresh_loading02);
                follwerText.setText("");
                if (user.following) {
                    mFollowerActivityPresent.user_destroy(user, mContext, follwerIcon, follwerText);
                } else {
                    mFollowerActivityPresent.user_create(user, mContext, follwerIcon, follwerText);
                }
            }
        };
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
    }


    public EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            if (mDatas != null && mDatas.size() > 0) {
                showLoadFooterView();
                mFollowerActivityPresent.requestMoreData(Long.valueOf(AccessTokenKeeper.readAccessToken(mContext).getUid()), mContext);
            }
        }
    };

    public void onArrorClick(View view) {
        finish();
    }

    @Override
    public void updateListView(ArrayList<User> userlist) {
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mDatas = userlist;
        mAdapter.setData(userlist);
        mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingIcon() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingIcon() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFooterView() {
        RecyclerViewStateUtils.setFooterViewState(FollowerActivity.this, mRecyclerView, mDatas.size(), LoadingFooter.State.Loading, null);
    }

    @Override
    public void hideFooterView() {
        RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showErrorFooterView() {
        RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.NetWorkError);
    }

    @Override
    public void updateRealtionShip(User user, ImageView icon, TextView text) {
        FillContent.updateRealtionShip(user, icon, text);
    }


}
