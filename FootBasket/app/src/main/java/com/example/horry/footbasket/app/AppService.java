package com.example.horry.footbasket.app;


import android.os.Handler;
import android.os.HandlerThread;

import com.example.horry.footbasket.RxMethod.RxGames;
import com.example.horry.footbasket.RxMethod.RxNbaVideoIndex;
import com.example.horry.footbasket.RxMethod.RxNews;
import com.example.horry.footbasket.RxMethod.RxStats;
import com.example.horry.footbasket.RxMethod.RxTeamSort;
import com.example.horry.footbasket.RxMethod.RxVideoItem;
import com.example.horry.footbasket.RxMethod.football.RxLeague;
import com.example.horry.footbasket.RxMethod.football.RxTop;
import com.example.horry.footbasket.RxMethod.football.RxVideo;
import com.example.horry.footbasket.network.Nba.NbaApi;
import com.example.horry.footbasket.network.Nba.NbaDetailApi;
import com.example.horry.footbasket.network.Factory;
import com.example.horry.footbasket.network.Nba.VideoIndexApi;
import com.example.horry.footbasket.network.football.FootballApi;
import com.example.horry.greendao.DbHelper;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import rx.subscriptions.CompositeSubscription;

public class AppService {
    private static final AppService FB_SERVICE =new AppService();
    private static Gson sGson;
    private static EventBus sBus ;
    private static DbHelper sDBHelper;
    private static NbaApi nbaApi;
    private static NbaDetailApi nbaDetailApi;
    private static FootballApi footballApi;
    private static VideoIndexApi videoIndexApi;
    private Map<Integer,CompositeSubscription> mCompositeSubByTaskId;
    private Handler mIoHandler;

    private AppService(){}
    public static AppService getInstance() {
        return FB_SERVICE;
    }

    void initService(){
        sBus=EventBus.getDefault();
        sGson=new Gson();
        mCompositeSubByTaskId=new HashMap<Integer,CompositeSubscription>();
        backGroundInit();
    }


    private void backGroundInit() {
        HandlerThread ioThread = new HandlerThread("IoThread");
        ioThread.start();
        mIoHandler= new Handler(ioThread.getLooper());
        mIoHandler.post(new Runnable() {
            @Override
            public void run() {
                nbaApi = Factory.getNbaApiInstance();
                nbaDetailApi = Factory.getNbaDetailApiInstance();
                footballApi = Factory.getFootballApiInstance();
                videoIndexApi = Factory.getVideoIndexApiInstance();
                sDBHelper = DbHelper.getInstance(App.getContext());
            }
        });}


    public void addCompositeSub(int taskId) {
        CompositeSubscription compositeSubscription;
        if(mCompositeSubByTaskId.get(taskId)==null) {
            compositeSubscription = new CompositeSubscription();
            mCompositeSubByTaskId.put(taskId, compositeSubscription);
        }
    }

    public void removeCompositeSub(int taskId) {
        CompositeSubscription compositeSubscription;
        if(mCompositeSubByTaskId!=null&& mCompositeSubByTaskId.get(taskId)!=null){
            compositeSubscription= mCompositeSubByTaskId.get(taskId);
            compositeSubscription.unsubscribe();
            mCompositeSubByTaskId.remove(taskId);
        }
    }

    private CompositeSubscription getCompositeSubscription(int taskId) {
        CompositeSubscription compositeSubscription ;
        if(mCompositeSubByTaskId.get(taskId)==null) {
            compositeSubscription = new CompositeSubscription();
            mCompositeSubByTaskId.put(taskId, compositeSubscription);
        }else {
            compositeSubscription= mCompositeSubByTaskId.get(taskId);
        }
        return compositeSubscription;
    }

    //篮球
    public void initNews(int taskId,String type) {
        getCompositeSubscription(taskId).add(RxNews.initNews(type));
    }

    public void updateNews(int taskId,String type) {
        getCompositeSubscription(taskId).add(RxNews.updateNews(type));
    }

    public void loadMoreNews(int taskId,String type,String newsId) {
        getCompositeSubscription(taskId).add(RxNews.loadMoreNews(type, newsId));
    }

    public void getNewsDetail(int taskId,String date,String detailId) {
        getCompositeSubscription(taskId).add(RxNews.getNewsDetail(date, detailId));
    }

    public void initPerStat(int taskId,String  statKind) {
        getCompositeSubscription(taskId).add(RxStats.initStat(statKind));
    }

    public void getPerStat(int taskId,String ...statKinds) {
        getCompositeSubscription(taskId).add(RxStats.getPerStat(statKinds));
    }

    public void getTeamSort(int taskId) {
        getCompositeSubscription(taskId).add(RxTeamSort.getTeams());
    }

    public void getGames(int taskId,String date) {
        getCompositeSubscription(taskId).add(RxGames.getTeams(date));
    }
    //足球
    public void initTop(int taskId){
        getCompositeSubscription(taskId).add(RxTop.initTop());
    }
    public void updateTop(int taskId){getCompositeSubscription(taskId).add(RxTop.updateTop());}
    public void loadMoreTop(int taskId,Map<String,String> next){getCompositeSubscription(taskId).add(RxTop.loadMoreTop(next));}

    public void initVideo(int taskId){getCompositeSubscription(taskId).add(RxVideo.initVideo());}
    public void updateVideo(int taskId){getCompositeSubscription(taskId).add(RxVideo.updateVideo());}
    public void loadMoreVideo(int taskId,Map<String,String> next){getCompositeSubscription(taskId).add(RxVideo.loadMoreVideo(next));}

    public void initLeague(int taskId,String type){getCompositeSubscription(taskId).add(RxLeague.initLeague(type));}
    public void updateLeague(int taskId,String type){getCompositeSubscription(taskId).add(RxLeague.updateLeague(type));}
    public void loadMoreLeague(int taskId,String type,Map<String,String> next){getCompositeSubscription(taskId).add(RxLeague.loadMoreLeague(type, next));}

    public void initIndex(int taskId,String type) {getCompositeSubscription(taskId).add(RxNbaVideoIndex.initVideoIndex(type));}
    public void updateIndex(int taskId,String type){getCompositeSubscription(taskId).add(RxNbaVideoIndex.updateIndex(type));}

    public void initVideoItem(int taskId,String type,String Ids){getCompositeSubscription(taskId).add(RxVideoItem.initVideo(type,Ids));}
    public void getVideoItem(int taskId,String type,String Ids){getCompositeSubscription(taskId).add(RxVideoItem.updateVideo(type,Ids));}

    public static NbaDetailApi getNbaDetailApi() {
        return nbaDetailApi;
    }

    public static DbHelper getsDBHelper() {
        return sDBHelper;
    }

    public Handler getmIoHandler() {
        return mIoHandler;
    }

    public static NbaApi getNbaApi() {
        return nbaApi;
    }

    public static FootballApi getFootballApi() {
        return footballApi;
    }
    public static VideoIndexApi getVideoIndexApi(){
        return videoIndexApi;
    }
    public static EventBus getsBus(){return  sBus;}
    public static Gson getsGson(){return sGson;}

}
