package com.example.horry.footbasket.ui.Fragment.BasketFragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.VideoIndex;
import com.example.horry.footbasket.entity.nbaVideoItem;
import com.example.horry.footbasket.events.nbaIndexEvent;
import com.example.horry.footbasket.events.nbaItemEvent;
import com.example.horry.footbasket.network.Nba.NbaClient;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.base.swipefreshFragment;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.NBAVdieoAdapter;
import com.example.horry.footbasket.ui.listener.LoadMoreOnScrollListener;
import com.example.horry.footbasket.ui.widgets.SpaceItemDecoration;
import com.example.horry.footbasket.utils.AppUtil;
import com.example.horry.footbasket.utils.RequestCallback;
import com.example.horry.footbasket.utils.ScreenUtil;
import com.example.horry.footbasket.utils.StringUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by Administrator on 2016/8/14.
 */
public class NbaVideoFragment  extends swipefreshFragment implements LoadMoreOnScrollListener.OnLoadMoreListener {
    String type=null;
    List<VideoIndex.IndexBean> indexList=new ArrayList<>();
    List<nbaVideoItem.NewsItemBean> videoList=new ArrayList<>();
    NBAVdieoAdapter adapter;
    private List<String> indexs = new ArrayList<>();
    private int start = 0; // 查询数据起始位置
    private int num = 10;
    String Ids="";
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        type=args.getString(StringUtil.key);
    }

    public static NbaVideoFragment newsInstance(){
        return new NbaVideoFragment();
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
        recyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtil.dp2px(5)));
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

    public void setAdapter(){
        adapter=new NBAVdieoAdapter(getActivity(),videoList);
        recyclerView.setAdapter(adapter);
        requestIndex();
    }

    public void requestIndex(){
        AppService.getInstance().initIndex(getTaskId(), type);
    }

    @Override
    public void onRefresh() {
        AppService.getInstance().updateIndex(getTaskId(), type);
    }

    @Override
    public void onLoadMore() {
        if(adapter.canLoadMore()) {
          parseIds();
            if(TextUtils.isEmpty(Ids))
                AppUtil.showSnackBar(newsContainer,R.string.load_end);
            else {
                adapter.setLoading(true);
                adapter.notifyItemChanged(adapter.getItemCount() - 1);
                requestNews(type,Ids,true);
            }
        }
    }

    private void requestNews(String type,final String Ids,boolean isfresh) {
//        AppService.getInstance().initVideoItem(getTaskId(),type,Ids);
        NbaClient.getNewsItem(type,Ids,isfresh, new RequestCallback<nbaVideoItem>() {
            @Override
            public void onSuccess(nbaVideoItem newsItem) {
                    videoList.clear();
                    videoList.addAll(newsItem.data);
                    adapter.updateItem();
//                    AppUtil.showSnackBar(newsContainer,R.string.load_success);
                    stopAll();
            }
            @Override
            public void onFailure(String message) {
                AppUtil.showSnackBar(newsContainer,R.string.load_fail);
                stopAll();
            }
        });
    }
    public void stopAll(){
        stopRefreshing();
        stopLoad();
    }
    public void stopLoad(){
        adapter.notifyItemChanged(adapter.getItemCount()-1);
        adapter.setLoading(false);
    }

    private void parseIds() {
        int size = indexs.size();
        for (int i = start, j = 0; i < size && j < num; i++, j++, start++) {
            Ids += indexs.get(i) + ",";
        }
        if (!TextUtils.isEmpty(Ids))
            Ids = Ids.substring(0, Ids.length() - 1);
        Log.i("TAG","articleIds = " + Ids);
    }

    public void updateIndex(nbaIndexEvent event){
        if (constant.Result.FAIL.equals(event.getEventResult())){
            if(event.getGetIndexWay().equals(constant.GETNEWSWAY.INIT))
                setRefreshing();
            else {
                stopAll();
                AppUtil.showSnackBar(newsContainer, R.string.load_index_fail);
            }
        }
        else{
            switch (event.getGetIndexWay()){
                case INIT:
                    indexList.clear();
                    indexList.addAll(event.getIndex().data);
                    break;
                case UPDATE:
                    indexList.clear();
                    indexList.addAll(event.getIndex().data);
                    break;
                    default:
                        break;

            }
            for(VideoIndex.IndexBean indexbean:indexList){
                indexs.add(indexbean.id);
            }
            parseIds();
            requestNews(type,Ids,false);
        }
    }

    public void updateItem(nbaItemEvent event){
        if(event.getEventResult().equals(constant.Result.FAIL)){
            if(event.getGetItemWay().equals(constant.GETNEWSWAY.INIT))
                AppService.getInstance().getVideoItem(getTaskId(),type,Ids);
            else{
                stopAll();
                AppUtil.showSnackBar(newsContainer,R.string.load_fail);
            }
        }
        else {
            switch (event.getGetItemWay()){
                case INIT:
                    videoList.clear();
                    videoList.addAll(event.getNbaVideoItem().data);
                    stopRefreshing();
                case UPDATE:
                    videoList.clear();
                    videoList.addAll(event.getNbaVideoItem().data);
                    stopAll();
                    default:
                        break;
            }
            adapter.updateItem();
            if(event.getGetItemWay().equals(constant.GETNEWSWAY.UPDATE)){
//                AppUtil.showSnackBar(newsContainer,R.string.load_success);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventMainThread(nbaIndexEvent event){
        if(event!=null&&event.getType().equals(type))
            updateIndex(event);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventMainThread(nbaItemEvent event){
        if(event!=null&&event.getType().equals(type))
            updateItem(event);
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
