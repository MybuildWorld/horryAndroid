package com.example.horry.footbasket.RxMethod;

import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.Games;
import com.example.horry.footbasket.events.GamesEvent;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/7.
 */
public class RxGames {
    public static Subscription getTeams(String date) {
    Subscription subscription = AppService.getNbaApi().getGames(date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<Games>() {
                @Override
                public void call(Games games) {
                    AppService.getsBus().post(new GamesEvent(games, constant.Result.SUCCESS));
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    AppService.getsBus().post(new GamesEvent(null, constant.Result.FAIL));
                }
            });
    return subscription;

    }
}
