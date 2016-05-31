package com.example.administrator.dt.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.dt.R;
import com.example.administrator.dt.bean.TopItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class TopItemAdapter extends PagerAdapter {
    Context context;
    public List<TopItem> imageInfo=new ArrayList<>();
   public  TopItemAdapter(Context context){
         this.context=context;
         initData();
    }
   public void initData(){
      imageInfo = new ArrayList<TopItem>();
       imageInfo.add(new TopItem(R.drawable.top1, "我所经历的生活", "4月18日 周六"));
       imageInfo.add(new TopItem(R.drawable.top2, "橡皮擦初心", "4月18日 周六"));
       imageInfo.add(new TopItem(R.drawable.top3, "一只喵的幸福生活", "4月17日 周五"));
       imageInfo.add(new TopItem(R.drawable.top4, "手绘电影场景", "4月16日 周四"));
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.image_top_item,container,false);
        container.addView(view);
        ImageView topImage= ButterKnife.findById(view,R.id.top_image);
        TextView tvTime=ButterKnife.findById(view,R.id.time);
        TextView tvTitle=ButterKnife.findById(view,R.id.title);
        TopItem item=imageInfo.get(position);
        topImage.setImageResource(item.getImageResource());
        tvTime.setText(item.getTime());
        tvTitle.setText(item.getTitle());
        return view;
    }

    @Override
    public int getCount() {
        return imageInfo.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
