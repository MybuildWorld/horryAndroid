package com.example.horryxiao.zhihu;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.horryxiao.zhihu.Adapter.ItemAdapter;
import com.example.horryxiao.zhihu.UI.DividerItemDecoration;
import com.example.horryxiao.zhihu.Util.Util;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    boolean isNight;
    DrawerLayout drawer;
    int navItemUncheck;
    RecyclerView recyclerView;
    ItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Util.swapTheme(this);
        setContentView(R.layout.activity_main);
        init();
    }

   public void init(){
       recyclerView= (RecyclerView) findViewById(R.id.recycleView);
       adapter=ItemAdapter.getInstance(this,20);
       recyclerView.setAdapter(adapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration();
       recyclerView.addItemDecoration(itemDecoration);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
           }
       });
       drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
               this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       drawer.setDrawerListener(toggle);
       toggle.syncState();

       navigationView = (NavigationView) findViewById(R.id.nav_view);
       navigationView.setNavigationItemSelectedListener(this);
       navigationView.setItemIconTintList(null);
       navItemUncheck=getValueofColorAttr(R.attr.textcolorMyValue);
       navigationView.setItemTextColor(ColorStateList.valueOf(navItemUncheck));
       Log.d("TAG",String.valueOf(navItemUncheck));
   }

    public int getValueofColorAttr(int attr){
        int[] attribute=new int[]{attr};
        TypedArray array=obtainStyledAttributes(attribute);
        int color=array.getColor(0,0);
        array.recycle();
        return color;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.home) {
             selectItemStyle(navItemUncheck,getResources().getColor(R.color.homechecked1),R.drawable.compassitem);
        } else if (id == R.id.search) {
             selectItemStyle(navItemUncheck,getResources().getColor(R.color.searchchecked2),R.drawable.searchitem);
        } else if (id == R.id.focus) {
             selectItemStyle(navItemUncheck,getResources().getColor(R.color.focuschecked3),R.drawable.focusitem);
        } else if (id == R.id.collection) {
            selectItemStyle(navItemUncheck,getResources().getColor(R.color.collectionchecked4),R.drawable.collectionitem);
        } else if(id==R.id.meet){
            selectItemStyle(navItemUncheck,getResources().getColor(R.color.meetchecked5),R.drawable.meetitem);
        } else if (id == R.id.message) {
            selectItemStyle(navItemUncheck,getResources().getColor(R.color.messegechecked6),R.drawable.messegeitem);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void selectItemStyle(int uncheck,int checked,int itembackground){
        int[][] states = new int[][] {
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_checked}  // pressed
        };
        int[] colors = new int[] {
                uncheck,
                checked
        };
        ColorStateList myList = new ColorStateList(states, colors);
        navigationView.setItemTextColor(myList);
        navigationView.setItemBackgroundResource(itembackground);
    }

    public void SwapThemeClick(View v){
        SharedPreferences prefer  =getSharedPreferences(Util.SharePreference_name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=prefer.edit();
        boolean isNight=prefer.getBoolean(Util.isNight,false);
        if(isNight)
        {
            editor.putBoolean(Util.isNight,false);
            editor.commit();
            drawer.closeDrawer(GravityCompat.START);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            this.recreate();
        }
        else
        {
            editor.putBoolean(Util.isNight,true);
            editor.commit();
            drawer.closeDrawer(GravityCompat.START);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            this.recreate();
        }

    }
    public void SettingClick(View v){

        }



}




