package com.example.horry.footbasket.entity.football;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class League {
    public int id;
    public String label;
    public String prev;
    public String next;
    public int max;
    public int min;
    public int page;
    public List<Articles> articles;

    public static class Articles {
        public int id;
        public String title;
        public String share_title;
        public String description;
        public int comments_total;
        public String share;
        public String thumb;
        public String published_at;
        public boolean top;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<String> getExtend() {
            return extend;
        }

        public void setExtend(List<String> extend) {
            this.extend = extend;
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

        public Topic getTopic() {
            return topic;
        }

        public void setTopic(Topic topic) {
            this.topic = topic;
        }

        public Album getAlbum() {
            return album;
        }

        public void setAlbum(Album album) {
            this.album = album;
        }

        public Cover getCover() {
            return cover;
        }

        public void setCover(Cover cover) {
            this.cover = cover;
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

        public String url;
        public String url1;
        public String scheme;
        public String channel;
        public Cover cover;
        public Album album;
        public Topic topic;
        public String label;
        public String label_color;
        public List<String> extend;

        public static class Cover {
            public String pic;
        }

        public static class Album {
            public int total;
            public List<String> pics;
        }

        public static class Topic {
            public String id;
            public String thumb;
            public String content;
            public Group group;
            public Author author;

            public static class Group {
                public int id;
                public String title;
            }

            public static class Author {
                public int id;
                public String username;
            }
        }
    }
}
