package com.example.horry.footbasket.data;

import com.example.horry.footbasket.R;

import java.util.HashMap;
import java.util.Map;


public class constant {

    // 射手榜
    public static final String URL_SCORER_CHINA = "https://api.dongqiudi.com/data/goal_ranking/51";   //中超
    public static final String URL_SCORER_ENGLAND = "https://api.dongqiudi.com/data/goal_ranking/8"; //英超
    public static final String URL_SCORER_SPAIN = "https://api.dongqiudi.com/data/goal_ranking/7";   //西甲
    public static final String URL_SCORER_GERMANY = "https://api.dongqiudi.com/data/goal_ranking/9"; //德甲
    public static final String URL_SCORER_ITALY = "https://api.dongqiudi.com/data/goal_ranking/13";   //意甲
    //积分榜
    public static final String URL_RANK_CHINA = "https://api.dongqiudi.com/data/team_ranking/51";   //中超
    public static final String URL_RANK_ENGLAND = "https://api.dongqiudi.com/data/team_ranking/8"; //英超
    public static final String URL_RANK_SPAIN = "https://api.dongqiudi.com/data/team_ranking/7";   //西甲
    public static final String URL_RANK_GERMANY = "https://api.dongqiudi.com/data/team_ranking/9"; //德甲
    public static final String URL_RANK_ITALY = "https://api.dongqiudi.com/data/team_ranking/13";   //意甲
    //助攻榜
    public static final String URL_ASSIST_CHINA = "https://api.dongqiudi.com/data/assist_ranking/51";   //中超
    public static final String URL_ASSIST_ENGLAND = "https://api.dongqiudi.com/data/assist_ranking/8"; //英超
    public static final String URL_ASSIST_SPAIN = "https://api.dongqiudi.com/data/assist_ranking/7";   //西甲
    public static final String URL_ASSIST_GERMANY = "https://api.dongqiudi.com/data/assist_ranking/9"; //德甲
    public static final String URL_ASSIST_ITALY = "https://api.dongqiudi.com/data/assist_ranking/13";   //意甲
    public static final String APP_FIR_IM_URL="http://fir.im/nbaplus";
    public static final String API_TOKEN_FIR="ff55b0c5cb165ec0b04c473cf77c8995";

    public static final String LOADIMAGE = "LOADIMAGE";
    public static final String ACTILEFONTSIZE = "ACTILEFONTSIZE";

    public static final String CSS_STYLE ="<style>* {font-size:%spx;line-height:28px;}p {color:%s;}</style>";

    public final static String[] TEAM_NAMES={"骑士","猛龙","老鹰","步行者","热火","活塞","公牛","魔术"
            ,"黄蜂","凯尔特人","尼克斯","奇才","雄鹿","篮网","76人","勇士","马刺","雷霆","快船","小牛"
            ,"灰熊","火箭","爵士","太阳","掘金","森林狼","国王","开拓者","鹈鹕","湖人"};
    public final static int[] TEAM_ICONS={R.mipmap.cleveland,R.mipmap.toronto, R.mipmap.atlanta,R.mipmap.indiana
            ,R.mipmap.miami,R.mipmap.detroit,R.mipmap.chicago,R.mipmap.orlando,R.mipmap.charlotte,R.mipmap.boston
            ,R.mipmap.newyork,R.mipmap.washington,R.mipmap.milwaukee,R.mipmap.brooklyn,R.mipmap.phila,R.mipmap.goldenstate
            ,R.mipmap.sanantonio,R.mipmap.okc,R.mipmap.laclippers,R.mipmap.dallas,R.mipmap.memphis,R.mipmap.houston
            ,R.mipmap.utah,R.mipmap.phoenix,R.mipmap.denver,R.mipmap.minnesota,R.mipmap.sacramento,R.mipmap.portland
            ,R.mipmap.neworleans,R.mipmap.lalakers};

    private static final Map<String,Integer> sTeamIconMap=new HashMap<>();

    static {
        for (int index=0;index<TEAM_NAMES.length;index++){
            sTeamIconMap.put(TEAM_NAMES[index],TEAM_ICONS[index]);
        }
    }

    public static Map<String,Integer>  getTeamIcons()  {
        return sTeamIconMap;
    }

    public enum NEWSTYPE {
        NEWS("news"),BLOG("blog");
        private String newsType;
        NEWSTYPE(String newsType) {
            this.newsType=newsType;
        }
        public String getNewsType() {
            return newsType;
        }
    }

    public enum GETNEWSWAY {
        INIT,UPDATE,LOADMORE;
    }

    public enum Result {
        SUCCESS,FAIL,NORMAL;
    }

}
