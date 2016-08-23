package com.example.horry.greendao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Config;


import de.greenrobot.dao.query.QueryBuilder;

public class DbHelper {
    private static DbHelper sInstance;
    private static final Object WATCH_DOG=new Object();
    private static final String DB_NAME = "footbasket";
    private DaoSession daoSession;
    private SQLiteDatabase db;
    public static DbHelper getInstance(Context context) {
        synchronized (WATCH_DOG) {
            if(sInstance==null) {
                sInstance=new DbHelper(context);
            }
            return sInstance;
        }
    }

    private DbHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        if(Config.DEBUG) {
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        }
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

}
