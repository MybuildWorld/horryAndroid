package com.example.horry.footbasket.ui.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.horry.footbasket.app.AppService;

import butterknife.ButterKnife;

//这类封装了eventbus的注册于接触，初始化Fragment视图的接口
public abstract class BaseFragment extends Fragment {

    private int mTaskId;
    protected View rootView;
    protected abstract void initViews() ;

    protected abstract int getContentViewId();

    protected int getTaskId (){
        mTaskId=getActivity().getTaskId();
        return mTaskId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppService.getInstance().getsBus().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(getContentViewId(),container,false);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppService.getInstance().getsBus().unregister(this);
    }
}
