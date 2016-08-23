package com.example.horry.footbasket.ui.Fragment.BasketFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.AppService;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.Teams;
import com.example.horry.footbasket.events.TeamSortEvent;
import com.example.horry.footbasket.ui.Fragment.BasketFragment.base.swipefreshFragment;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.TeamSortAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class TeamSortFragment extends swipefreshFragment {

    @BindView(R.id.rv_news)
    RecyclerView mTeamsListView;
    private TeamSortAdapter mTeamSortAdapter;
    protected List<Teams.TeamsortEntity> mTeamsSortEntity = new ArrayList<>();

    public static TeamSortFragment newInstance() {
        TeamSortFragment teamSortFragment = new TeamSortFragment();
        return teamSortFragment;
    }

    @Override
    protected void initViews() {
        super.initViews();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mTeamsListView.getContext());
        mTeamsListView.setLayoutManager(linearLayoutManager);
        mTeamSortAdapter=new TeamSortAdapter(getActivity(),mTeamsSortEntity);
        mTeamsListView.setAdapter(mTeamSortAdapter);
        setRefreshing();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_news;
    }

    @Override
    public void onRefresh() {
        AppService.getInstance().getTeamSort(getTaskId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TeamSortEvent teamSortEvent) {
        stopRefreshing();
        if(constant.Result.FAIL.equals(teamSortEvent.getEventResult())){
            return;
        }
        mTeamsSortEntity.clear();
        mTeamsSortEntity.addAll(teamSortEvent.getmTeams().getTeamsort());
        mTeamSortAdapter.notifyDataSetChanged();
    }
}
