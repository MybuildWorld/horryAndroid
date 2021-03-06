package com.horry.MicroBlog.mvp.view;

import com.horry.MicroBlog.entity.User;

import java.util.ArrayList;

/**
 * Created by wenmingvs on 16/5/18.
 */
public interface AccoutActivityView {
    public void updateListView(ArrayList<User> userArrayList);

    public void showListView();

    public void hideListView();

    public void showProgressDialog();

    public void hideProgressDialog();

    public void setUpListener();

    public void initListView(ArrayList<User> userArrayList);

    public void finishItself();
}
