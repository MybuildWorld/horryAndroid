package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by Administrator on 2016/8/7.
 */
public class DaoGeneration {
    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(1, "com.example.horry.greendao");
        addNews(schema);
        addStats(schema);
        addTop(schema);
        addIndex(schema);
        addItem(schema);
        addLeague(schema);
        addVideo(schema);
        new DaoGenerator().generateAll(schema, "/programme/OpenSource/FootBasket/app/src/main/java-gen");
    }

    public static void addNews(Schema schema) {
        Entity news = schema.addEntity("GreenNews");
        news.addIdProperty();
        news.addStringProperty("newslist");
        news.addStringProperty("type");
    }

    public static void addStats(Schema schema) {
        Entity stat = schema.addEntity("GreenStat");
        stat.addIdProperty();
        stat.addStringProperty("statentity");
        stat.addStringProperty("statkind");
    }
    public static void addTop(Schema schema){
        Entity top=schema.addEntity("GreenTop");
        top.addIdProperty();
        top.addStringProperty("topentity");
        top.addStringProperty("typekind");
    }
    public static void addIndex(Schema schema){
        Entity index=schema.addEntity("GreenIndex");
        index.addIdProperty();
        index.addStringProperty("indexentity");
        index.addStringProperty("typevalue");
    }

    public static void addItem(Schema schema){
        Entity item=schema.addEntity("GreenItem");
        item.addIdProperty();
        item.addStringProperty("itementity");
        item.addStringProperty("Ids");
    }

    public static void addLeague(Schema schema){
        Entity league=schema.addEntity("GreenLeague");
        league.addIdProperty();
        league.addStringProperty("leagueentity");
        league.addStringProperty("kinds");
    }

    public static void addVideo(Schema schema){
        Entity video=schema.addEntity("GreenVideo");
        video.addIdProperty();
        video.addStringProperty("videoentity");
        video.addStringProperty("kindtype");
    }
}
