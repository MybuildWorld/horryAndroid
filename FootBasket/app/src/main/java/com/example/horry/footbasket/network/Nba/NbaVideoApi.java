package com.example.horry.footbasket.network.Nba;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/8/14.
 */
public interface NbaVideoApi {
    @GET("/getinfo?otype=xml&platform=1&ran=0%2E9652906153351068")
    Call<String> getVideoRealUrl(@Query("vid") String vid);
}
