package com.example.administrator.dt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;

import com.astuetz.PagerSlidingTabStrip;
import com.example.administrator.dt.UI.CircleImageDrawble;
import com.example.administrator.dt.adapter.pagerAdapter;
import com.example.administrator.dt.util.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.viewPager)
    ViewPager pager;
    @InjectView(R.id.indicator)
    PagerSlidingTabStrip mIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionbar();
        ButterKnife.inject(this);
        pagerAdapter adapter=new pagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        mIndicator.setViewPager(pager);
        setListener();
    }
    public void setListener() {
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {

                Log.d("Tag",String.valueOf(position));
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void setActionbar() {

        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_default_face);
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setIcon(new CircleImageDrawble(mBitmap));
        mActionbar.setTitle("用户");
        mActionbar.setHomeButtonEnabled(true);
        setOverflowShowingAlways();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Intent i = new Intent(this, PersonActivity.class);
                startActivity(i);
                break;
            default: return true;

        }

        return super.onOptionsItemSelected(item);
    }

    // 设置显示Overflow按钮，即使有物理Menu按钮
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);

    };

}
