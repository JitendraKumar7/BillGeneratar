package com.agira.bhinfotech;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.agira.bhinfotech.ui.MainActivity;
import com.agira.bhinfotech.utility.Utility;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MainActivity.startActivity(SplashActivity.this);
                // https://github.com/koush/ion
            }
        };

        final int DELAY = 3000;
        Handler mHandler = new Handler();
        mHandler.postDelayed(runnable, DELAY);

    }

}