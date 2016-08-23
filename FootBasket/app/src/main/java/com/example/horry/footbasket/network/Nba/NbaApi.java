package com.example.horry.footbasket.network.Nba;



import com.example.horry.footbasket.entity.Games;
import com.example.horry.footbasket.entity.News;
import com.example.horry.footbasket.entity.Statictics;
import com.example.horry.footbasket.entity.Teams;
import com.example.horry.footbasket.entity.football.Top;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/3.
 */
public interface NbaApi {

    //篮球模块
    @GET("api/v1.0/{type}/update")
    Observable<News> updateNews(@Path("type") String type);
    @GET("api/v1.0/{type}/loadmore/{newsId}")
    Observable<News> loadMoreNews(@Path("type") String type,@Path("newsId") String newsId );
    @GET("api/v1.0/nbastat/perstat/{statKind}")
    Observable<Statictics> getPerStats(@Path("statKind") String statKind);
    @GET("api/v1.0/teamsort/sort")
    Observable<Teams> getTeamSort();
    @GET("api/v1.0/gamesdate/{date}")
    Observable<Games> getGames(@Path("date") String date);

}
