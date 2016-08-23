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
import com.example.horry.footbasket.entity.Games;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/4.
 */
public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.GamesViewHolder> {

    private final static Map<String,Integer> sTeamIconMap= constant.getTeamIcons();
    private List<Games.GamesEntity> mGames;
    protected Context mContext;
    protected LayoutInflater mInflater;
    private static final int GAMES_DATE=0;

    public MatchAdapter(Context context,List<Games.GamesEntity> teams){
        super();
        this.mContext = context;
        this.mGames=teams;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return mGames.get(position).getType();
    }

    @Override
    public GamesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GamesViewHolder gamesViewHolder;
        if(viewType==GAMES_DATE) {
            gamesViewHolder=new GameTitleViewHolder(mInflater.inflate(R.layout.item_data_title,parent,false));
        }else {
            gamesViewHolder=new GameEntutyViewHolder(mInflater.inflate(R.layout.matches_games_item,parent,false));
        }

        return gamesViewHolder;
    }

    @Override
    public void onBindViewHolder(GamesViewHolder holder, int position) {
        holder.updateItem(position);
    }

    @Override
    public int getItemCount() {
        return mGames==null?0:mGames.size();
    }

    abstract class GamesViewHolder extends RecyclerView.ViewHolder {

        public GamesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        abstract void updateItem(int position);

    }

    class GameTitleViewHolder extends GamesViewHolder {
        @BindView(R.id.teams_title)
        TextView mDate_tv;

        public GameTitleViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void updateItem(int position) {
            mDate_tv.setText(mGames.get(position).getDate());
        }
    }

    class GameEntutyViewHolder extends GamesViewHolder implements View.OnClickListener {
        @BindView(R.id.left_teamName_tv)
        TextView leftTeamNameTv;
        @BindView(R.id.right_teamName_tv)
        TextView rightTeamNameTv;
        @BindView(R.id.left_teamIcon_iv)
        ImageView leftTeamIconTv;
        @BindView(R.id.right_teamIcon_iv)
        ImageView rightTeamIconTv;
        @BindView(R.id.gamestatus)
        TextView gameStatusTv;
        @BindView(R.id.scores)
        TextView scoresTv;
        @BindView(R.id.stats)
        TextView statsTv;
        @BindView(R.id.status_indicate)
        View statusIndicate;
        private Games.GamesEntity mGameEntity;
        public GameEntutyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            statsTv.setOnClickListener(this);

        }

        @Override
        void updateItem(int position) {
            mGameEntity=mGames.get(getLayoutPosition());
            if(mGameEntity.getStateText()!="") {
                statusIndicate.setVisibility(View.VISIBLE);
                if("已结束".equals(mGameEntity.getStatus())) {
                    statusIndicate.setBackgroundColor(Color.parseColor("#448AFF"));
                }else {
                    statusIndicate.setBackgroundColor(Color.parseColor("#f44336"));
                }
                statsTv.setVisibility(View.VISIBLE);
                statsTv.setText(mGameEntity.getStateText());
            }else {
                statusIndicate.setVisibility(View.GONE);
                statsTv.setVisibility(View.GONE);
            }
            leftTeamNameTv.setText(mGameEntity.getLeftTeam());
            leftTeamIconTv.setImageResource(sTeamIconMap.get(mGameEntity.getLeftTeam()));
            gameStatusTv.setText(mGameEntity.getStatus());
            rightTeamNameTv.setText(mGameEntity.getRightTeam());
            rightTeamIconTv.setImageResource(sTeamIconMap.get(mGameEntity.getRightTeam()));
            scoresTv.setText(mGameEntity.getLeftScore() + "-" + mGameEntity.getRightScore());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.stats:
                    new FinestWebView.Builder((Activity)mContext)
                            .gradientDivider(false)
                            .show(mGameEntity.getStateUrl());
                    break;
                default:
                    new FinestWebView.Builder((Activity)mContext)
                            .gradientDivider(false)
                            .show(mGameEntity.getStatusUrl());
                    break;
            }
        }
    }
}
