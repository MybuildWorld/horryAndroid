package com.example.horry.footbasket.RxMethod.football;

import android.util.Log;

import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.football.Top;
import com.example.horry.footbasket.events.TopEvent;
import com.example.horry.greendao.GreenTop;
import com.example.horry.greendao.GreenTopDao;

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
 * Created by Administrator on 2016/8/10.
 */
public class RxTop {
    public final static String key="fb";
    public static Subscription initTop(){
        return  Observable.create(new Observable.OnSubscribe<Top>() {
            @Override
               public void call(Subscriber<? super Top> subscriber) {
                    Top top=getCacheTop(key);
                    subscriber.onNext(top);
                    subscriber.onCompleted();
                Log.i("RxTop", Thread.currentThread() + ";;;create");
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Action1<Top>() {
                               @Override
                               public void call(Top top) {
                                   TopEvent topEvent=new TopEvent(top, constant.GETNEWSWAY.INIT);
                                   if(top==null){
                                       topEvent.setEventResult(constant.Result.FAIL);
                                   }
                                   AppService.getsBus().post(topEvent);
                               }
                           },
                            new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                TopEvent topEvent=new TopEvent(new Top(), constant.GETNEWSWAY.INIT);
                                topEvent.setEventResult(constant.Result.FAIL);
                                AppService.getsBus().post(topEvent);
                            }
                        });
    }

    public static Subscription updateTop(){
        return AppService.getFootballApi().getTop()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Top>() {
                    @Override
                    public void call(Top top) {
                        cacheTop(top,key);
                        Log.i("RxTop", Thread.currentThread() + ";;;donOnNext");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Top>() {
                    @Override
                    public void call(Top top) {
                        Log.i("RxTop",Thread.currentThread()+";;;subscribe");
                        AppService.getsBus().post(new TopEvent(top, constant.GETNEWSWAY.UPDATE));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        TopEvent topevent = new TopEvent(new Top(), constant.GETNEWSWAY.UPDATE);
                        topevent.setEventResult(constant.Result.FAIL);
                        AppService.getsBus().post(topevent);
                    }
                });
    }

    public static Subscription loadMoreTop(final Map<String,String> next){
        return AppService.getFootballApi().loadMoreTop(next)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Top>() {
                    @Override
                    public void call(Top top) {
                        cacheTop(top,key);
                        Log.i("RxTop", Thread.currentThread() + ";;;donOnNext");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Top>() {
                               @Override
                               public void call(Top top) {
                                   AppService.getsBus().post(new TopEvent(top, constant.GETNEWSWAY.LOADMORE));
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                                TopEvent topevent = new TopEvent(new Top(), constant.GETNEWSWAY.LOADMORE);
                                topevent.setEventResult(constant.Result.FAIL);
                                AppService.getsBus().post(topevent);
                            }
                        });
    }

    private  static void cacheTop(final Top top,final String type){
        GreenTopDao greenTopDao=AppService.getsDBHelper().getDaoSession().getGreenTopDao();
        DeleteQuery query=greenTopDao.queryBuilder()
                .where(GreenTopDao.Properties.Typekind.eq(type))
                .buildDelete();
        query.executeDeleteWithoutDetachingEntities();
        String topstr=AppService.getsGson().toJson(top);
        GreenTop greenTop=new GreenTop(null,topstr,type);
        greenTopDao.insert(greenTop);
    }

    private static Top getCacheTop(String type){
        Top top=null;
        GreenTopDao greenTopDao=AppService.getsDBHelper().getDaoSession().getGreenTopDao();
        Query query=greenTopDao.queryBuilder().where(GreenTopDao.Properties.Typekind.eq(type)).build();
        List<GreenTop> topList=query.list();
        if(topList!=null&&topList.size()>0){
            top=AppService.getsGson().fromJson(topList.get(0).getTopentity(),Top.class);
        }
        return top;
    }
}
