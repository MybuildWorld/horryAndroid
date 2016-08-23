package com.example.horry.footbasket.events;

import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.Games;

/**
 * Created by Administrator on 2016/8/6.
 */
public class GamesEvent  extends event  {
    private Games mAllGames;
    public GamesEvent(Games games,constant.Result result) {
        this.mAllGames=games;
        this.mEventResult=result;
    }

    public Games getAllGames() {
        return mAllGames;
    }
}
