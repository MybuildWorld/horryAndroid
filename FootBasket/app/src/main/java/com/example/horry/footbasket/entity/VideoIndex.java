package com.example.horry.footbasket.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/14.
 */
public class VideoIndex {
    public List<IndexBean> data;
    public static class IndexBean  {
        public String type;
        public String id;
        public String column;
        public String needUpdate;
    }
}
