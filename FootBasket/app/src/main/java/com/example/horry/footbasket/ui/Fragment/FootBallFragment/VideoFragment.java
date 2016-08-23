package com.example.horry.footbasket.ui.Fragment.FootBallFragment;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.football.Video;
import com.example.horry.footbasket.events.VideoEvent;
import com.example.horry.footbasket.ui.Fragment.FootBallFragment.Base.SwipefreshFragment;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.FootBall.VideoAdapter;
import com.example.horry.footbasket.ui.listener.LoadMoreOnScrollListener;
import com.example.horry.footbasket.utils.AppUtil;
import com.example.horry.footbasket.utils.StringUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/11.
 */
public class VideoFragment extends SwipefreshFragment implements LoadMoreOnScrollListener.OnLoadMoreListener {
    @BindView(R.id.rv_news)
    RecyclerView recyclerView;
    @BindView(R.id.newsContainer)
    CoordinatorLayout newsContainer;
    private List<Video.Articles> videos=new ArrayList<>();
    VideoAdapter adapter;
    Map<String ,String> next=new HashMap<>();
    public static VideoFragment newInstance(){
        return new VideoFragment();
    }
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
                if (mSwipeRefreshLayout.isRefreshing())
                    return true;
                else
                    return false;
            }
        });
        setAdapter();
    }

    protected void setAdapter(){
        mSwipeRefreshLayout.setBackgroundResource(R.color.grey50);
        adapter=new VideoAdapter(getActivity(),videos);
        recyclerView.setAdapter(adapter);
        initCache();
    }
    protected void initCache(){
        AppService.getInstance().initVideo(getTaskId());
    }

    @Override
    public void onRefresh() {
        AppService.getInstance().updateVideo(getTaskId());
    }
    public void updateView(VideoEvent event){
        if(constant.Result.FAIL.equals(event.getEventResult())){
            if(constant.GETNEWSWAY.INIT.equals(event.getGetVideosway())){
                setRefreshing();
            }
            else{
                stopAll();
                AppUtil.showSnackBar(newsContainer,R.string.load_fail);
            }
        }
        else{
            Video video=event.getVideo();
            String nexturl=video.getNext();
            next.put(StringUtil.AFTER1,StringUtil.subString(nexturl));
            next.put(StringUtil.PAGE1,StringUtil.lastChar(nexturl));
            Log.d("TAG",next.toString());
            switch (event.getGetVideosway()){
                case INIT:
                    videos.clear();
                    videos.addAll(video.getArticles());
                   break;
                case UPDATE:
                    videos.clear();
                    videos.addAll(video.getArticles());
                    stopRefreshing();
                    break;
                case LOADMORE:
                    videos.addAll(video.getArticles());
                    stopLoad();
                    break;
                    default:
                        break;
            }
                adapter.updateItem();
                if(constant.GETNEWSWAY.UPDATE.equals(event.getGetVideosway()))
                    AppUtil.showSnackBar(newsContainer,R.string.load_success);

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
    public void onEventMainThrad(VideoEvent event){
        if(event!=null) {
            updateView(event);
        }
    }

    @Override
    public void onLoadMore() {
        if(adapter.canLoadMore()){
            adapter.setLoading(true);
            adapter.notifyItemChanged(adapter.getItemCount()-1);
            AppService.getInstance().loadMoreVideo(getTaskId(),next);
        }
    }
}
