package com.example.horry.footbasket.utils;

import com.example.horry.footbasket.entity.VideoRealUrl;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/8/14.
 */
public interface RealUrlParser {
    VideoRealUrl parse(InputStream is) throws Exception;
}
