package com.example.horry.footbasket.network;

import com.example.horry.footbasket.network.Nba.NbaApi;
import com.example.horry.footbasket.network.Nba.NbaClient;
import com.example.horry.footbasket.network.Nba.NbaDetailApi;
import com.example.horry.footbasket.network.Nba.VideoIndexApi;
import com.example.horry.footbasket.network.football.FootballApi;
import com.example.horry.footbasket.network.football.FootballClient;

/**
 * Created by Administrator on 2016/8/6.
 */
public class Factory {
    private static NbaApi sInstanceInstance=null;
    private static NbaDetailApi sNewsSetileInStance=null;
    private static FootballApi footballApiInstance=null;
    private static VideoIndexApi videoIndexApiInstance=null;
    private static final Object WATCH_DOG=new Object();

    private Factory(){}

    public static NbaApi getNbaApiInstance() {
        synchronized (WATCH_DOG) {
            if(sInstanceInstance==null){
                NbaClient nbaClient = new NbaClient();
                sInstanceInstance= nbaClient.getNbaApi();
                sNewsSetileInStance= nbaClient.getNbaDetailApi();
            }
            return sInstanceInstance;
        }
    }

    public static NbaDetailApi getNbaDetailApiInstance() {
        synchronized (WATCH_DOG) {
            if(sNewsSetileInStance==null){
                NbaClient nbaClient = new NbaClient();
                sInstanceInstance= nbaClient.getNbaApi();
                sNewsSetileInStance= nbaClient.getNbaDetailApi();
            }
            return sNewsSetileInStance;
        }
    }

    public static FootballApi getFootballApiInstance(){
        synchronized (WATCH_DOG){
            if (footballApiInstance==null){
                FootballClient footballClient=new FootballClient();
                footballApiInstance=footballClient.getFbApi();
            }
            return footballApiInstance;
        }
    }
    public static VideoIndexApi getVideoIndexApiInstance(){
        synchronized (WATCH_DOG){
            if(videoIndexApiInstance==null){
                NbaClient nbaClient=new NbaClient();
                videoIndexApiInstance=nbaClient.getVideoIndexApi();
            }
        }
        return videoIndexApiInstance;
    }

}
