package com.agira.bhinfotech;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.agira.bhinfotech.ui.MainActivity;
import com.agira.bhinfotech.utility.Utility;

public class SplashActivity extends AppCompatActivity implements Runnable {

    @Override
    public void run() {
        MainActivity.startActivity(SplashActivity.this);
        // https://github.com/koush/ion
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final int DELAY = 3000;
        Handler mHandler = new Handler();
        boolean b = mHandler.postDelayed(this, DELAY);
        Utility.log_d(String.format("Post Delayed %s", b));

    }

}