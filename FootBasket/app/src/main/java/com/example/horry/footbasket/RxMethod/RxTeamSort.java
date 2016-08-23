package com.example.horry.footbasket.RxMethod;

import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.Teams;
import com.example.horry.footbasket.events.TeamSortEvent;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/7.
 */
public class RxTeamSort {
    public static Subscription getTeams() {
        Subscription subscription = AppService.getNbaApi().getTeamSort()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Teams>() {
                    @Override
                    public void call(Teams teams) {
                        cacheTeams();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Teams>() {
                    @Override
                    public void call(Teams news) {
                        AppService.getsBus().post(new TeamSortEvent(news, constant.Result.SUCCESS));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        AppService.getsBus().post(new TeamSortEvent(null, constant.Result.FAIL));
                    }
                });
        return subscription;
    }

    private static void cacheTeams() {

    }
}
