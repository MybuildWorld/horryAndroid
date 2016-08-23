package com.example.horry.footbasket.events;

import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.football.Top;

/**
 * Created by Administrator on 2016/8/10.
 */
public class TopEvent extends event {
    private Top top;
    private constant.GETNEWSWAY getNewsWay;
    public TopEvent(Top top,constant.GETNEWSWAY getnewsway){
        this.top=top;
        this.getNewsWay=getnewsway;
    }

    public Top getTop() {
        return top;
    }

    public void setTop(Top top) {
        this.top = top;
    }

    public constant.GETNEWSWAY getGetNewsWay() {
        return getNewsWay;
    }

    public void setGetNewsWay(constant.GETNEWSWAY getNewsWay) {
        this.getNewsWay = getNewsWay;
    }
}
