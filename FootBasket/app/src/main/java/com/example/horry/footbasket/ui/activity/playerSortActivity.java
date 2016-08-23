package com.example.horry.footbasket.ui.activity;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.events.event;
import com.example.horry.footbasket.ui.widgets.SwipeBackLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/17.
 */
public class playerSortActivity extends BaseActivity {
//    @BindView(R.id.swipebacklayout)
//    SwipeBackLayout swipeBackLayout;
    @Override
    protected void initViews() {
//        swipeBackLayout.setCallBack(new SwipeBackLayout.CallBack() {
//            @Override
//            public void onFinish() {
//                finish();
//            }
//        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_playersort;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventMain(event event){

    }
}
