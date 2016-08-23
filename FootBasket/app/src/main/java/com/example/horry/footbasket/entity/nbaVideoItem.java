package com.example.horry.footbasket.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/14.
 */
public class nbaVideoItem extends Base {
    /**
     * 20160603041445 : {"atype":"0","title":"詹皇：今天的比赛属于利文斯顿","abstract":"骑士的板凳输出乏力，仅得10分，勇士则多点开花。","imgurl":"http://inews.gtimg.com/newsapp_ls/0/333422118_640470/0","imgurl2":"http://inews.gtimg.com/newsapp_ls/0/333422118_150120/0","newsId":"20160603041445","url":"http://nbachina.qq.com/a/20160603/041445.htm","commentId":"1422192316","pub_time":"2016-06-03 15:02","column":"news","vid":"","duration":"","img_count":"0","images_3":[],"footer":""}
     */

    public List<NewsItemBean> data;

    /**
     * version : c020d3303db6179626d1f8c7fc77acfd
     */

    public static class NewsItemBean  {

        /*"atype":"2",
        "title":"视频-史蒂芬森2015-16赛季劲爆表现合集",
        "abstract":"自由球员兰斯-史蒂芬森迟迟未和任何球队达成协议，有报道称他将...",
        "imgurl":"http://inews.gtimg.com/newsapp_ls/0/482442952_640470/0",
        "imgurl2":"http://inews.gtimg.com/newsapp_ls/0/482442952_150120/0",
        "newsId":"20160812027889",
        "url":"http://nbachina.qq.com/a/20160812/026708.htm",
        "commentId":"1499602700",
        "pub_time":"2016-08-12 11:59",
        "column":"videos",
        "vid":"c0021h0zy2h",
        "duration":"09:25",
        "img_count":"0",
        "images_3":
        */
        public String index;
        public String atype; // 0：新闻  1:多图模式  2:视频 h5:banner
        public String title;
        public String abstractX;
        public String imgurl;
        public String imgurl2;
        public String newsId;
        public String url;
        public String commentId;
        public String pub_time;
        public String column;
        public String vid;
        public String duration;
        public String img_count;
        public String footer;
        public List<String> images_3;

        public String realUrl; // 存放腾讯视频真实地址

        public NewsItemBean(String imgurl, String title, String pub_time) {
            this.imgurl = imgurl;
            this.title = title;
            this.pub_time = pub_time;
        }
    }
}
