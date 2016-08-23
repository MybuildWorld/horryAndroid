package com.example.horry.footbasket.ui.adapter.RecycleAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.Teams;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/4.
 */
public class TeamSortAdapter extends RecyclerView.Adapter<TeamSortAdapter.TeamHolder> {

    private final Map<String,Integer> teamIconMap= constant.getTeamIcons();
    private List<Teams.TeamsortEntity> mTeams;
    protected Context mContext;
    protected LayoutInflater mInflater;
    private static final int TEAMS_TITLE=0;
    private static final int TEAMS_ENTITY=1;

    public TeamSortAdapter(Context context,List<Teams.TeamsortEntity> teams) {
        super();
        this.mContext = context;
        this.mTeams=teams;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public TeamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TeamHolder teamHolder;
        if(viewType==TEAMS_TITLE) {
            teamHolder=new TeamTitle(mInflater.inflate(R.layout.teamsort_title_item,parent,false));
        }else {
            teamHolder=new TeamEntity(mInflater.inflate(R.layout.teamsort_list_item,parent,false));
        }

        return teamHolder;
    }
    @Override
    public int getItemViewType(int position) {
        if(position%17==0) {
            return TEAMS_TITLE;
        }else {
            return TEAMS_ENTITY;
        }
    }


    @Override
    public void onBindViewHolder(TeamHolder holder, int position) {
        holder.updateItem(position);
    }

    @Override
    public int getItemCount() {
        return mTeams==null?0:mTeams.size()+2;
    }

    abstract class TeamHolder extends RecyclerView.ViewHolder {

        public TeamHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        abstract void updateItem(int position);
    }

    class TeamTitle extends TeamHolder {
        @BindView(R.id.teams_title)
        TextView mTeamTitle_tv;
        public TeamTitle(View itemView) {
            super(itemView);
        }

        @Override
        void updateItem(int position) {
            if(mTeams==null||mTeams.size()==0){
                return;
            }
            if(position==0) {
                mTeamTitle_tv.setText("东部球队");
            }else {
                mTeamTitle_tv.setText("西部球队");
            }

        }
    }

    class TeamEntity extends TeamHolder implements View.OnClickListener{
        @BindView(R.id.place)
        TextView mTeamPlace_tv;
        @BindView(R.id.team_icon)
        ImageView mTeamicon_IV;
        @BindView(R.id.team_name)
        TextView mTeamName_tv;
        @BindView(R.id.win)
        TextView mTeamWin_tv;
        @BindView(R.id.lose)
        TextView mTeamLose_tv;
        @BindView(R.id.win_percent)
        TextView mTeamWinPer_tv;
        @BindView(R.id.gap)
        TextView mTeamGap_tv;
        @BindView(R.id.divider)
        View divider;
        @BindView(R.id.itemLayout)
        View mItemLayout;
        private Teams.TeamsortEntity mTeam;
        public TeamEntity(View itemView) {
            super(itemView);
            mItemLayout.setOnClickListener(this);
        }
        @Override
        void updateItem(int position) {
            if(mTeams==null||mTeams.size()==0){
                return;
            }
            int index=0;
            if(position>17) {
                index=position-2;
            }else if(position>0){
                index=position-1;
            }
            mTeam=mTeams.get(index);
            mTeamPlace_tv.setText(mTeam.getSort());

            mTeamName_tv.setText(mTeam.getTeam());
            mTeamWin_tv.setText(mTeam.getWin());
            mTeamLose_tv.setText(mTeam.getLose());
            mTeamWinPer_tv.setText(mTeam.getWinPercent());
            mTeamGap_tv.setText(mTeam.getGap());
            if(index==0||index==16) {
                mTeamPlace_tv.setText("");
                divider.setVisibility(View.VISIBLE);
                mTeamicon_IV.setVisibility(View.INVISIBLE);
                mTeam=null;

            }else {
                mTeamicon_IV.setVisibility(View.VISIBLE);
                mTeamicon_IV.setImageResource(teamIconMap.get(mTeam.getTeam()));
                divider.setVisibility(View.GONE);
            }

            if((index>0&&index<9)||(index>16&&index<25)) {
                mTeamPlace_tv.setTextColor(Color.parseColor("#f44336"));
            }else {
                mTeamPlace_tv.setTextColor(Color.parseColor("#212121"));
            }
        }

        @Override
        public void onClick(View view) {
            if(mTeam==null) {
                return;
            }
            new FinestWebView.Builder((Activity)mContext)
                    .gradientDivider(false)
                    .show(mTeam.getTeamurl());
        }
    }

}
