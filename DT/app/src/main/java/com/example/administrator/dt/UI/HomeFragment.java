package com.example.administrator.dt.UI;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.example.administrator.dt.R;
import com.example.administrator.dt.adapter.StaggerItemAdapter;
import com.example.administrator.dt.adapter.TopItemAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ViewPager imagePager;

    ClumsyIndicator indicator;
    @InjectView(R.id.stagger_view)
    StaggeredGridView staggerView;

    @InjectView(R.id.tofresh)
    SwipeRefreshLayout refreshLayout;

    private Handler mhandler;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//       setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.home_fragment,container,false);
        ButterKnife.inject(this,root);
        View topView=inflater.inflate(R.layout.top_view,(ViewGroup)root,false);
        imagePager=ButterKnife.findById(topView,R.id.image_pager);
        indicator=ButterKnife.findById(topView,R.id.pager_indicator);
        staggerView.addHeaderView(topView);
        initView();
        mhandler=new Handler();
        return root;
    }
    public void initView(){
            imagePager.setAdapter(new TopItemAdapter(getContext()));
            indicator.setViewPager(imagePager);
            staggerView.setAdapter(new StaggerItemAdapter(getContext()));
            refreshLayout.setOnRefreshListener(this);
            refreshLayout.setColorSchemeColors(R.color.colorAccent);

        imagePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.setSelectedItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void onRefresh() {
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),R.string.refresh,Toast.LENGTH_SHORT).show();
            }
        },3000);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            int currentItem=savedInstanceState.getInt(key);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mhandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(key,imagePager.getCurrentItem());
    }
    private static final String key ="current_item";
}
