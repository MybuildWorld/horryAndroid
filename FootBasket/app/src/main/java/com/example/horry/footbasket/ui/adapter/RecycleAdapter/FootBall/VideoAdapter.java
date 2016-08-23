package com.example.horry.footbasket.ui.adapter.RecycleAdapter.FootBall;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.entity.football.Video;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.AnimAdapter;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/11.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.BaseViewHolder> {
    List<Video.Articles> videos;
    Context context;
    LayoutInflater mInflater;
    protected Boolean mLoading=false;

    public boolean canLoadMore(){
        return !mLoading;
    }
    public Boolean getmLoading() {
        return mLoading;
    }

    public void setLoading(Boolean mLoading) {
        this.mLoading = mLoading;
    }

    public VideoAdapter(Context context,List<Video.Articles> videos){
        this.context=context;
        this.videos=videos;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          BaseViewHolder holder=null;
          switch (VIEWTYPE.values()[viewType]){
              case NORMAL:
                  holder=new Normal(mInflater.inflate(R.layout.item_list_video,parent,false));
                  break;
              case LOADMORE:
                  holder=new LoadMore(mInflater.inflate(R.layout.loadmore_news_item,parent,false));
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
        NORMAL(0),LOADMORE(1),ERRO(2);
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
        if (videos==null||videos.get(position)==null)
            return VideoAdapter.VIEWTYPE.ERRO.getViewType();
        else if(position==(getItemCount()-1))
            return VideoAdapter.VIEWTYPE.LOADMORE.getViewType();
        else return VideoAdapter.VIEWTYPE.NORMAL.getViewType();
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        protected abstract void update(int position);
    }
    public abstract  class EntityHolder extends BaseViewHolder implements View.OnClickListener {
        Video.Articles articles;
        public EntityHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        protected void update(int position) {
            articles=videos.get(position);
        }
        @Override
        public void onClick(View v) {
            new FinestWebView.Builder((Activity)context)
                    .gradientDivider(false)
                    .show(articles.url);
        }
    }
    public class Normal extends EntityHolder{
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.video)
        ImageView video;
        @BindView(R.id.title)
        TextView title;
        public Normal(View itemView) {
            super(itemView);
        }

        @Override
        public void update(int position) {
            super.update(position);
            Glide.with(App.getContext()).load(videos.get(position).thumb).placeholder(R.mipmap.football_holder)
            .centerCrop()
            .into(icon);
            title.setText(videos.get(position).title);
        }

        @Override
        public void onClick(View v) {
                super.onClick(v);
        }
    }
    public class LoadMore extends BaseViewHolder{
        @BindView(R.id.item_load_more_icon_loading)
        protected View iconLoading;

        @BindView(R.id.item_load_more_icon_fail)
        protected View iconFail;

        public LoadMore(View itemView) {
            super(itemView);
        }

        @Override
        public void update(int position) {
            iconLoading.setVisibility(mLoading ? View.VISIBLE : View.GONE);
            iconFail.setVisibility(mLoading ? View.GONE : View.VISIBLE);
        }
    }
    public void updateItem(){
        notifyDataSetChanged();
    }

}
