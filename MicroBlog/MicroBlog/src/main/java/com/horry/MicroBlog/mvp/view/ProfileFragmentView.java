package com.horry.MicroBlog.mvp.view;

import com.horry.MicroBlog.entity.User;

/**
 * Created by wenmingvs on 16/5/16.
 */
public interface ProfileFragmentView {

    public void setUserDetail(User user);

    public void showScrollView();

    public void hideScrollView();

    public void showProgressDialog();

    public void hideProgressDialog();

}
