package com.example.administrator.dt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TimeUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class WelcomeActivty extends Activity {
    @InjectView(R.id.imageWelcome)
    ImageView welcomeImage;
    @InjectView(R.id.count_down)
    TextView countdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_layout);
        ButterKnife.inject(this);
        init();
    }
    public void init(){
        countdown.setVisibility(View.INVISIBLE);
        CountDownTimer aTimer=new CountDownTimer(5200,1000) {
            int num=5;
            @Override
            public void onTick(long millisUntilFinished) {
                if(num<=3) {
                    welcomeImage.setImageResource(R.drawable.welcome_img);
                    countdown.setVisibility(View.VISIBLE);
                    countdown.setText(String.valueOf(num));
                }
                num--;
            }

            @Override
            public void onFinish() {
                  Intent intent=new Intent(WelcomeActivty.this,MainActivity.class);
                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  startActivity(intent);
                  finish();
            }
        };
        aTimer.start();
    }
}
