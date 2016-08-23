package com.example.horry.footbasket.ui.adapter.RecycleAdapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.horry.footbasket.R;
import com.example.horry.footbasket.entity.News;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/8/4.
 */
public class BlogAdapter extends AnimAdapter {
    public BlogAdapter(Context context, List<News.NewsEnity> newsList) {
        super(context, newsList);
        setAnimateEndCount(1);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder=null;
        switch(VIEWTYPE.values()[viewType]){
            case LOADMORE:
                holder=new LoadMoreViewHolder(mInflater.inflate(R.layout.loadmore_news_item,parent,false));
                break;
            case NOPIC:
                holder=new NormalBlogViewHolder(mInflater.inflate(R.layout.normal_blog_item,parent,false),false);
                break;
            case NORMAL:
                holder=new NormalBlogViewHolder(mInflater.inflate(R.layout.normal_blog_item,parent,false),true);
                break;
            default:
                break;
        }
        return holder;
    }

    class NormalBlogViewHolder extends EntityHolder{
        @BindView(R.id.newsImage)
        ImageView mNewsImage;
        @BindView(R.id.newsTitle)
        TextView mNwsTitle;
        @BindView(R.id.newsTime) TextView mNewsTime;
        @BindView(R.id.description) TextView description;
        @BindView(R.id.profile_image)
        CircleImageView profileImage;
        @BindView(R.id.author) TextView author;
        View itemView;
        boolean hasImage=true;
        String showTime;
        public NormalBlogViewHolder(View itemView,Boolean hasImage) {
            super(itemView);
            this.itemView = itemView;
            this.hasImage = hasImage;
            if (!hasImage) {
                mNewsImage.setVisibility(View.GONE);
                description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            }
        }

        @Override
        protected void update(int position) {
            super.update(position);
            boolean isZhangJiaWei = newEntity.getContentSourceName().equals("张佳玮的博客");
            if(hasImage) {
                mNewsImage.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(newEntity.getImgUrlList().get(0))
                        .placeholder(R.mipmap.placeholder_biger)
                        .into(mNewsImage);
            }
            profileImage.setImageResource(isZhangJiaWei ? R.mipmap.zhangjiawei : R.mipmap.suqun);
            author.setText(isZhangJiaWei ? "张佳玮  " : "苏群  ");
            mNwsTitle.setText(newEntity.getTitle());
            showTime = new DateTime(Long.parseLong(newEntity.getPutdate())).toString("yyyy年MM月dd日");
            mNewsTime.setText(showTime);
            description.setText(newEntity.getDescription() + "......");
        }
    }
}
