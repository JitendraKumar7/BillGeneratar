package com.agira.bhinfotech.ui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import com.agira.bhinfotech.app.AppConstant;
import com.agira.bhinfotech.app.IonService;
import com.agira.bhinfotech.utility.Utility;
import com.google.gson.Gson;

public abstract class BaseActivity extends AppCompatActivity implements AppConstant {

    private Toast mToast = null;
    private Gson mGson = new Gson();
    private IonService mIonService = null;
    private Activity mActivity = BaseActivity.this;


    public Gson getGson() {

        return mGson;
    }

    public Activity getActivity() {

        return mActivity;
    }

    public IonService getIonService() {

        if (mIonService == null) mIonService = new IonService(getActivity());
        return mIonService;
    }

    @SuppressLint("ShowToast")
    protected void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(msg);
        mToast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utility.requestPermission(getActivity());
    }

}
