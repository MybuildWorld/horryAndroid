package com.example.horry.footbasket.ui.adapter.RecycleAdapter.FootBall;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.entity.football.Top;
import com.example.horry.footbasket.events.NewsAnimEndEvent;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.AnimAdapter;
import com.example.horry.footbasket.utils.ScreenUtil;
import com.thefinestartist.Base;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/10.
 */
public class TopAdapter extends RecyclerView.Adapter<TopAdapter.BaseViewHolder> {

    List<Top.Article> articles;
    Context context;
    LayoutInflater mInflater;
    protected Boolean mLoading=false;
    protected int mAnimateEndCount;
    private int lastAnimatedPosition=-1;
    private static final int ANIMATED_ITEMS_DURATION=1000;
    public  TopAdapter(Context context,List<Top.Article> articles) {
       this.context=context;
        this.articles=articles;
        mInflater= LayoutInflater.from(context);
    }
    //从底部进入动画
    protected void runEnterAnimation(View view, final int position) {

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(ScreenUtil.getScreenHeight(context));
            view.animate()
                    .setInterpolator(new DecelerateInterpolator(3.0f))
                    .translationY(0)
                    .setDuration(ANIMATED_ITEMS_DURATION)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {

                            if (position == mAnimateEndCount||position>=getItemCount()-1) {
                                AppService.getInstance().getsBus().post(new NewsAnimEndEvent());
                            }
                        }
                    })
                    .start();
        }
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder=null;
        switch (VIEWTYPE.values()[viewType]){
            case NORMAL:
                holder=new NormalHolder(mInflater.inflate(R.layout.item_fb_top_normal,parent,false));
                break;
            case MOREPIC:
                holder=new MorePicHolder(mInflater.inflate(R.layout.item_fb_top_morrpic,parent,false));
                break;
            case LOADMORE:
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
        runEnterAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return articles==null?0:articles.size();
    }

    @Override
    public int getItemViewType(int position) {
        int itemtype;
        if(articles==null||articles.get(position)==null)
            return VIEWTYPE.ERRO.getViewType();
        else if(getItemCount()-1==position)
            return VIEWTYPE.LOADMORE.getViewType();
        else if (articles.get(position).album != null)
            return VIEWTYPE.MOREPIC.getViewType();
        else
            return VIEWTYPE.NORMAL.getViewType();

    }
    public boolean canLoadMore() {
        return !mLoading;
    }
    public void setLoading(boolean loading) {
        this.mLoading = loading;
    }

    public void updateItem(){notifyDataSetChanged();}

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
    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected abstract void update(int position);

    }
    protected abstract  class EntityHolder extends  BaseViewHolder implements View.OnClickListener{

        Top.Article articleEntity;
        public EntityHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void update(int position) {
            articleEntity= articles.get(position);
        }

        @Override
        public void onClick(View v) {
            if(articleEntity==null) return ;
            new FinestWebView.Builder((Activity)context)
                    .gradientDivider(false)
                    .show(articleEntity.getUrl());

        }
    }

    public class NormalHolder extends EntityHolder {
        @BindView(R.id.title)
        TextView title ;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.icon)
        ImageView icon;

        public NormalHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void update(int position) {
            super.update(position);
            Glide.with(App.getContext()).load(articles.get(position).getThumb()).placeholder(R.mipmap.football_holder)
                    .centerCrop().into(icon);
            title.setText(articles.get(position).getTitle());
            desc.setText(articles.get(position).getDescription());


        }
    }

    public class MorePicHolder extends EntityHolder {
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
        protected void update(int position) {
            super.update(position);
            Glide.with(App.getContext()).load(articles.get(position).getAlbum().pics.get(0)).placeholder(R.mipmap.football_holder)
                    .centerCrop().into(icon1);
            Glide.with(App.getContext()).load(articles.get(position).getAlbum().pics.get(1)).placeholder(R.mipmap.football_holder)
                    .centerCrop().into(icon2);
            Glide.with(App.getContext()).load(articles.get(position).getAlbum().pics.get(2)).placeholder(R.mipmap.football_holder)
                    .centerCrop().into(icon3);
            title.setText(articles.get(position).getTitle());
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
