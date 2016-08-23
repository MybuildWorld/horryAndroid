package com.example.horry.footbasket.events;

/**
 * Created by Administrator on 2016/8/8.
 */
public class RhxthmEvent extends event {
    private int mPosition;
    public RhxthmEvent(int position) {
        this.mPosition=position;
    }

    public int getPosition() {
        return mPosition;
    }
}
