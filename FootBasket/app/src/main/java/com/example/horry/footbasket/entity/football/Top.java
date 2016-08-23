package com.example.horry.footbasket.entity.football;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class Top {
    /*
    {
        "id": 43,
            "label": "视频",
            "prev": "http://api.dongqiudi.com/app/tabs/android/43.json?before=1470843004",
            "next": "http://api.dongqiudi.com/app/tabs/android/43.json?after=1470739397&page=2",
            "max": 1470843004,
            "min": 1470739397,
            "page": 1,
            "articles": [
        {
            "id": 206174,
                "title": "快到不可思议，贝莱林季前赛的高速攻防集锦",
                "share_title": "快到不可思议，贝莱林季前赛的高速攻防集锦",
                "comments_total": 19,
                "share": "http://www.dongqiudi.com/article/206174",
                "thumb": "http://img1.dongqiudi.com/fastdfs/M00/02/7C/180x135/crop/-/oYYBAFep_aSARfCGAAAppyvCANg307.jpg",
                "top": false,
                "url": "https://api.dongqiudi.com/article/206174.html",
                "url1": "https://api.dongqiudi.com/article/206174.html",
                "scheme": "dongqiudi:///news/206174",
                "is_video": true,
                "published_at": "2016-08-10 23:30:04",
                "channel": "video"},.....],
            "recommend": [
            {
                "id": 206170,
                    "title": "五场三球，格鲁伊奇季前赛个人集锦",
                    "share_title": "五场三球，格鲁伊奇季前赛个人集锦",
                    "comments_total": 42,
                    "share": "http://www.dongqiudi.com/article/206170",
                    "thumb": "http://img1.dongqiudi.com/fastdfs/M00/02/7B/640x400/-/-/ooYBAFep-pqAQvj5AAIwCkKLOKA376.jpg",
                    "top": false,
                    "url": "https://api.dongqiudi.com/article/206170.html",
                    "url1": "https://api.dongqiudi.com/article/206170.html",
                    "scheme": "dongqiudi:///news/206170",
                    "is_video": true,
                    "published_at": "2016-08-10 20:40:19",
                    "channel": "video",
                    "label": "视频",
                    "label_color": "#4da463"
            },......
            ]
        }
        */
            public int id;
    public String label;
    public String prev;
    public String next;
    public int max;
    public int min;
    public int page;
    public List<Article> articles;
    public List<Recommend> recommend;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Recommend> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<Recommend> recommend) {
        this.recommend = recommend;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public static class Article {
        public int id;
        public String title;
        public String share_title;
        public String description;
        public int comments_total;
        public String share;
        public String thumb;
        public String published_at;
        public boolean top;
        public String url;
        public String url1;
        public String scheme;
        public String channel;
        public String label;
        public String label_color;
        public Album album;

        public static class Album {
            public int total;
            public List<String> pics;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Album getAlbum() {
            return album;
        }

        public void setAlbum(Album album) {
            this.album = album;
        }

        public String getLabel_color() {
            return label_color;
        }

        public void setLabel_color(String label_color) {
            this.label_color = label_color;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getScheme() {
            return scheme;
        }

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        public String getUrl1() {
            return url1;
        }

        public void setUrl1(String url1) {
            this.url1 = url1;
        }

        public boolean isTop() {
            return top;
        }

        public void setTop(boolean top) {
            this.top = top;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPublished_at() {
            return published_at;
        }

        public void setPublished_at(String published_at) {
            this.published_at = published_at;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public int getComments_total() {
            return comments_total;
        }

        public void setComments_total(int comments_total) {
            this.comments_total = comments_total;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getShare_title() {
            return share_title;
        }

        public void setShare_title(String share_title) {
            this.share_title = share_title;
        }

        public void setId(int id) {

            this.id = id;
        }
    }

    public static class Recommend {
        public int id;
        public String title;
        public String share_title;
        public String description;
        public int comments_total;
        public String share;
        public String thumb;
        public String published_at;
        public boolean top;
        public String url;
        public String label;
        public String url1;
        public String scheme;
        public String channel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getScheme() {
            return scheme;
        }

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        public String getUrl1() {
            return url1;
        }

        public void setUrl1(String url1) {
            this.url1 = url1;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isTop() {
            return top;
        }

        public void setTop(boolean top) {
            this.top = top;
        }

        public String getPublished_at() {
            return published_at;
        }

        public void setPublished_at(String published_at) {
            this.published_at = published_at;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public int getComments_total() {
            return comments_total;
        }

        public void setComments_total(int comments_total) {
            this.comments_total = comments_total;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getShare_title() {
            return share_title;
        }

        public void setShare_title(String share_title) {
            this.share_title = share_title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
