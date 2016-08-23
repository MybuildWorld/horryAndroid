package com.example.horry.footbasket.events;

import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.nbaVideoItem;

/**
 * Created by Administrator on 2016/8/14.
 */
public class nbaItemEvent extends event {
    String type;
    nbaVideoItem nbaVideoItem;
    constant.GETNEWSWAY getItemWay;
    public nbaItemEvent(String type,nbaVideoItem item,constant.GETNEWSWAY getItemWay){
        this.type=type;
        nbaVideoItem=item;
        this.getItemWay=getItemWay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public constant.GETNEWSWAY getGetItemWay() {
        return getItemWay;
    }

    public void setGetItemWay(constant.GETNEWSWAY getItemWay) {
        this.getItemWay = getItemWay;
    }

    public com.example.horry.footbasket.entity.nbaVideoItem getNbaVideoItem() {
        return nbaVideoItem;
    }

    public void setNbaVideoItem(com.example.horry.footbasket.entity.nbaVideoItem nbaVideoItem) {
        this.nbaVideoItem = nbaVideoItem;
    }
}
