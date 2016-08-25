package com.horry.MicroBlog.mvp.presenter.imp;

import android.content.Context;

import com.horry.MicroBlog.entity.Status;
import com.horry.MicroBlog.mvp.model.UserModel;
import com.horry.MicroBlog.mvp.model.imp.UserModelImp;
import com.horry.MicroBlog.mvp.presenter.MyWeiBoActivityPresent;
import com.horry.MicroBlog.mvp.view.MyWeiBoActivityView;

import java.util.ArrayList;

/**
 * Created by wenmingvs on 16/5/16.
 */
public class MyWeiBoActivityPresentImp implements MyWeiBoActivityPresent {
    private UserModel mUserModel;
    private MyWeiBoActivityView mMyWeiBoActivityView;

    public MyWeiBoActivityPresentImp(MyWeiBoActivityView myWeiBoActivityView) {
        this.mMyWeiBoActivityView = myWeiBoActivityView;
        this.mUserModel = new UserModelImp();
    }

    @Override
    public void pullToRefreshData(long uid, int groupId, Context context) {
        mMyWeiBoActivityView.showLoadingIcon();
        mUserModel.userTimeline(uid, groupId, context, pullToRefreshListener);
    }

    @Override
    public void requestMoreData(long uid, int groupId, Context context) {
        mUserModel.userTimelineNextPage(uid, groupId, context, requestMoreDataListener);
    }

    public UserModel.OnStatusListFinishedListener pullToRefreshListener = new UserModel.OnStatusListFinishedListener() {
        @Override
        public void noMoreDate() {
            mMyWeiBoActivityView.hideLoadingIcon();
        }

        @Override
        public void onDataFinish(ArrayList<Status> list) {
            mMyWeiBoActivityView.hideLoadingIcon();
            mMyWeiBoActivityView.scrollToTop(false);
            mMyWeiBoActivityView.updateListView(list);
        }

        @Override
        public void onError(String error) {
            mMyWeiBoActivityView.hideLoadingIcon();
            mMyWeiBoActivityView.showErrorFooterView();
        }
    };

    public UserModel.OnStatusListFinishedListener requestMoreDataListener = new UserModel.OnStatusListFinishedListener() {
        @Override
        public void noMoreDate() {
            mMyWeiBoActivityView.showEndFooterView();
        }

        @Override
        public void onDataFinish(ArrayList<Status> list) {
            mMyWeiBoActivityView.hideFooterView();
            mMyWeiBoActivityView.updateListView(list);
        }

        @Override
        public void onError(String error) {
            mMyWeiBoActivityView.showErrorFooterView();
        }
    };
}
