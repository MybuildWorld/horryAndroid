package com.example.horry.footbasket.ui.adapter.RecycleAdapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.entity.News;
import com.example.horry.footbasket.entity.NewsDetail;
import com.example.horry.footbasket.events.NewsAnimEndEvent;
import com.example.horry.footbasket.ui.activity.NewsDetailActivity;
import com.example.horry.footbasket.utils.ScreenUtil;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/4.
 */
public abstract  class AnimAdapter extends RecyclerView.Adapter<AnimAdapter.BaseViewHolder> {

    private static final int ANIMATED_ITEMS_DURATION=1000;
    protected Context mContext;
    protected List<News.NewsEnity> mNewsList;
    protected LayoutInflater mInflater;
    private boolean mAnimate=true;
    protected int mAnimateEndCount;
    private int lastAnimatedPosition=-1;
    protected Boolean mLoading=false;

    protected AnimAdapter(Context context,List<News.NewsEnity> newsList){
        this.mContext = context;
        this.mNewsList=newsList;
        mInflater = LayoutInflater.from(context);
    }
    //接受数据类型的情况：正常、无图、多图、加载过多、错误
    enum VIEWTYPE{
        NORMAL(0),NOPIC(1),MOREPIC(2),LOADMORE(3),ERROR(4);
        private int viewType;
        VIEWTYPE(int viewType) {
            this.viewType=viewType;
        }

        public int getViewType() {
            return viewType;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.update(position);
        if(mAnimate) {
            runEnterAnimation(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList==null?0:mNewsList.size();
    }

    //返回每一个视图的数据类型
    @Override
    public int getItemViewType(int position) {
        int itemType;
        if(mNewsList==null||mNewsList.get(position)==null) {
            itemType= VIEWTYPE.ERROR.getViewType();
        }else if ( position == getItemCount() - 1) {
            itemType= VIEWTYPE.LOADMORE.getViewType();
        } else  if(mNewsList.get(position).getImgUrlList().size()==0){
            itemType= VIEWTYPE.NOPIC.getViewType();
        }else if(mNewsList.get(position).getImgUrlList().size()>=4){
            itemType= VIEWTYPE.MOREPIC.getViewType();
        }else {
            itemType= VIEWTYPE.NORMAL.getViewType();
        }
        return itemType;
    }

    protected void setAnimateEndCount(int animateEndCount) {
        this.mAnimateEndCount=animateEndCount;
    }

    //从底部进入动画
    protected void runEnterAnimation(View view, final int position) {

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(ScreenUtil.getScreenHeight(mContext));
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
    public void setLoading(boolean loading) {
        this.mLoading = loading;
    }

    public void setAnimate(boolean animate) {
        this.mAnimate = animate;
    }

    public boolean canLoadMore() {
        return !mLoading;
    }
    public void updateItem(){notifyDataSetChanged();}
    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected abstract void update(int position);

    }
    protected abstract  class EntityHolder extends  BaseViewHolder implements View.OnClickListener{

        News.NewsEnity newEntity;
        public EntityHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void update(int position) {
            newEntity= mNewsList.get(position);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(mContext, NewsDetailActivity.class);
            intent.putExtra(NewsDetailActivity.TITLE, newEntity.getTitle());
            intent.putExtra(NewsDetailActivity.DETILE_DATE, new DateTime(Long.parseLong(newEntity.getPutdate())).toString("yyyyMMdd"));
            intent.putExtra(NewsDetailActivity.DETILE_ID, newEntity.getArticleId());
            intent.putExtra(NewsDetailActivity.IMAGE_EXIST, newEntity.getImgUrlList().size() > 0);
            if (newEntity.getImgUrlList().size()>0) {
                intent.putExtra(NewsDetailActivity.IMAGE_URL, newEntity.getImgUrlList().get(0));
            }
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeScaleUpAnimation(v, //The View that the new activity is animating from
                            (int) v.getWidth() / 2, (int) v.getHeight() / 2, //拉伸开始的坐标
                            0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏

            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
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
