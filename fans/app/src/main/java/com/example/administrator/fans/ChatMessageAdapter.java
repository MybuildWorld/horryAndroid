package com.example.administrator.fans;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.bean.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/4/22.
 */
public class ChatMessageAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<ChatMessage> message;

    ChatMessageAdapter(Context context, List<ChatMessage> message){
        inflater= LayoutInflater.from(context);
        this.message=message;
    }
    @Override
    public int getCount() {
        return message.size();
    }

    @Override
    public Object getItem(int position) {
        return message.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position)
    {
        ChatMessage chatMessage = message.get(position);
        if (chatMessage.getType() == ChatMessage.Type.Incoming)
        {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount()
    {
        return 2;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessage = message.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            // 通过ItemType设置不同的布局
            if (getItemViewType(position) == 0)
            {
                convertView = inflater.inflate(R.layout.item_from_msg, parent,
                        false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = (TextView) convertView
                        .findViewById(R.id.id_form_msg_date);
                viewHolder.mMsg = (TextView) convertView
                        .findViewById(R.id.id_from_msg_info);
            } else
            {
                convertView =inflater.inflate(R.layout.item_to_msg, parent,
                        false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = (TextView) convertView
                        .findViewById(R.id.id_to_msg_date);
                viewHolder.mMsg = (TextView) convertView
                        .findViewById(R.id.id_to_msg_info);
            }
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 设置数据
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.mDate.setText(df.format(chatMessage.getDate()));
        viewHolder.mMsg.setText(chatMessage.getMsg());
        return convertView;
    }

    private final class ViewHolder
    {
        TextView mDate;
        TextView mMsg;
    }
}
