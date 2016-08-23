package com.example.horry.footbasket.network.football;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/8/10.
 */
public class FootballClient {
    private FootballApi fbApi;
    public FootballClient(){
        Retrofit retrofit0=new Retrofit.Builder()
                .baseUrl("http://api.dongqiudi.com/app/tabs/android/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        fbApi=retrofit0.create(FootballApi.class);
    }
    public FootballApi getFbApi() {
        return fbApi;
    }
}
