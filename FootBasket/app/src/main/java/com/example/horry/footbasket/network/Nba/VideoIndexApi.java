package com.example.horry.footbasket.network.Nba;

import com.example.horry.footbasket.entity.VideoIndex;
import com.example.horry.footbasket.entity.nbaVideoItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/14.
 */
public interface VideoIndexApi {
    @GET("news/index")
    Observable<VideoIndex> getNewsIndex(@Query("column") String column);

    @GET("news/item")
    Observable<nbaVideoItem> getNewsItem(@Query("column") String column, @Query("articleIds") String articleIds);
}
