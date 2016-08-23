package com.example.horry.footbasket.ui.Fragment.BasketFragment.playerSort;

import android.animation.Animator;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.events.RhxthmEvent;
import com.example.horry.footbasket.events.StatEvent;
import com.example.horry.footbasket.events.event;
import com.example.horry.footbasket.ui.Fragment.FragmentAdapter.StatPagerAdapter;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.base.ToolBarFragment;
import com.example.horry.footbasket.ui.widgets.RhxthmAdapter;
import com.example.horry.footbasket.ui.widgets.RhxthmLayout;
import com.example.horry.footbasket.utils.AnimatorUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/4.
 */
public class StatisticFragment extends ToolBarFragment implements View.OnClickListener {
    @BindView(R.id.rl_playersort)
    View rl_playersort;
    @BindView(R.id.box_rhythm)
    RhxthmLayout mRhythmLayout;
    @BindView(R.id.player_page)
    ViewPager mViewPager;
    @BindView(R.id.refresh)
    ImageView mRefresh;
    List<BarFragment> mBatFrgments=new ArrayList<>(5);
    private int mCurrentPosition;
    private Animator mAnimator;
    private static final String[] sStatKinds={"points","reb","assi","ste","blk"};
    private static final int[] sChartColors={Color.parseColor("#26a69a"),Color.parseColor("#5c6bc0"),
            Color.parseColor("#42a5f5"), Color.parseColor("#4dd0e1"),Color.parseColor("#66bb6a")};
    public static StatisticFragment newInstance(){
        return new StatisticFragment();
    }


    @Override
    protected void initViews() {
        super.initViews();
        for (int index=0;index<sStatKinds.length;index++) {
            mBatFrgments.add(BarFragment.newInstace(sStatKinds[index],sChartColors[index]));
        }
        RhxthmAdapter adapter = new RhxthmAdapter(getContext(),sChartColors);
        mRhythmLayout.setAdapter(adapter);
        mViewPager.setAdapter(new StatPagerAdapter(getChildFragmentManager(), mBatFrgments));
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mRhythmLayout.showRhythmAtPosition(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mRefresh.setOnClickListener(this);
        refreshData();
    }

    private void refreshData() {
        if(mAnimator!=null) {
            return;
        }
        mAnimator= AnimatorUtil.animRotation(mRefresh, 500);
        AppService.getInstance().getPerStat(getTaskId(), sStatKinds);
    }
    @Override
    protected int getContentViewId() {
        return R.layout.layout_playersort;
    }

    // no need initdata temporary
    private void initData() {

        AppService.getInstance().initPerStat(getTaskId(), sStatKinds[0]);
    }
   @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(event event) {
        if(event instanceof RhxthmEvent) {
            mCurrentPosition=((RhxthmEvent) event).getPosition();
            mViewPager.setCurrentItem(mCurrentPosition,true);
        }else if(event instanceof StatEvent){

            if(mAnimator!=null) {
                mAnimator.cancel();
                mAnimator=null;
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh :
                refreshData();
                break;
            default:break;
        }
    }
}
