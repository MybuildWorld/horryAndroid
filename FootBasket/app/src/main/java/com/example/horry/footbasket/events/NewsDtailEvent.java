package com.example.horry.footbasket.events;

/**
 * Created by Administrator on 2016/8/6.
 */
public class NewsDtailEvent extends event{
    private String mNewsContent;
    public NewsDtailEvent(String newsContent) {
        this.mNewsContent=newsContent;
    }
    public String getContent() {
        return mNewsContent;
    }
}
