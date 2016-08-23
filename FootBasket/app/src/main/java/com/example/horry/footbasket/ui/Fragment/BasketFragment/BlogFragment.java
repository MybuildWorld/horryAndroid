package com.example.horry.footbasket.ui.Fragment.BasketFragment;

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
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.BlogAdapter;
import com.example.horry.footbasket.ui.listener.LoadMoreOnScrollListener;
import com.example.horry.footbasket.utils.AppUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/4.
 */
public class BlogFragment extends swipefreshFragment implements LoadMoreOnScrollListener.OnLoadMoreListener{
    private static boolean mFirstAnimate=true;
    @BindView(R.id.rv_news)
    RecyclerView mNewsListView;
    @BindView(R.id.newsContainer)
    CoordinatorLayout newsContainer;


    List<News.NewsEnity> mNewsListEntity = new ArrayList<News.NewsEnity>();
    AnimAdapter mLoadAdapter;
    String mNewsId="";

    public static BlogFragment newInstance() {
        return  new BlogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setAdapter(){
         mLoadAdapter=new BlogAdapter(getActivity(),mNewsListEntity);
         mNewsListView.setAdapter(mLoadAdapter);
         mNewsListView.setVerticalScrollBarEnabled(false);
         mLoadAdapter.setAnimate(mFirstAnimate);
         mFirstAnimate=false;
         initCaChe();
     };
    private void initCaChe() {
        AppService.getInstance().initNews(getTaskId(), constant.NEWSTYPE.BLOG.getNewsType());
    }

    @Override
    protected void initViews() {
        super.initViews();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mNewsListView.getContext());
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

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_news;
    }

    void stopAll() {
        stopRefreshing();
        stopLoading();
    }

    void stopLoading() {
        mLoadAdapter.notifyItemChanged(mLoadAdapter.getItemCount() - 1);
        mLoadAdapter.setLoading(false);
    }



    protected void updateView(NewsEvent newsEvent) {

        if (constant.Result.FAIL.equals(newsEvent.getEventResult())) {
            if(newsEvent.getNewsWay().equals(constant.GETNEWSWAY.INIT)) {
                setRefreshing();
            }else {
                stopAll();
                AppUtil.showSnackBar(newsContainer, R.string.load_fail);
            }
        } else {
            News news = newsEvent.getNews();
            mNewsId = news.getNextId();
            switch (newsEvent.getNewsWay()) {
                case INIT:
                    mNewsListEntity.clear();
                    mNewsListEntity.addAll(news.getNewslist());
                    break;
                case UPDATE:
                    mNewsListEntity.clear();
                    mNewsListEntity.addAll(news.getNewslist());
                    stopRefreshing();
                    mLoadAdapter.setAnimate(false);
                    break;
                case LOADMORE:
                    mNewsListEntity.addAll(news.getNewslist());
                    stopLoading();
                    mLoadAdapter.setAnimate(false);
                    break;
                default:
                    break;
            }
            mLoadAdapter.updateItem();
            if (constant.GETNEWSWAY.UPDATE.equals(newsEvent.getNewsWay())) {
                AppUtil.showSnackBar(newsContainer, R.string.load_success);
            }

        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NewsAnimEndEvent newsAnimatEndEvent) {

    }

    @Override
    public void onLoadMore() {
        if (mLoadAdapter.canLoadMore()) {
            mLoadAdapter.setLoading(true);
            mLoadAdapter.notifyItemChanged(mLoadAdapter.getItemCount() - 1);
            AppService.getInstance().loadMoreNews(getTaskId(),constant.NEWSTYPE.BLOG.getNewsType(), mNewsId);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NewsEvent newsEvent) {
        if(newsEvent!=null&&constant.NEWSTYPE.BLOG.getNewsType().equals(newsEvent.getNewsType())) {
            updateView(newsEvent);
        }
    }
    @Override
    public void onRefresh() {
        AppService.getInstance().updateNews(getTaskId(), constant.NEWSTYPE.BLOG.getNewsType());
    }
}
