package com.example.horry.footbasket.RxMethod;

import android.util.Log;

import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.News;
import com.example.horry.footbasket.entity.NewsDetail;
import com.example.horry.footbasket.events.NewsDtailEvent;
import com.example.horry.footbasket.events.NewsEvent;
import com.example.horry.footbasket.utils.PreferenceUtil;
import com.example.horry.greendao.GreenNews;
import com.example.horry.greendao.GreenNewsDao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/7.
 */
public class RxNews {
    public static Subscription initNews(final String newsType) {

        return Observable.create(new Observable.OnSubscribe<News>() {
            @Override
            public void call(Subscriber<? super News> subscriber) {
                News news = getCacheNews(newsType);
                subscriber.onNext(news);
                subscriber.onCompleted();
                Log.i("RxNews", Thread.currentThread() + ";;;create");
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        NewsEvent newsEvent= new NewsEvent(news, constant.GETNEWSWAY.INIT,newsType);
                        if(news==null) {
                            newsEvent.setEventResult(constant.Result.FAIL);
                        }
                        AppService.getsBus().post(newsEvent);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsEvent newsEvent= new NewsEvent(new News(), constant.GETNEWSWAY.INIT,newsType);
                        newsEvent.setEventResult(constant.Result.FAIL);
                        AppService.getsBus().post(newsEvent);
                    }
                });
    }
    public static Subscription updateNews(final String newsType) {
        return  AppService.getNbaApi().updateNews(newsType)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        cacheNews(news, newsType);
                        Log.i("RxNews", Thread.currentThread() + ";;;donOnNext");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        Log.i("RxNews",Thread.currentThread()+";;;subscribe");
                        AppService.getsBus().post(new NewsEvent(news, constant.GETNEWSWAY.UPDATE,newsType));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsEvent newsEvent= new NewsEvent(new News(), constant.GETNEWSWAY.UPDATE,newsType);
                        newsEvent.setEventResult(constant.Result.FAIL);
                        AppService.getsBus().post(newsEvent);
                    }
                });
    }

    public static Subscription loadMoreNews(final String newsType,final String newsId) {
        return AppService.getNbaApi().loadMoreNews(newsType, newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        AppService.getsBus().post(new NewsEvent(news, constant.GETNEWSWAY.LOADMORE,newsType));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsEvent newsEvent= new NewsEvent(new News(), constant.GETNEWSWAY.LOADMORE,newsType);
                        newsEvent.setEventResult(constant.Result.FAIL);
                        AppService.getsBus().post(newsEvent);
                    }
                });
    }
    private static void cacheNews(final News news,final String newsType) {

        GreenNewsDao greenNewsDao = AppService.getsDBHelper().getDaoSession().getGreenNewsDao();
        DeleteQuery deleteQuery = greenNewsDao.queryBuilder()
                .where(GreenNewsDao.Properties.Type.eq(newsType))
                .buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
        String newsList = AppService.getsGson().toJson(news);
        GreenNews greenNews = new GreenNews(null, newsList, newsType);
        greenNewsDao.insert(greenNews);

    }

    public static Subscription getNewsDetail(final String date,final String detailId) {
        return AppService.getNbaDetailApi().getNewsDetile(date, detailId + ".json")
                .subscribeOn(Schedulers.io())
                .map(new Func1<NewsDetail, String>() {
                    @Override
                    public String call(NewsDetail newsDetile) {
                        int imageCount = 0;
                        String assStyle = String.format(constant.CSS_STYLE, PreferenceUtil.getPrefString(App.getContext()
                                , constant.ACTILEFONTSIZE, "18"), "#333333");
                        String html = "<html><head>" + assStyle + "</head><body>"
                                + newsDetile.getContent() + "<p>（" + newsDetile.getAuthor() + "）<p></body></html>";
                        Pattern p = Pattern.compile("(<p class=\"reader_img_box\"><img id=\"img_\\d\" class=\"reader_img\" src=\"reader_img_src\" /></p>)+");
                        Matcher m = p.matcher(html);
                        while (m.find() && imageCount < newsDetile.getArticleMediaMap().size()) {
                            if (imageCount == 0) {
                                html = html.replace(html.substring(m.start(), (m.end())), "");
                            } else {
                                if (PreferenceUtil.getPrefBoolean(App.getContext(), constant.LOADIMAGE, true)) {
                                    html = html.replace(html.substring(m.start(), (m.end())), "<img src="
                                            + newsDetile.getArticleMediaMap().get(String.format("img_%d", imageCount)).getUrl() + " "
                                            + "width=\"100%\" height=\"auto\">");
                                }
                            }
                            imageCount++;
                            m = p.matcher(html);
                        }
                        return html;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String newsContent) {
                        AppService.getsBus().post(new NewsDtailEvent(newsContent));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsDtailEvent newsDetailEvent = new NewsDtailEvent(throwable.toString());
                        newsDetailEvent.setEventResult(constant.Result.FAIL);
                        AppService.getsBus().post(newsDetailEvent);
                    }
                });
    }

    private static News getCacheNews(String newsType) {
        News news=null;
        GreenNewsDao greenNewsDao = AppService.getsDBHelper().getDaoSession().getGreenNewsDao();
        Query query = greenNewsDao.queryBuilder()
                .where(GreenNewsDao.Properties.Type.eq(newsType))
                .build();
        // 查询结果以 List 返回
        List<GreenNews> greenNewses = query.list();
        if(greenNewses!=null&&greenNewses.size()>0) {
            news = AppService.getsGson().fromJson(greenNewses.get(0).getNewslist(), News.class);
        }
        return news;
    }
}
