package com.agira.bhinfotech.ui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import com.agira.bhinfotech.app.AppConstant;
import com.agira.bhinfotech.app.Networking;
import com.agira.bhinfotech.utility.Utility;
import com.google.gson.Gson;

public abstract class BaseActivity extends AppCompatActivity implements AppConstant {

    private Toast toast = null;
    private Gson gson = new Gson();
    private Networking mNetworking = null;
    private Activity activity = BaseActivity.this;


    public Gson getGson() {

        return gson;
    }

    public Activity getActivity() {

        return activity;
    }

    public Networking getNetworking() {

        return mNetworking;
    }

    @SuppressLint("ShowToast")
    protected void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.requestPermission(activity);

        mNetworking = new Networking(activity);
    }

}
