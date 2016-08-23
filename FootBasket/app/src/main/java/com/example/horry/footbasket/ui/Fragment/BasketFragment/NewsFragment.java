package com.example.horry.footbasket.ui.Fragment.BasketFragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.News;
import com.example.horry.footbasket.events.NewsAnimEndEvent;
import com.example.horry.footbasket.events.NewsEvent;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.base.swipefreshFragment;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.AnimAdapter;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.NewAdapter;
import com.example.horry.footbasket.ui.listener.LoadMoreOnScrollListener;
import com.example.horry.footbasket.utils.AppUtil;
import com.example.horry.footbasket.utils.ScreenUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsFragment extends swipefreshFragment implements LoadMoreOnScrollListener.OnLoadMoreListener{
    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static boolean mFirstAnimate=true;
    @BindView(R.id.rv_news)
    RecyclerView mNewsListView;
    @BindView(R.id.newsContainer)
    CoordinatorLayout newsContainer;

    List<News.NewsEnity> mNewsListEntity=new ArrayList<News.NewsEnity>();
    String newsId="";
    AnimAdapter animAdapter;

    public static NewsFragment newInstance(){
        return new NewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initViews() {
        super.initViews();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mNewsListView.getContext());
        mNewsListView.setLayoutManager(linearLayoutManager);
        mNewsListView.addOnScrollListener(new LoadMoreOnScrollListener(linearLayoutManager, this, 0));
        mNewsListView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
        );
        setAdapter();
    }

    protected void setAdapter(){
        mSwipeRefreshLayout.setBackgroundResource(R.color.grey50);
        animAdapter=new NewAdapter(getActivity(),mNewsListEntity);
        mNewsListView.setAdapter(animAdapter);
        animAdapter.setAnimate(mFirstAnimate);
//        if(mFirstAnimate) {
//            startIntoAnimation();
//            mFirstAnimate=false;
//        }else {
            initCaChe();
//        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_news;
    }
    void stopAll(){
        stopRefreshing();
        stopLoad();
    }
    void stopLoad(){
        animAdapter.notifyItemChanged(animAdapter.getItemCount() - 1);
        animAdapter.setLoading(false);
    }

    protected void updateView(NewsEvent newsEvent) {
        if (constant.Result.FAIL.equals(newsEvent.getEventResult())) {
            if (newsEvent.getNewsWay().equals(constant.GETNEWSWAY.INIT)) {
                setRefreshing();
            } else {
                stopAll();
                AppUtil.showSnackBar(newsContainer, R.string.load_fail);
            }
        } else {
            News news = newsEvent.getNews();
            newsId = news.getNextId();
            switch (newsEvent.getNewsWay()) {
                case INIT:
                    mNewsListEntity.clear();
                    mNewsListEntity.addAll(news.getNewslist());
                    break;
                case UPDATE:
                    mNewsListEntity.clear();
                    mNewsListEntity.addAll(news.getNewslist());
                    stopRefreshing();
                    animAdapter.setAnimate(false);
                    break;
                case LOADMORE:
                    mNewsListEntity.addAll(news.getNewslist());
                    stopLoad();
                    animAdapter.setAnimate(false);
                    break;
                default:
                    break;
            }
            animAdapter.updateItem();
            if (constant.GETNEWSWAY.UPDATE.equals(newsEvent.getNewsWay())) {
                AppUtil.showSnackBar(newsContainer, R.string.load_success);
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NewsAnimEndEvent newsAnimatEndEvent) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NewsEvent newsEvent) {
        try {
            if (newsEvent != null && constant.NEWSTYPE.NEWS.getNewsType().equals(newsEvent.getNewsType())) {
                updateView(newsEvent);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initCaChe() {
        AppService.getInstance().initNews(getTaskId(), constant.NEWSTYPE.NEWS.getNewsType());
    }

    @Override
    public void onRefresh() {
        AppService.getInstance().updateNews(getTaskId(), constant.NEWSTYPE.NEWS.getNewsType());
    }

    @Override
    public void onLoadMore() {
        if (animAdapter.canLoadMore()) {
            animAdapter.setLoading(true);
            animAdapter.notifyItemChanged(animAdapter.getItemCount() - 1);
            AppService.getInstance().loadMoreNews(getTaskId(),constant.NEWSTYPE.NEWS.getNewsType(), newsId);
        }
    }

//    private void startIntoAnimation() {
//
//        int actionbarSize = ScreenUtil.dp2px(56);
//        mToolBar.setTranslationY(-actionbarSize);
//        mainTitle.setTranslationY(-actionbarSize);
//
//        mToolBar.animate()
//                .translationY(0)
//                .setDuration(ANIM_DURATION_TOOLBAR)
//                .setStartDelay(300);
//        mainTitle.animate()
//                .translationY(0)
//                .setDuration(ANIM_DURATION_TOOLBAR)
//                .setStartDelay(400)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        // startContentAnimation();
//                        initCaChe();
//                    }
//                }).start();
//    }
}
