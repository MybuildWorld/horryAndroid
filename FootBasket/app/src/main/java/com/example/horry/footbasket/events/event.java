package com.example.horry.footbasket.events;

import com.example.horry.footbasket.data.constant;


public class event  {
    protected constant.Result mEventResult;

    public void setEventResult(constant.Result eventResult) {
        this.mEventResult=eventResult;
    }

    public constant.Result getEventResult() {
        return mEventResult;
    }
}
