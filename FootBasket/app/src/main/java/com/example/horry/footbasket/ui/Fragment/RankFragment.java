package com.example.horry.footbasket.ui.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.football.Rank;
import com.example.horry.footbasket.entity.football.Scorer;
import com.example.horry.footbasket.events.event;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.FootBall.RankAdapter;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.FootBall.ScorerAdapter;
import com.example.horry.footbasket.ui.widgets.SpaceItemDecoration;
import com.example.horry.footbasket.utils.ScreenUtil;
import com.example.horry.footbasket.utils.StringUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/16.
 */
public class RankFragment extends BaseFragment implements Response.Listener<String>,Response.ErrorListener {
    @BindView(R.id.rv_rank)
    RecyclerView recyclerView;
    List<Rank.Rankings> rankingses=new ArrayList<>();
    Rank rank;
    RankAdapter adapter;
    Map<String,String> map=new HashMap<>();
    String url;
    String type;

    public static RankFragment newInstance(){
        RankFragment fragment=new RankFragment();
        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        type=args.getString(StringUtil.key);
        Log.d("TAG",type);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        initmap();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration=new SpaceItemDecoration(ScreenUtil.dp2px(1));
        recyclerView.addItemDecoration(itemDecoration);
        setAdapter();
    }
    public void setAdapter(){
        url=map.get(type);
        Log.d("URL",url);
        adapter= new RankAdapter(getActivity(),rankingses);
        recyclerView.setAdapter(adapter);
        loadDataServer();
    }

    public void initmap(){
        map.put("中超", constant.URL_RANK_CHINA);
        map.put("英超",constant.URL_RANK_ENGLAND);
        map.put("西甲",constant.URL_RANK_SPAIN);
        map.put("德甲",constant.URL_RANK_GERMANY);
        map.put("意甲", constant.URL_RANK_ITALY);
    }

    public void loadDataServer(){
        //创建请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(App.getContext());
        //创建请求
        StringRequest stringRequest = new StringRequest(url, this, this);
        //将请求添加到请求队列
        requestQueue.add(stringRequest);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_rank;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    @Override
    public void onResponse(String s) {
        //处理字符串
        String jsonString = s.substring(1, s.length()-1);
        //Json解析
        Gson gson = new Gson();
        rank = gson.fromJson(jsonString, Rank.class);
        rankingses.addAll(rank.rankings);

        adapter.notifyDataSetChanged();

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventMain(event event){

    }
}
