package com.example.horry.footbasket.ui.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.horry.footbasket.R;
import com.example.horry.footbasket.app.App;
import com.example.horry.footbasket.data.constant;
import com.example.horry.footbasket.entity.football.Assit;
import com.example.horry.footbasket.events.event;
import com.example.horry.footbasket.ui.adapter.RecycleAdapter.FootBall.AssitAdapter;
import com.example.horry.footbasket.ui.widgets.SpaceItemDecoration;
import com.example.horry.footbasket.utils.ScreenUtil;
import com.example.horry.footbasket.utils.StringUtil;

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
public class AssitFragment extends BaseFragment implements Response.Listener<String>,Response.ErrorListener{
    @BindView(R.id.colunm)
    LinearLayout linearLayout;
    @BindView(R.id.rv_assist)
    RecyclerView recyclerView;
    List<Assit> assitList=new ArrayList<>();
    AssitAdapter adapter;
    Map<String,String> map=new HashMap<>();
    String url;
    String type;

    public static AssitFragment newInstance(){
        AssitFragment fragment=new AssitFragment();
        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        type=args.getString(StringUtil.key);
    }

    @Override
    protected void initViews() {
        Log.d("TAG","INIT");
        initmap();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration=new SpaceItemDecoration(ScreenUtil.dp2px(1));
        recyclerView.addItemDecoration(itemDecoration);
        setAdapter();
    }
    public void setAdapter(){
        url=map.get(type);
        Log.d("URL", url);
        adapter=new AssitAdapter(getActivity(), assitList);
        recyclerView.setAdapter(adapter);
        loadDataServer();
    }

    public void initmap(){
        map.put("中超", constant.URL_ASSIST_CHINA);
        map.put("英超",constant.URL_ASSIST_ENGLAND);
        map.put("西甲",constant.URL_ASSIST_SPAIN);
        map.put("德甲", constant.URL_ASSIST_GERMANY);
        map.put("意甲", constant.URL_ASSIST_ITALY);
    }
    private void parseJson(String s) {
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String person_id = jsonObject.getString("person_id");
                String name = jsonObject.getString("name");
                String team_id = jsonObject.getString("team_id");
                String team_name = jsonObject.getString("team_name");
                String count = jsonObject.getString("count");
                Assit assist = new Assit(person_id, name, team_id, team_name, count);
                assitList.add(assist);
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        return R.layout.fragment_assit;
    }



    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.d("Load","No");
        volleyError.printStackTrace();
    }

    @Override
    public void onResponse(String s) {
        Log.d("Loa","YES");
        parseJson(s);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventMain(event event){

    }
}
