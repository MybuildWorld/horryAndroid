package com.example.horry.footbasket.ui.adapter.RecycleAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.horry.footbasket.R;
import com.example.horry.footbasket.entity.News;
import com.example.horry.footbasket.entity.VideoRealUrl;
import com.example.horry.footbasket.entity.nbaVideoItem;
import com.example.horry.footbasket.network.Nba.NbaClient;
import com.example.horry.footbasket.ui.Fragment.BaseFragment;
import com.example.horry.footbasket.utils.RequestCallback;
import com.example.horry.footbasket.utils.ScreenUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Administrator on 2016/8/14.
 */
public class NBAVdieoAdapter extends RecyclerView.Adapter<NBAVdieoAdapter.BaseViewHolder> {

    List<nbaVideoItem.NewsItemBean> videoList=new ArrayList<>();
    Context context;
    LayoutInflater mInflater;
    protected Boolean mLoading=false;

    public boolean canLoadMore(){
        return !mLoading;
    }
    public void setLoading(Boolean mLoading) {
        this.mLoading = mLoading;
    }
    public NBAVdieoAdapter(Context context,List<nbaVideoItem.NewsItemBean> videoList){
        this.context=context;
        this.videoList=videoList;
        this.mInflater=LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder=null;
        switch (VIEWTYPE.values()[viewType]){
            case NORMAL:
                holder=new NormalHolder(mInflater.inflate(R.layout.item_nba_video,parent,false));
                break;
            case MORE:
                holder=new LoadMoreViewHolder(mInflater.inflate(R.layout.loadmore_news_item,parent,false));
                break;
                default:
                    break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.update(position);
    }
    enum VIEWTYPE{
        NORMAL(0),MORE(1),ERRO(2);
        private int viewType;
        VIEWTYPE(int viewType) {
            this.viewType=viewType;
        }

        public int getViewType() {
            return viewType;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(videoList==null||videoList.get(0)==null)
            return VIEWTYPE.ERRO.getViewType();
        else if(getItemCount()-1==position)
            return VIEWTYPE.MORE.getViewType();
        else
            return VIEWTYPE.NORMAL.getViewType();
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public void updateItem(){
        notifyDataSetChanged();
    }
    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected abstract void update(int position);

    }
    protected  class NormalHolder extends  BaseViewHolder implements View.OnClickListener{
        @BindView(R.id.vpVideo)
        JCVideoPlayerStandard videoPlayer;
        @BindView(R.id.tvVideoTitle)
        TextView title;
        @BindView(R.id.tvVideoTime)
        TextView time;

        nbaVideoItem.NewsItemBean bean;
        public NormalHolder (View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void update(int position) {
           bean=videoList.get(position);
            if (TextUtils.isEmpty(bean.realUrl)) {
//                videoPlayer.setUp(" ",JCVideoPlayerStandard.SCREEN_LAYOUT_LIST,bean.title);
                NbaClient.getVideoRealUrl(bean.vid, new RequestCallback<VideoRealUrl>() {
                    @Override
                    public void onSuccess(VideoRealUrl real) {
                        String vid = TextUtils.isEmpty(real.fn) ? real.vid + ".mp4" : real.fn;
                        String url = real.url + vid + "?vkey=" + real.fvkey;
                        bean.realUrl = url;
                        Log.i("TAG", "title：" + bean.title);
                        Log.i("TAG", "real-url：" + url);
                        videoPlayer.setUp(url, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, bean.title);
                    }

                    @Override
                    public void onFailure(String message) {
                        Log.i("TAG", "real-url：" + message);
                    }
                });
            } else {
                if(!TextUtils.isEmpty(bean.title)) {
                    videoPlayer.setUp(bean.realUrl, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, bean.title);
                }
            }
            Glide.with(context).load(bean.imgurl).centerCrop().into(videoPlayer.thumbImageView);
            ViewGroup.LayoutParams params = videoPlayer.getLayoutParams();
            params.height = ScreenUtil.getScreenWidth(context) / 2;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            videoPlayer.setLayoutParams(params);
            title.setText(bean.title);
            time.setText(bean.pub_time);
        }

        @Override
        public void onClick(View v) {

        }
    }

    protected class LoadMoreViewHolder extends BaseViewHolder {

        @BindView(R.id.item_load_more_icon_loading)
        protected View iconLoading;

        @BindView(R.id.item_load_more_icon_fail)
        protected View iconFail;

        protected LoadMoreViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void update(int position) {
            iconLoading.setVisibility(mLoading ? View.VISIBLE : View.GONE);
            iconFail.setVisibility(mLoading ? View.GONE : View.VISIBLE);
        }

    }

}
