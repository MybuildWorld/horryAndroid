package com.example.horry.footbasket.events;

import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.football.Video;

/**
 * Created by Administrator on 2016/8/11.
 */
public class VideoEvent extends event{
    Video video;
    constant.GETNEWSWAY getVideosway;
    public VideoEvent(Video video,constant.GETNEWSWAY getVideosway){
        this.video=video;
        this.getVideosway=getVideosway;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public constant.GETNEWSWAY getGetVideosway() {
        return getVideosway;
    }

    public void setGetVideosway(constant.GETNEWSWAY getVideosway) {
        this.getVideosway = getVideosway;
    }
}
