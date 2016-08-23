package com.example.horry.footbasket.events;

/**
 * Created by Administrator on 2016/8/16.
 */
public class NewsDetailEvent extends event {
    private String mNewsContent;
    public NewsDetailEvent(String newsContent) {
        this.mNewsContent=newsContent;
    }
    public String getContent() {
        return mNewsContent;
    }
}
