package com.example.horry.footbasket.RxMethod.football;

import android.util.Log;

import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.football.Video;
import com.example.horry.footbasket.events.VideoEvent;
import com.example.horry.greendao.GreenVideo;
import com.example.horry.greendao.GreenVideoDao;

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
public class RxVideo {
    public final static String key="fb";
    public static Subscription initVideo(){
        return Observable.create(new Observable.OnSubscribe<Video>() {
            @Override
            public void call(Subscriber<? super Video> subscriber) {
                Video video=getCacheVideo(key);
                subscriber.onNext(video);
                subscriber.onCompleted();
                Log.i("RxVideo", Thread.currentThread() + ";;;create");
            }
        }).subscribeOn(Schedulers.io())
          .subscribe(new Action1<Video>() {
              @Override
              public void call(Video video) {
                  VideoEvent event=new VideoEvent(video, constant.GETNEWSWAY.INIT);
                  if(video==null){
                      event.setEventResult(constant.Result.FAIL);
                  }
                  AppService.getsBus().post(event);
              }
          }, new Action1<Throwable>() {
              @Override
              public void call(Throwable throwable) {
                  Video video=new Video();
                  VideoEvent event=new VideoEvent(video, constant.GETNEWSWAY.INIT);
                  event.setEventResult(constant.Result.FAIL);
                  AppService.getsBus().post(event);
              }
          });
    }

    public static Subscription updateVideo(){
        return AppService.getFootballApi().getVideo()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Video>() {
                    @Override
                    public void call(Video video) {
                        cacheVideo(video,key);
                        Log.i("RxVideo", Thread.currentThread() + ";;;donOnNext");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Video>() {
                               @Override
                               public void call(Video video) {
                                   Log.i("RxVideo",Thread.currentThread()+";;;subscribe");
                                   AppService.getsBus().post(new VideoEvent(video, constant.GETNEWSWAY.UPDATE));
                               }
                           },
                           new Action1<Throwable>() {
                              @Override
                               public void call(Throwable throwable) {
                                    VideoEvent event=new VideoEvent(new Video(), constant.GETNEWSWAY.UPDATE);
                                    event.setEventResult(constant.Result.FAIL);
                                    AppService.getsBus().post(event);
                            }
                        });
    }

    public static Subscription loadMoreVideo(Map<String,String> next){
        return AppService.getFootballApi().loadMoreVideo(next)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Video>() {
                    @Override
                    public void call(Video video) {
                        AppService.getsBus().post(new VideoEvent(video, constant.GETNEWSWAY.LOADMORE));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        VideoEvent event=new VideoEvent(new Video(), constant.GETNEWSWAY.LOADMORE);
                        event.setEventResult(constant.Result.FAIL);
                        AppService.getsBus().post(event);
                    }
                });
    }

    public static void cacheVideo(final Video video,final String type){
        GreenVideoDao greenVideoDao=AppService.getsDBHelper().getDaoSession().getGreenVideoDao();
        DeleteQuery deleteQuery=greenVideoDao.queryBuilder()
                .where(GreenVideoDao.Properties.Kindtype.eq(type))
                .buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
        String videos=AppService.getsGson().toJson(video);
        GreenVideo greenVideo=new GreenVideo(null,videos,type);
        greenVideoDao.insert(greenVideo);
    }

    public static Video getCacheVideo(String type){
        Video video=null;
        GreenVideoDao greenVideoDao=AppService.getsDBHelper().getDaoSession().getGreenVideoDao();
        Query<GreenVideo> deleteQuery=greenVideoDao.queryBuilder()
                .where(GreenVideoDao.Properties.Kindtype.eq(type))
                .build();
        List<GreenVideo> list=deleteQuery.list();
        if(list!=null&&list.size()>0){
            video=AppService.getsGson().fromJson(list.get(0).getVideoentity(),Video.class);
        }
        return video;
    }
}
