package com.example.horry.footbasket.ui.adapter.RecycleAdapter.FootBall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.entity.football.Assit;
import com.example.horry.footbasket.entity.football.Rank;
import com.example.horry.footbasket.ui.Fragment.RankFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/16.
 */
public class RankAdapter extends RecyclerView.Adapter<RankAdapter.Holder> {
    List<Rank.Rankings> rankingses=new ArrayList<>();
    Context context;
    LayoutInflater mInflater;

    public RankAdapter(Context context,List<Rank.Rankings> rankingses){
        this.context=context;
        this.rankingses=rankingses;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder=null;
        switch (viewType){
            case 0:
                holder=new Holder(mInflater.inflate(R.layout.item_rank_focus,parent,false));
                break;
            case 1:
                holder=new Holder(mInflater.inflate(R.layout.item_rank,parent,false));
                break;
            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.updateView(position);
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0||position==1||position==2) return 0;
        else
            return 1;
    }

    @Override
    public int getItemCount() {
        return rankingses.size();
    }
    public class Holder extends RecyclerView.ViewHolder{
        Rank.Rankings rankings;
        @BindView(R.id.rank)
        TextView tvRank;
        @BindView(R.id.club_name)
        TextView tvClub_name;
        @BindView(R.id.matches_total)
        TextView tvTotal;
        @BindView(R.id.matches_won)
        TextView tvWon;
        @BindView(R.id.matches_lost)
        TextView tvLost;
        @BindView(R.id.matches_draw)
        TextView tvDraw;
        @BindView(R.id.goals_pro)
        TextView tvGoalsIn;
        @BindView(R.id.goals_against)
        TextView tvGoalsLose;
        @BindView(R.id.points)
        TextView tvPoints;
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void  updateView(int position){
            rankings=rankingses.get(position);
            tvRank.setText(rankings.rank);
            tvClub_name.setText(rankings.club_name);
            tvTotal.setText(rankings.matches_total);
            tvWon.setText(rankings.matches_won);
            tvLost.setText(rankings.matches_lost);
            tvDraw.setText(rankings.matches_draw);
            tvGoalsIn.setText(rankings.goals_pro);
            tvGoalsLose.setText(rankings.goals_against);
            tvPoints.setText(rankings.points);
        }
    }
}
