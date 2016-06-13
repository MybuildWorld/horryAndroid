package com.example.horryxiao.zhihu.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.horryxiao.zhihu.Item.DataItem;
import com.example.horryxiao.zhihu.R;
import com.example.horryxiao.zhihu.UI.CircleImageView;
import com.example.horryxiao.zhihu.Util.FontsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/6/7.
 */
public class ItemAdapter extends RecyclerView.Adapter {
    Typeface typeface;
    List<DataItem> itemList=new ArrayList<>();
    public ItemAdapter(List<DataItem> itemList){
        this.itemList=itemList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        FontsUtil.getTypeface(context,"splash.ttf");
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.messege_layout,parent,false);
        return  new MyViewHolder(view );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataItem data=itemList.get(position);
        MyViewHolder viewHolder= (MyViewHolder) holder;
        viewHolder.user_image.setImageDrawable(data.getUser_image());
        viewHolder.topic.setText(data.getTopic());
        viewHolder.votes.setText(data.getVotes());
        viewHolder.title.setText(data.getTitle());
        viewHolder.contain.setText(data.getContent());
        viewHolder.contain.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public static class  MyViewHolder extends RecyclerView.ViewHolder{
         public CircleImageView user_image;
         public TextView topic,votes,title,contain;
         public MyViewHolder(View itemView) {
             super(itemView);
             user_image= (CircleImageView) itemView.findViewById(R.id.user_image);
             topic= (TextView) itemView.findViewById(R.id.tv_topic);
             votes=(TextView)itemView.findViewById(R.id.tv_vote);
             title=(TextView)itemView.findViewById(R.id.tv_title);
             contain=(TextView)itemView.findViewById(R.id.tv_contain);
         }
     }
    public static ItemAdapter getInstance(Context context,int item){
        List<DataItem> itemList=new ArrayList<>();
        Random random=new Random();
        ItemAdapter itemAdapter;
         for(int i=0;i<item;i++){
             Drawable user_image=context.getResources().getDrawable(R.drawable.img_user);
             String title=context.getResources().getString(R.string.title);
             String contain=context.getResources().getString(R.string.test);
             String vote=String.valueOf(random.nextInt(999));
             String topic ="哲学";
             DataItem dataItem=new DataItem(user_image,topic,vote,title,contain);
             itemList.add(dataItem);
         }
         return  itemAdapter=new ItemAdapter(itemList);
    }

}
