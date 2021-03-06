package com.horry.MicroBlog.mvp.presenter;

import android.content.Context;

import com.horry.MicroBlog.entity.Status;

/**
 * Created by wenmingvs on 16/6/26.
 */
public interface DetailActivityPresent {
    public void pullToRefreshData(int groupId, Status status, Context context);

    public void requestMoreData(int groupId, Status status, Context context);
}
