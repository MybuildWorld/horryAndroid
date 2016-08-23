package com.example.horry.footbasket.events;

import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.Teams;

/**
 * Created by Administrator on 2016/8/6.
 */
public class TeamSortEvent extends event {
    private Teams mTeams;
    public TeamSortEvent(Teams teams,constant.Result result) {
        this.mTeams=teams;
        this.mEventResult=result;
    }

    public Teams getmTeams() {
        return mTeams;
    }
}
