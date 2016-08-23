package com.example.horry.footbasket.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.events.event;
import com.example.horry.footbasket.ui.activity.AboutActivity;
import com.example.horry.footbasket.ui.activity.AssitActivity;
import com.example.horry.footbasket.ui.activity.GameDateActivity;
import com.example.horry.footbasket.ui.activity.RankActivity;
import com.example.horry.footbasket.ui.activity.ScoreActivity;
import com.example.horry.footbasket.ui.activity.playerSortActivity;
import com.example.horry.footbasket.ui.activity.setttingActivity;
import com.example.horry.footbasket.ui.activity.teamSortActivity;
import com.example.horry.footbasket.utils.StringUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/15.
 */
public class StatTabFragment extends BaseFragment {
    @BindView(R.id.nbaSort)
    RelativeLayout nbasort;
    @BindView(R.id.nbaGame)
    RelativeLayout nbagame;
    @BindView(R.id.nbaStat)
    RelativeLayout nbastat;
    @BindView(R.id.fbMark)
    RelativeLayout fbmarks;
    @BindView(R.id.fbScore)
    RelativeLayout fbscore;
    @BindView(R.id.fbAssit)
    RelativeLayout fbassit;
    @BindView(R.id.toolbar)
    Toolbar mtoolbar;
    public static StatTabFragment newInstance(){
       return new StatTabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initViews() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(mtoolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("数据");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_stat;
    }

    @OnClick(R.id.nbaSort)
    public void  nbaSort(){
        Intent intent=new Intent(getActivity(),teamSortActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.nbaGame)
    public void nbaGame(){
        Intent intent=new Intent(getActivity(), GameDateActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.nbaStat)
    public void nbaStat(){
        Intent intent=new Intent(getActivity(),playerSortActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.fbMark)
    public void fbMark(){
        Intent intent=new Intent(getActivity(), AssitActivity.class);
        intent.putExtra(StringUtil.key,"积分榜");
        startActivity(intent);
    }
    @OnClick(R.id.fbScore)
    public void fbScore(){
        Intent intent=new Intent(getActivity(), AssitActivity.class);
        intent.putExtra(StringUtil.key,"射手榜");
        startActivity(intent);
    }
    @OnClick(R.id.fbAssit)
    public void fbAssit(){
        Intent intent=new Intent(getActivity(), AssitActivity.class);
        intent.putExtra(StringUtil.key,"助攻榜");
        startActivity(intent);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventThread(event event){
        Log.d("StatTabFragment",event.getEventResult().toString());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.about:
                AboutActivity.navigateFrom(getActivity());
                break;
            case R.id.setting:
                setttingActivity.navigateFrom(getActivity());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
