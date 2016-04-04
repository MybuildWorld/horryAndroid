package com.example.horryxiao.circleprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Circlelayout mCirclelayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCirclelayout= (Circlelayout) findViewById(R.id.circleprogress);
        mCirclelayout.startAnimation();
    }
}
