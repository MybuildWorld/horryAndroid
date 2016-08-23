package com.example.horry.footbasket.events;


import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.News;

public class NewsEvent extends event {
    private News News;
    private constant.GETNEWSWAY getNewsWay;
    private String newsType;

    public String getNewsType() {
        return newsType;
    }

    public NewsEvent(News News,constant.GETNEWSWAY getNewsWay,String newsType) {
        this.News = News;
        this.newsType=newsType;
        this.getNewsWay=getNewsWay;

    }

    public News getNews() {
        return News;
    }

    public constant.GETNEWSWAY getNewsWay() {
        return getNewsWay;
    }
}
