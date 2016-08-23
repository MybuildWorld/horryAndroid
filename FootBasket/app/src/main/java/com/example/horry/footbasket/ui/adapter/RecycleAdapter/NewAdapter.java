package com.example.horry.footbasket.ui.adapter.RecycleAdapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.horry.footbasket.R;
import com.example.horry.footbasket.entity.News;
import com.example.horry.footbasket.utils.DateFomatter;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;

public class NewAdapter extends AnimAdapter {
    public NewAdapter(Context context, List<News.NewsEnity> newsList) {
        super(context, newsList);
        setAnimateEndCount(4);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder=null;
        switch (VIEWTYPE.values()[viewType]){
            case LOADMORE:
                holder=new LoadMoreViewHolder(mInflater.inflate(R.layout.loadmore_news_item,parent,false));
                break;
            case NOPIC:
                holder=new NoPicViewHolder(mInflater.inflate(R.layout.nopic_news_item,parent,false));
                break;
            case MOREPIC:
                holder=new MorePicViewHolder(mInflater.inflate(R.layout.morepic_news_item,parent,false));
                break;
            case NORMAL:
                holder=new NomalViewHolder(mInflater.inflate(R.layout.normal_news_item,parent,false));
                break;
                default:
                    break;
        }
        return holder;
    }

    class NomalViewHolder extends EntityHolder{

        @BindView(R.id.newsImage)
        ImageView newsImage;
        @BindView(R.id.newsTitle)
        TextView newsTitleTV;
        @BindView(R.id.newsTime)
        TextView newsTimeTV;
        String showTime;

        public NomalViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void update(int position) {
            super.update(position);
            Glide.with(mContext).load(newEntity.getImgUrlList().get(0))
                    .placeholder(R.mipmap.placeholder_small).centerCrop()
                    .into(newsImage);
            newsTitleTV.setText(newEntity.getTitle());
            if((Long.parseLong(newEntity.getPutdate()))<20151207){
                showTime=newEntity.getPutdate().substring(4,6)+"月"+newEntity.getPutdate().substring(6,8)+"日";
            }else{
                showTime = DateFomatter.getRecentlyTimeFormatText(new DateTime(Long.parseLong(newEntity.getPutdate())));
            }
            newsTimeTV.setText(showTime);
        }
    }

    class NoPicViewHolder extends  EntityHolder{

        @BindView(R.id.newsTitle)
        TextView newsTitleTV;
        @BindView(R.id.newsTime)
        TextView newsTimeTV;
        @BindView(R.id.newsDescription)
        TextView newsDescriptionTV;
        String showTime;

        public NoPicViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void update(int position) {
            super.update(position);
            newsTitleTV.setText(newEntity.getTitle());
            newsDescriptionTV.setText(newEntity.getDescription());
            if((Long.parseLong(newEntity.getPutdate()))<20151207){
                showTime=newEntity.getPutdate().substring(4,6)+"月"+newEntity.getPutdate().substring(6,8)+"日";
            }else{
                showTime = DateFomatter.getRecentlyTimeFormatText(new DateTime(Long.parseLong(newEntity.getPutdate())));
            }
            newsTimeTV.setText(showTime);
        }
    }

    class MorePicViewHolder extends EntityHolder{

        @BindView(R.id.newsImage1)
        ImageView newsImage1;
        @BindView(R.id.newsImage2)
        ImageView newsImage2;
        @BindView(R.id.newsImage3)
        ImageView newsImage3;
        @BindView(R.id.newsTitle)
        TextView newsTitleTV;
        @BindView(R.id.newsTime)
        TextView newsTimeTV;
        String showTime;

        public MorePicViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void update(int position) {
            super.update(position);
            Glide.with(mContext).load(newEntity.getImgUrlList().get(1))
                    .placeholder(R.mipmap.placeholder_small).centerCrop()
                    .into(newsImage1);
            Glide.with(mContext).load(newEntity.getImgUrlList().get(2))
                    .placeholder(R.mipmap.placeholder_small).centerCrop()
                    .into(newsImage2);
            Glide.with(mContext).load(newEntity.getImgUrlList().get(3))
                    .placeholder(R.mipmap.placeholder_small).centerCrop()
                    .into(newsImage3);
            newsTitleTV.setText(newEntity.getTitle());
            if ((Long.parseLong(newEntity.getPutdate())) < 20151207) {
                showTime = newEntity.getPutdate().substring(4, 6) + "月" + newEntity.getPutdate().substring(6, 8) + "日";
            } else {
                showTime = DateFomatter.getRecentlyTimeFormatText(new DateTime(Long.parseLong(newEntity.getPutdate())));
            }
            newsTimeTV.setText(showTime);
        }
    }

}
