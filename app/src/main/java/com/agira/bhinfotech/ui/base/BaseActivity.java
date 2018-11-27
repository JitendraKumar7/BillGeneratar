package com.agira.bhinfotech.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.agira.bhinfotech.app.AppConstant;
import com.agira.bhinfotech.utility.Utility;

public abstract class BaseActivity extends AppCompatActivity implements AppConstant {

    protected Activity activity = BaseActivity.this;

    protected void toastShow(String msg) {

        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.requestPermission(activity);
    }

}
