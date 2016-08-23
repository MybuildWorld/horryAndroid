package com.example.horry.footbasket.events;

import com.example.horry.footbasket.data.constant;

/**
 * Created by Administrator on 2016/8/6.
 */
public class StatEvent extends event {
    private String mStatKind;
    private String[][] mLables;
    private String[][] mPlayerUrls;
    private float[][] mStatValues;
    private constant.GETNEWSWAY getNewsWay;
    public StatEvent (String statKind,String[][] lables,float[][] statValues,String[][] playerUrls) {
        this.mStatKind=statKind;
        this.mLables=lables;
        this.mStatValues=statValues;
        this.mPlayerUrls=playerUrls;
    }

    public constant.GETNEWSWAY getGetNewsWay() {
        return getNewsWay;
    }

    public void setGetNewsWay(constant.GETNEWSWAY getNewsWay) {
        this.getNewsWay = getNewsWay;
    }

    public String getStatKind() {
        return mStatKind;
    }

    public String[][] getLables() {
        return mLables;
    }

    public float[][] getStatValues() {
        return mStatValues;
    }

    public String[][] getPlayerUrls() {
        return mPlayerUrls;
    }
}
