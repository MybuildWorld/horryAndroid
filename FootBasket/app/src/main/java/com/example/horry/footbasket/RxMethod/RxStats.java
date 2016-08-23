package com.example.horry.footbasket.RxMethod;

import android.util.Log;

import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.Statictics;
import com.example.horry.footbasket.events.StatEvent;
import com.example.horry.greendao.GreenStat;
import com.example.horry.greendao.GreenStatDao;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/7.
 */
public class RxStats {
    public static Subscription getPerStat(String ...statKinds) {

        Subscription subscription = Observable.from(statKinds)
                .flatMap(new Func1<String, Observable<Statictics>>() {
                    @Override
                    public Observable<Statictics> call(String s) {
                        return AppService.getNbaApi().getPerStats(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Statictics>() {
                    @Override
                    public void call(Statictics statistics) {
                        cacheStat(statistics,statistics.getDailyStat().get(0).getStatkind());
                    }
                })
                .subscribe(new Action1<Statictics>() {
                    @Override
                    public void call(Statictics statistics) {
                        String[][] labels = getLabels(statistics);
                        float[][] statValues=getStatValues(statistics);
                        String[][] playerUrls=getPlayerUrls(statistics);
                        AppService.getsBus().post(new StatEvent(statistics.getDailyStat().get(0).getStatkind(), labels,statValues,playerUrls));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        StatEvent statEvent = new StatEvent(throwable.toString(), null, null,null);
                        statEvent.setEventResult(constant.Result.FAIL);
                        AppService.getsBus().post(statEvent);
                    }
                });
        return subscription;
    }

    public static Subscription initStat(final String statKind) {

        Subscription subscription = Observable.create(new Observable.OnSubscribe<Statictics>() {
            @Override
            public void call(Subscriber<? super Statictics> subscriber) {
                Statictics statistics = getCacheStat(statKind);
                subscriber.onNext(statistics);
                subscriber.onCompleted();

            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Action1<Statictics>() {
                    @Override
                    public void call(Statictics statistics) {
                        String[][] lables = getLabels(statistics);
                        float[][] ststValues=getStatValues(statistics);
                        String[][] playerUrls=getPlayerUrls(statistics);
                        StatEvent statEvent = new StatEvent(statistics.getDailyStat().get(0).getStatkind(), lables, ststValues,playerUrls);
                        statEvent.setGetNewsWay(constant.GETNEWSWAY.INIT);
                        AppService.getsBus().post(statEvent);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        StatEvent statEvent = new StatEvent(statKind, null, null,null);
                        statEvent.setEventResult(constant.Result.FAIL);
                        statEvent.setGetNewsWay(constant.GETNEWSWAY.INIT);
                        AppService.getsBus().post(statEvent);
                    }
                });
        return subscription;
    }
    private static String[][] getLabels(Statictics statistics) {
        String[][] players=new String[2][5];
        for(int index=0;index<5;index++) {
            players[0][index]=parseLabel(statistics.getDailyStat().get(index));
            players[1][index]=parseLabel(statistics.getEverageStat().get(index));
        }
        return players;
    }
    private static String[][] getPlayerUrls(Statictics statistics) {
        String[][] statValues=new String[2][5];
        for(int index=0;index<5;index++) {
            statValues[0][index]=statistics.getDailyStat().get(index).getPlayerurl();
            statValues[1][index]=statistics.getEverageStat().get(index).getPlayerurl();
        }
        return statValues;
    }
    private static String parseLabel(Statictics.StatEntity statEntity) {
        StringBuilder label =new StringBuilder();
        String playerName=statEntity.getName();
        for(int index=0;index<playerName.length()-3;index+=4) {
            label.append(playerName.substring(index,index+4));
            label.append("\n");
        }
        if(playerName.length()%4!=0) {
            label.append(playerName.substring(4 * (playerName.length() / 4), playerName.length()));
            label.append("\n");
        }
        label.append("(");
        label.append(statEntity.getTeam());
        label.append(")");
        return label.toString();

    }

    private static float[][] getStatValues(Statictics statistics) {
        float[][] statValues=new float[2][5];
        for(int index=0;index<5;index++) {
            statValues[0][index]=Float.parseFloat(statistics.getDailyStat().get(index).getStatdata());
            statValues[1][index]=Float.parseFloat(statistics.getEverageStat().get(index).getStatdata());
        }
        return statValues;
    }

    public static void cacheStat(final Statictics stat,final String statKind) {

        GreenStatDao greenStatDao = AppService.getsDBHelper().getDaoSession().getGreenStatDao();
        DeleteQuery deleteQuery = greenStatDao.queryBuilder()
                .where(GreenStatDao.Properties.Statentity.eq(statKind))
                .buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
        String statEntity = AppService.getsGson().toJson(stat);
        GreenStat greenStat = new GreenStat(null, statEntity, statKind);

        greenStatDao.insert(greenStat);
        Log.d("StatEvent", greenStat.getStatkind() + "getStatkind");

    }
    private static Statictics getCacheStat(String statKind) {
        Statictics statistics=null;
        GreenStatDao greenStatDao = AppService.getsDBHelper().getDaoSession().getGreenStatDao();
        Query query = greenStatDao.queryBuilder()
                .where(GreenStatDao.Properties.Statkind.eq(statKind))
                .build();
        // 查询结果以 List 返回
        List<GreenStat> greenStats = query.list();
        if(greenStats!=null&&greenStats.size()>0) {
            statistics = AppService.getsGson().fromJson(greenStats.get(0).getStatentity(), Statictics.class);
        }
        return statistics;
    }


}
