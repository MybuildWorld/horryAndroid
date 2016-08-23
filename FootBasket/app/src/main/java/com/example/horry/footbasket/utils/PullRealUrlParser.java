package com.example.horry.footbasket.utils;

import android.text.TextUtils;
import android.util.Xml;

import com.example.horry.footbasket.entity.VideoRealUrl;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/8/14.
 */
public class PullRealUrlParser implements RealUrlParser{
    @Override
    public VideoRealUrl parse(InputStream is) throws Exception {

        VideoRealUrl real = new VideoRealUrl();

        XmlPullParser parser = Xml.newPullParser(); //由android.util.Xml创建一个XmlPullParser实例
        parser.setInput(is, "UTF-8");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:

                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("url")) {
                        String urlbase = parser.nextText();
                        if ((urlbase.contains(".tc.qq.com"))
                                && TextUtils.isEmpty(real.url)) {
                            real.url = urlbase;
                        }
                    } else if (parser.getName().equals("fvkey")) {
                        String vkey = parser.nextText();
                        real.fvkey = vkey;
                    } else if (parser.getName().equals("vid")) {
                        String vid = parser.nextText();
                        real.vid = vid;
                    } else if (parser.getName().equals("fn")) { // 目前发现直接用{vid}.mp4 有部分不能播放，用fn下的可以
                        String fn = parser.nextText();
                        if (fn.endsWith(".mp4")) {
                            real.fn = fn;
                        }
                    }

                    break;

                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = parser.next();
        }
        return real;
    }
}
