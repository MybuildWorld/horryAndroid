package com.example.horry.footbasket.events;

/**
 * Created by Administrator on 2016/8/4.
 */
public class DrawerClickEvent extends event{
    private String ItemName;

    public DrawerClickEvent(String ItemName) {
        this.ItemName=ItemName;
    }

    public String getDrawItemName() {
        return ItemName;
    }
}

