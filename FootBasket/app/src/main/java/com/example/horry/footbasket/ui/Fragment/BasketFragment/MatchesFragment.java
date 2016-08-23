package com.example.horry.footbasket.ui.Fragment.BasketFragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.Games;
import com.example.horry.footbasket.events.GamesEvent;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.base.swipefreshFragment;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.MatchAdapter;
import com.example.horry.footbasket.utils.DateFomatter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MatchesFragment extends swipefreshFragment {
    @BindView(R.id.rv_news)
    RecyclerView mGamesListView;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    private MatchAdapter mGamesAdapter;

    private String mDate;
    private String mDateToday = DateFomatter.formatDate("yyyy-MM-dd");
    private List<Games.GamesEntity> mGamesEntity = new ArrayList<>();
    public static MatchesFragment newInstance() {
        MatchesFragment gamesFragment = new MatchesFragment();
        return gamesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initViews() {
        super.initViews();
        initBar();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mGamesListView.getContext());
        mGamesListView.setLayoutManager(linearLayoutManager);
        mGamesAdapter=new MatchAdapter(getActivity(),mGamesEntity);
        mGamesListView.setAdapter(mGamesAdapter);
        mDate=mDateToday;
        setRefreshing();
    }
    public void initBar(){
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("赛程");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_matches;
    }

    @Subscribe
    public void onEventMainThread(GamesEvent gamesEvent) {
        stopRefreshing();
        if(constant.Result.FAIL.equals(gamesEvent.getEventResult())){
            return;
        }
        mGamesEntity.clear();
        mGamesEntity.addAll(gamesEvent.getAllGames().getGames());
        mGamesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_date, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.date :
                chooseDate();
                break;
            default:break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void chooseDate() {
        if(isRefreshing()) {
            return;
        }
        DatePickerPopWin datePicker = new DatePickerPopWin.Builder(getActivity(), new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                mDate=dateDesc;
                setRefreshing();
            }
        }).colorConfirm(Color.parseColor("#448AFF"))//color of confirm button
                .minYear(2015) //min year in loop
                .maxYear(2017) // max year in loop
                .build();
        datePicker.showPopWin(getActivity());
    }

    @Override
    public void onRefresh() {
        AppService.getInstance().getGames(getTaskId(),mDate);
        mDate=mDateToday;
    }
}
