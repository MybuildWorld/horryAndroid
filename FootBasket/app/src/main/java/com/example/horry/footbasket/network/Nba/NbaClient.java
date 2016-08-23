package com.example.horry.footbasket.network.Nba;


import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.entity.VideoRealUrl;
import com.example.horry.footbasket.entity.nbaVideoItem;
import com.example.horry.footbasket.utils.ParseUtil;
import com.example.horry.footbasket.utils.PullRealUrlParser;
import com.example.horry.footbasket.utils.RequestCallback;
import com.example.horry.greendao.GreenItem;
import com.example.horry.greendao.GreenItemDao;
import com.example.horry.greendao.GreenNews;
import com.example.horry.greendao.GreenNewsDao;
import com.google.gson.JsonParser;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2016/8/3.
 */
public class NbaClient {
    final NbaApi nbaApi;
    final NbaDetailApi nbaDetailApi;
    final VideoIndexApi videoIndexApi;
    public  NbaClient(){
        Retrofit retrofit0 = new Retrofit.Builder()
                .baseUrl("http://nbaplus.sinaapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        nbaApi=retrofit0.create(NbaApi.class);

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("http://reader.res.meizu.com/reader/articlecontent/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nbaDetailApi=retrofit1.create(NbaDetailApi.class);

        Retrofit retrofit2=new Retrofit.Builder()
                .baseUrl("http://sportsnba.qq.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        videoIndexApi=retrofit2.create(VideoIndexApi.class);
    }

    public NbaApi getNbaApi() {
        return nbaApi;
    }

    public NbaDetailApi getNbaDetailApi() {
        return nbaDetailApi;
    }

    public VideoIndexApi getVideoIndexApi(){return videoIndexApi;}
    public static OkHttpClient getClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true) ;// 失败重发
//                .addInterceptor(logging)
//                .addInterceptor(new CommonParamsInterceptor());
        return builder.build();
    }
    public static void getVideoRealUrl(String vid, final RequestCallback<VideoRealUrl> cbk) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vv.video.qq.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(getClient())
                .build();
        VideoRealUrlApi api=retrofit.create(VideoRealUrlApi.class);
        Call<String> call = api.getVideoRealUrl(vid);
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response != null && !TextUtils.isEmpty(response.body())) {
                    String xmlStr = response.body();
                    PullRealUrlParser parser = new PullRealUrlParser();
                    try {
                        VideoRealUrl url = parser.parse(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
                        cbk.onSuccess(url);
                    } catch (Exception e) {
                        Log.e("TAG","解析xml异常:" + e.getMessage());
                        cbk.onFailure("解析出错");
                    }
                } else {
                    cbk.onFailure("获取数据失败");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                cbk.onFailure(t.getMessage());
            }
        });
    }
    public static void getNewsItem(final String type, final String articleIds,boolean isfresh,final RequestCallback<nbaVideoItem> cbk){
        nbaVideoItem item=getCacheItem(type);
        if(item!=null&&!isfresh){
            cbk.onSuccess(item);
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sportsnba.qq.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(getClient())
                .build();
        VideoRealUrlApi api=retrofit.create(VideoRealUrlApi.class);
        Call<String> call = api.getNewsItem(type, articleIds);
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.d("Item",response.body());
                if (response != null && !TextUtils.isEmpty(response.body())) {
                    String jsonStr = response.body();
                    nbaVideoItem newsItem = ParseUtil.parseNewsItem(jsonStr);
                    cbk.onSuccess(newsItem);
                    cacheItem(newsItem,articleIds);
                } else {
                    cbk.onFailure("获取数据失败");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                cbk.onFailure(t.getMessage());
            }
        });
    }

    public static nbaVideoItem getCacheItem(String Ids){
        nbaVideoItem item=null;
        GreenItemDao greenItemDao= AppService.getsDBHelper().getDaoSession().getGreenItemDao();
        Query query=greenItemDao.queryBuilder()
                .where(GreenItemDao.Properties.Ids.eq(Ids)).build();
        List<GreenItem> list=query.list();
        if(list!=null&&list.size()>0){
            item=AppService.getsGson().fromJson(list.get(0).getIds(),nbaVideoItem.class);
        }
        return item;
    }
    public static void cacheItem(nbaVideoItem item,String Ids){
        GreenItemDao greenItemDao= AppService.getsDBHelper().getDaoSession().getGreenItemDao();
        DeleteQuery deleteQuery = greenItemDao.queryBuilder()
                .where(GreenItemDao.Properties.Ids.eq(Ids))
                .buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
        String itemlist = AppService.getsGson().toJson(item);
        GreenItem greenItem=new GreenItem(null,itemlist,Ids);
        greenItemDao.insert(greenItem);
    }

}


