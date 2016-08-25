package com.horry.MicroBlog.mvp.presenter.imp;

import android.content.Context;

import com.horry.MicroBlog.entity.Group;
import com.horry.MicroBlog.mvp.model.GroupListModel;
import com.horry.MicroBlog.mvp.model.imp.GroupListModelImp;
import com.horry.MicroBlog.mvp.presenter.GroupListPresenter;
import com.horry.MicroBlog.mvp.view.GroupPopWindowView;
import com.horry.MicroBlog.mvp.view.HomeFragmentView;

import java.util.ArrayList;

/**
 * Created by wenmingvs on 16/5/14.
 */
public class GroupListPresenterImp implements GroupListPresenter {

    private GroupPopWindowView mGroupPopView;
    private GroupListModel mGroupListModel;
    private HomeFragmentView mHomeFragmentView;

    public GroupListPresenterImp(GroupPopWindowView groupPopView) {
        this.mGroupPopView = groupPopView;
        this.mGroupListModel = new GroupListModelImp();
    }

    @Override
    public void updateGroupList(final Context context) {
        mGroupListModel.groupsOnlyOnce(context, new GroupListModel.OnGroupListFinishedListener() {
            @Override
            public void noMoreDate() {
                
            }

            @Override
            public void onDataFinish(ArrayList<Group> groupslist) {
                mGroupPopView.updateListView(groupslist);
            }

            @Override
            public void onError(String error) {
                mGroupPopView.showErrorMessage(error);
            }

        });
    }


}
