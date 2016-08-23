package com.example.horry.footbasket.entity.football;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class Video {
    public int id;
    public String label;
    public int max;
    public int min;
    public String next;
    public int page;
    public String prev;
    public List<Articles> articles;

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public static class Articles {
        public String channel;
        public int comments_total;
        public int id;
        public String published_at;
        public String scheme;
        public String share;
        public String share_title;
        public String thumb;
        public String title;
        public boolean top;
        public String url;
        public String url1;
        public String label;
    }
}
