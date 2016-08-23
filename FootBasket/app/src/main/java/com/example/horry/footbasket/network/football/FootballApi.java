package com.example.horry.footbasket.network.football;

import com.example.horry.footbasket.entity.football.League;
import com.example.horry.footbasket.entity.football.Top;
import com.example.horry.footbasket.entity.football.Video;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface FootballApi {
    //足球模块
    @GET("1.json")
    Observable<Top> getTop();
    @GET("1.json")
    Observable<Top> loadMoreTop(@QueryMap Map<String,String> next);
    @GET("11.json")
    Observable<Video> getVideo();
    @GET("11.json")
    Observable<Video> loadMoreVideo(@QueryMap Map<String,String> next);
    @GET("{type}")
    Observable<League> getLeagueInfo(@Path("type") String type);
    @GET("{type}")
    Observable<League> loadMoreLeagueInfo(@Path("type") String type,@QueryMap Map<String,String> map);
}
