package com.example.horry.footbasket.network.Nba;



import com.example.horry.footbasket.entity.NewsDetail;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/6.
 */
public interface NbaDetailApi {
    @GET("{date}/{detileId}")
    Observable<NewsDetail> getNewsDetile(@Path("date") String type,@Path("detileId") String newsId);
}
