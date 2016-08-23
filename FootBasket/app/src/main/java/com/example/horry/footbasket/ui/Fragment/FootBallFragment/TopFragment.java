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
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.football.Top;
import com.example.horry.footbasket.events.TopEvent;
import com.example.horry.footbasket.ui.Fragment.FootBallFragment.Base.SwipefreshFragment;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.FootBall.HeaderViewAdapter;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.FootBall.TopAdapter;
import com.example.horry.footbasket.ui.listener.LoadMoreOnScrollListener;
import com.example.horry.footbasket.utils.AppUtil;
import com.example.horry.footbasket.utils.ResUtils;
import com.example.horry.footbasket.utils.StringUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/10.
 */
public class TopFragment extends SwipefreshFragment implements LoadMoreOnScrollListener.OnLoadMoreListener{

    @BindView(R.id.rv_news)
    RecyclerView recyclerView;
    @BindView(R.id.newsContainer)
    CoordinatorLayout newsContainer;
    private ViewPager viewPager;
    private TextView title;
    private LinearLayout linearLayout;
    public List<Top.Article> articles=new ArrayList<>();
    public List<Top.Recommend> recommend=new ArrayList<>();
    TopAdapter topAdapter;
    HeaderViewAdapter headerViewAdapter;
    Map<String ,String> next=new HashMap<>();
    public static TopFragment newInstance(){
        return new TopFragment();
    }
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_news;
    }

    @Override
    public void onRefresh() {
        AppService.getInstance().updateTop(getTaskId());
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
        //添加头布局
        View headerView = ResUtils.inflate(R.layout.list_item_header);
//        recyclerView.addView(headerView,0);
        viewPager = (ViewPager) headerView.findViewById(R.id.view_pager);
        title = (TextView) headerView.findViewById(R.id.title);
        linearLayout = (LinearLayout) headerView.findViewById(R.id.dot_container);
        setAdapter();
    }
    protected void setAdapter(){
        mSwipeRefreshLayout.setBackgroundResource(R.color.grey50);
        topAdapter=new TopAdapter(getActivity(),articles);
        recyclerView.setAdapter(topAdapter);
        headerViewAdapter=new HeaderViewAdapter(recommend);
        viewPager.setAdapter(headerViewAdapter);
        initcache();
    }
    public void initcache(){
        AppService.getInstance().initTop(getTaskId());
    }
    @Override
    public void onLoadMore() {
        if(topAdapter.canLoadMore()){
            topAdapter.setLoading(true);
            topAdapter.notifyItemChanged(topAdapter.getItemCount() - 1);
            AppService.getInstance().loadMoreTop(getTaskId(), next);
        }
    }
    protected void updateView(TopEvent event){
        if(constant.Result.FAIL.equals(event.getEventResult())){
            if(event.getGetNewsWay().equals(constant.GETNEWSWAY.INIT)){
                setRefreshing();
                Log.d("TAG","hre");
            }
            else{
                stopAll();
                AppUtil.showSnackBar(newsContainer, R.string.load_fail);
            }
        }
        else{
            Top top=event.getTop();
            String nexturl= top.getNext();
            next.put(StringUtil.AFTER1,StringUtil.subString(nexturl));
            next.put(StringUtil.PAGE1,StringUtil.lastChar(nexturl));
            Log.d("next",next.toString());
            switch (event.getGetNewsWay()){
                case INIT:
                    articles.clear();
                    recommend.clear();
                    articles.addAll(top.getArticles());
                    recommend.addAll(top.getRecommend());
                    break;
                case UPDATE:
                    articles.clear();
                    recommend.clear();
                    articles.addAll(top.getArticles());
                    recommend.addAll(top.getRecommend());
                    stopRefreshing();
                    break;
                case LOADMORE:
                    articles.addAll(top.getArticles());
                    stopLoad();
                    break;
                default:
                    break;
            }
            topAdapter.updateItem();
            headerViewAdapter.updateItem();
            if(constant.GETNEWSWAY.UPDATE.equals(event.getGetNewsWay())){
                AppUtil.showSnackBar(newsContainer,R.string.load_success);
            }
        }
    }
    public void stopAll(){
        stopRefreshing();
        stopLoad();
    }
    public void stopLoad(){
        topAdapter.notifyItemChanged(topAdapter.getItemCount()-1);
        topAdapter.setLoading(false);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TopEvent event){

        if(event!=null){
            updateView(event);
        }
    }
}
