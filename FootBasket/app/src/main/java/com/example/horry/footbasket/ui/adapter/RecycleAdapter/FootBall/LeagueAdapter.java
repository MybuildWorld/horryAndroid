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
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.entity.football.League;
import com.example.horry.footbasket.ui.activity.MainActivity;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/11.
 */
    public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueHolder> {

    List<League.Articles> leagues;
    Context context;
    LayoutInflater mInflater;
    protected Boolean mLoading=false;

    public boolean canLoadMore(){
        return !mLoading;
    }

    public void setLoading(Boolean mLoading) {
        this.mLoading = mLoading;
    }
    public LeagueAdapter(Context context,List<League.Articles>leagues){
        this.context=context;
        this.leagues=leagues;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public LeagueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       LeagueHolder holder=null;
        switch (VIEWTYPE.values()[viewType]){
            case NORMAL:
                holder=new Normal(mInflater.inflate(R.layout.item_fb_top_normal,parent,false));
                break;
            case MOREPIC:
                holder=new MorePicHolder(mInflater.inflate(R.layout.item_fb_top_morrpic,parent,false));
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
    public void onBindViewHolder(LeagueHolder holder, int position) {
        holder.updateView(position);
    }

    @Override
    public int getItemCount() {
        return leagues.size();
    }
    enum VIEWTYPE{
        NORMAL(0),MOREPIC(1),LOADMORE(2),ERRO(3);
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
        if(leagues==null||leagues.get(position)==null)
            return VIEWTYPE.ERRO.getViewType();
        else if(getItemCount()-1==position)
            return VIEWTYPE.LOADMORE.getViewType();
        else if (leagues.get(position).album != null)
            return VIEWTYPE.MOREPIC.getViewType();
        else
            return VIEWTYPE.NORMAL.getViewType();
    }

    public abstract class LeagueHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public LeagueHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        abstract public void updateView(int position);
    }
    public class Normal extends LeagueHolder{
        League.Articles articles;
        @BindView(R.id.title)
        TextView title ;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.icon)
        ImageView icon;
        public Normal(View itemView) {
            super(itemView);

        }

        @Override
        public void updateView(int position) {
            articles=leagues.get(position);
            Glide.with(App.getContext()).load(leagues.get(position).getThumb()).placeholder(R.mipmap.football_holder)
                    .centerCrop().into(icon);
            title.setText(leagues.get(position).getTitle());
            desc.setText(leagues.get(position).getDescription());
        }

        @Override
        public void onClick(View v) {
            new FinestWebView.Builder((Activity)context)
                    .gradientDivider(false)
                    .show(articles.getUrl());
        }
    }
    public class MorePicHolder extends LeagueHolder{
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.icon1)
        ImageView icon1;
        @BindView(R.id.icon2)
        ImageView icon2;
        @BindView(R.id.icon3)
        ImageView icon3;
        public MorePicHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void updateView(int position) {
            Glide.with(App.getContext()).load(leagues.get(position).getAlbum().pics.get(0)).placeholder(R.mipmap.football_holder)
                    .centerCrop().into(icon1);
            Glide.with(App.getContext()).load(leagues.get(position).getAlbum().pics.get(1)).placeholder(R.mipmap.football_holder)
                    .centerCrop().into(icon2);
            Glide.with(App.getContext()).load(leagues.get(position).getAlbum().pics.get(2)).placeholder(R.mipmap.football_holder)
                    .centerCrop().into(icon3);
            title.setText(leagues.get(position).getTitle());
        }

        @Override
        public void onClick(View v) {

        }
    }
    public class LoadMore extends LeagueHolder{

        @BindView(R.id.item_load_more_icon_loading)
        protected View iconLoading;

        @BindView(R.id.item_load_more_icon_fail)
        protected View iconFail;
        public LoadMore(View itemView) {
            super(itemView);
        }

        @Override
        public void updateView(int position) {
            iconLoading.setVisibility(mLoading ? View.VISIBLE : View.GONE);
            iconFail.setVisibility(mLoading ? View.GONE : View.VISIBLE);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public void updateItem(){
        notifyDataSetChanged();
    }

}
