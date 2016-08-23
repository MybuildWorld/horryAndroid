package com.example.horry.footbasket.RxMethod;

import android.util.Log;

import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.News;
import com.example.horry.footbasket.entity.VideoIndex;
import com.example.horry.footbasket.events.nbaIndexEvent;
import com.example.horry.greendao.GreenIndex;
import com.example.horry.greendao.GreenIndexDao;
import com.example.horry.greendao.GreenNews;
import com.example.horry.greendao.GreenNewsDao;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/14.
 */
public class RxNbaVideoIndex  {
    public static Subscription initVideoIndex(final String type){
        return Observable.create(new Observable.OnSubscribe<VideoIndex>() {
            @Override
            public void call(Subscriber<? super VideoIndex> subscriber) {
               VideoIndex index= getIndexCache(type);
                subscriber.onNext(index);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Action1<VideoIndex>() {
                    @Override
                    public void call(VideoIndex videoIndex) {
                        Log.d("initIndex",videoIndex.toString());
                        nbaIndexEvent event=new nbaIndexEvent(type,videoIndex, constant.GETNEWSWAY.INIT);
                        if(videoIndex==null){
                            event.setEventResult(constant.Result.FAIL);
                        }
                            AppService.getsBus().post(event);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        nbaIndexEvent event=new nbaIndexEvent(type,new VideoIndex(), constant.GETNEWSWAY.INIT);
                        event.setEventResult(constant.Result.FAIL);
                        AppService.getsBus().post(event);
                    }
                });
    }

    public static Subscription updateIndex(final String type){
        return AppService.getVideoIndexApi().getNewsIndex(type)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<VideoIndex>() {
                    @Override
                    public void call(VideoIndex videoIndex) {
                        cacheIndex(videoIndex,type);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new Action1<VideoIndex>() {
                    @Override
                    public void call(VideoIndex videoIndex) {
                        Log.d("updateIndex",videoIndex.toString());
                        nbaIndexEvent event=new nbaIndexEvent(type,videoIndex, constant.GETNEWSWAY.UPDATE);
                        AppService.getsBus().post(event);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        nbaIndexEvent event=new nbaIndexEvent(type,new VideoIndex(), constant.GETNEWSWAY.UPDATE);
                        event.setEventResult(constant.Result.FAIL);
                        AppService.getsBus().post(event);
                    }
                });
    }
    public static VideoIndex getIndexCache(String type){

        VideoIndex index=null;
        GreenIndexDao greenIndexDao=AppService.getsDBHelper().getDaoSession().getGreenIndexDao();
        Query query = greenIndexDao.queryBuilder()
                .where(GreenIndexDao.Properties.Typevalue.eq(type))
                        .build();
        // 查询结果以 List 返回
        List<GreenIndex> greenIndexes = query.list();
        if(greenIndexes!=null&&greenIndexes.size()>0) {
           index = AppService.getsGson().fromJson(greenIndexes.get(0).getIndexentity(), VideoIndex.class);
        }
        return index;
    }
    public static void cacheIndex(VideoIndex index,String type){
        GreenIndexDao greenIndexDao=AppService.getsDBHelper().getDaoSession().getGreenIndexDao();
        DeleteQuery deleteQuery = greenIndexDao.queryBuilder()
                .where(GreenIndexDao.Properties.Typevalue.eq(type))
                        .buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
        String newsList = AppService.getsGson().toJson(index);
        GreenIndex greenIndex=new GreenIndex(null,newsList,type);
        greenIndexDao.insert(greenIndex);
    }
}
