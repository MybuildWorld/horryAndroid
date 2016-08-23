package com.example.horry.footbasket.events;

import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.football.League;

/**
 * Created by Administrator on 2016/8/11.
 */
public class LeagueEvent extends event{
    League league;
    constant.GETNEWSWAY getLeagueWay;
    String type;
    public LeagueEvent(League league,constant.GETNEWSWAY getnewsway,String type){
        this.league=league;
        this.getLeagueWay=getnewsway;
        this.type=type;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public constant.GETNEWSWAY getGetLeagueWay() {
        return getLeagueWay;
    }

    public void setGetLeagueWay(constant.GETNEWSWAY getLeagueWay) {
        this.getLeagueWay = getLeagueWay;
    }
}
