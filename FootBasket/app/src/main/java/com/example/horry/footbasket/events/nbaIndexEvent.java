package com.example.horry.footbasket.events;

import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.VideoIndex;

/**
 * Created by Administrator on 2016/8/14.
 */
public class nbaIndexEvent extends event {
    String type;
    VideoIndex index;
    constant.GETNEWSWAY getIndexWay;
    public nbaIndexEvent(String type,VideoIndex index,constant.GETNEWSWAY getIndexWay){
        this.type=type;
        this.index=index;
        this.getIndexWay=getIndexWay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public constant.GETNEWSWAY getGetIndexWay() {
        return getIndexWay;
    }

    public void setGetIndexWay(constant.GETNEWSWAY getIndexWay) {
        this.getIndexWay = getIndexWay;
    }

    public VideoIndex getIndex() {
        return index;
    }

    public void setIndex(VideoIndex index) {
        this.index = index;
    }
}
