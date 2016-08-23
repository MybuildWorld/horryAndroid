package com.example.horry.footbasket.ui.adapter.RecycleAdapter.FootBall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.entity.football.Assit;
import com.example.horry.footbasket.entity.football.Scorer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/16.
 */
public class ScorerAdapter extends RecyclerView.Adapter<ScorerAdapter.Holder> {
    List<Scorer> scorerList=new ArrayList<>();
    Context context;
    LayoutInflater mInflater;

    public ScorerAdapter(Context context,List<Scorer> scorers){
        this.context=context;
        scorerList=scorers;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder=null;
        switch (viewType){
            case 0:
                holder=new Holder(mInflater.inflate(R.layout.item_assit_focus,parent,false));
                break;
            case 1:
                holder=new Holder(mInflater.inflate(R.layout.item_assit,parent,false));
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
        return scorerList.size();
    }
    public class Holder extends RecyclerView.ViewHolder{
        Scorer scorer;
        @BindView(R.id.rank)
        TextView tvRank;
        @BindView(R.id.name)
        TextView tvName;
        @BindView(R.id.team_name)
        TextView tvTeam;
        @BindView(R.id.count)
        TextView tvCount;
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void  updateView(int position){
            scorer=scorerList.get(position);
            tvRank.setText(position+1+"");
            tvCount.setText(scorer.count);
            tvName.setText(scorer.name);
            tvTeam.setText(scorer.team_name);
        }
    }
}
