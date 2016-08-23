package com.example.horry.footbasket.ui.adapter.RecycleAdapter.FootBall;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.entity.football.Top;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class HeaderViewAdapter extends PagerAdapter {

    private List<Top.Recommend> recommendList;

    public HeaderViewAdapter(List<Top.Recommend> recommends){
        recommendList=recommends;

    }
    public void updateItem(){
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int newPosition = position % recommendList.size();
        ImageView imageView=new ImageView(App.getContext());
        Glide.with(App.getContext())
                .load(recommendList.get(position).getThumb())
                .centerCrop()
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
