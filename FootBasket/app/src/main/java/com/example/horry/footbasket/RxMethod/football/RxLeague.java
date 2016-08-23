package com.example.horry.footbasket.RxMethod.football;

import android.util.Log;

import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.football.League;
import com.example.horry.footbasket.events.LeagueEvent;
import com.example.horry.greendao.GreenLeague;
import com.example.horry.greendao.GreenLeagueDao;

import java.util.List;
import java.util.Map;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2016/8/11.
 */
public class RxLeague {
    public static Subscription initLeague(final String type){
        return Observable.create(new Observable.OnSubscribe<League>() {
            @Override
            public void call(Subscriber<? super League> subscriber) {
                League video = getCacheLeague(type);
                subscriber.onNext(video);
                subscriber.onCompleted();
                Log.i("RxLeague", Thread.currentThread() + ";;;create");
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Action1<League>() {
                    @Override
                    public void call(League league) {
                        LeagueEvent event=new LeagueEvent(league, constant.GETNEWSWAY.INIT,type);
                        if(league==null){
                            event.setEventResult(constant.Result.FAIL);
                        }
                        AppService.getsBus().post(event);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        League league=new League();
                       LeagueEvent event=new LeagueEvent(league, constant.GETNEWSWAY.INIT,type);
                        event.setEventResult(constant.Result.FAIL);
                        AppService.getsBus().post(event);
                    }
                });
    }

    public static Subscription updateLeague(final String type){
        return AppService.getFootballApi().getLeagueInfo(type)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<League>() {
                    @Override
                    public void call(League league) {
                        cacheLeague(league, type);
                        Log.i("RxLeague", Thread.currentThread() + ";;;donOnNext");


                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<League>() {
                               @Override
                               public void call(League league) {
                                   Log.i("RxLeague",Thread.currentThread()+";;;subscribe");
                                   AppService.getsBus().post(new LeagueEvent(league, constant.GETNEWSWAY.UPDATE,type));
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                LeagueEvent event=new LeagueEvent(new League(), constant.GETNEWSWAY.UPDATE,type);
                                event.setEventResult(constant.Result.FAIL);
                                AppService.getsBus().post(event);
                            }
                        });
    }

    public static Subscription loadMoreLeague(final String type,Map<String,String>next){
        return AppService.getFootballApi().loadMoreLeagueInfo(type,next)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<League>() {
                    @Override
                    public void call(League league) {
                        cacheLeague(league,type);
                        Log.i("RxLeague", Thread.currentThread() + ";;;donOnNext");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<League>() {
                    @Override
                    public void call(League league) {
                        Log.d("RxLeague","LOADMORE");
                        AppService.getsBus().post(new LeagueEvent(league, constant.GETNEWSWAY.LOADMORE, type));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        LeagueEvent event=new LeagueEvent(new League(), constant.GETNEWSWAY.LOADMORE,type);
                        event.setEventResult(constant.Result.FAIL);
                        AppService.getsBus().post(event);
                    }
                });
    }
    public static void cacheLeague(final League league, final String type){
        GreenLeagueDao greenLeagueDao=AppService.getsDBHelper().getDaoSession().getGreenLeagueDao();
        DeleteQuery deleteQuery=greenLeagueDao.queryBuilder()
                .where(GreenLeagueDao.Properties.Kinds.eq(type))
                .buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
        String leaguestr= AppService.getsGson().toJson(league);
        GreenLeague greenLeague=new GreenLeague(null,leaguestr,type);
        greenLeagueDao.insert(greenLeague);
    }

    public static League getCacheLeague(final String type){
        League league=null;
        GreenLeagueDao greenLeagueDao=AppService.getsDBHelper().getDaoSession().getGreenLeagueDao();
        Query query=greenLeagueDao.queryBuilder().where(GreenLeagueDao.Properties.Kinds.eq(type)).build();
        List<GreenLeague> greenLeagues=query.list();
        if(greenLeagues!=null&&greenLeagues.size()>0) {
            league = AppService.getsGson().fromJson(greenLeagues.get(0).getLeagueentity(), League.class);
        }
        return league;
    }
}
