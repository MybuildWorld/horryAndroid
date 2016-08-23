package com.example.horry.footbasket.RxMethod;

import android.util.Log;

import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.nbaVideoItem;
import com.example.horry.footbasket.events.nbaItemEvent;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/14.
 */
public class RxVideoItem {
    public static Subscription initVideo(final String type,final String Ids){
        return Observable.create(new Observable.OnSubscribe<nbaVideoItem>() {
            @Override
            public void call(Subscriber<? super nbaVideoItem> subscriber) {
                nbaVideoItem nbaVideoItem=getCacheVideo(type,Ids);
                subscriber.onNext(nbaVideoItem);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<nbaVideoItem>() {
                    @Override
                    public void call(nbaVideoItem nbaVideoItem) {
                        Log.d("initVideo","onNext");
                        nbaItemEvent event=new nbaItemEvent(type,nbaVideoItem, constant.GETNEWSWAY.INIT);
                        if(nbaVideoItem==null)
                            event.setEventResult(constant.Result.FAIL);
                        AppService.getsBus().post(event);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("initVideo","onError");
                        nbaItemEvent event=new nbaItemEvent(type,new nbaVideoItem(), constant.GETNEWSWAY.INIT);
                        event.setEventResult(constant.Result.FAIL);
                        AppService.getsBus().post(event);
                    }
                });
    }

    public static Subscription updateVideo(final String type,final String Ids){
        return AppService.getVideoIndexApi().getNewsItem(type, Ids)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<nbaVideoItem>() {
                    @Override
                    public void call(nbaVideoItem item) {
                        cacheVideo(type,Ids);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<nbaVideoItem>() {
                               @Override
                               public void call(nbaVideoItem nbaVideoItem) {
                                   Log.d("updateVideo", "onNext");
                                   nbaItemEvent event=new nbaItemEvent(type,nbaVideoItem, constant.GETNEWSWAY.UPDATE);
                                   AppService.getsBus().post(event);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.d("updateVideo","onError");
                                throwable.printStackTrace();
                                nbaItemEvent event=new nbaItemEvent(type,new nbaVideoItem(), constant.GETNEWSWAY.UPDATE);
                                event.setEventResult(constant.Result.FAIL);
                                AppService.getsBus().post(event);
                            }
                        });
    }
    public static  nbaVideoItem getCacheVideo(final String type,final String Ids){
        return null;
    }
    public static void cacheVideo(final String type ,final String Ids){

    }
}
