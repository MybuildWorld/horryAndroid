package com.example.horryxiao.zhihu.Item;


import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.example.horryxiao.zhihu.UI.CircleImageView;

import org.w3c.dom.Text;

import java.util.List;

public class DataItem {
    Drawable user_image;
    String topic;
    String votes;
    String title;
    String content;

    public DataItem(Drawable user_image,String topic,String votes,String title,String content){
        this.user_image=user_image;
        this.topic=topic;
        this.votes=votes;
        this.title=title;
        this.content=content;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setUser_image(Drawable user_image) {
        this.user_image = user_image;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getUser_image() {
        return user_image;
    }

    public String getContent() {
        return content;
    }

    public String getTopic() {
        return topic;
    }

    public String getVotes() {
        return votes;
    }

}
