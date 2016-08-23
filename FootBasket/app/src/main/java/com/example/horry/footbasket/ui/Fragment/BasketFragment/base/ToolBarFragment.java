package com.example.horry.footbasket.ui.Fragment.BasketFragment.base;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.horry.footbasket.R;
import com.example.horry.footbasket.ui.Fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/4.
 */
public abstract  class ToolBarFragment extends BaseFragment {


    @Override
    protected void initViews() {

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
