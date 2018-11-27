package com.agira.bhinfotech.ui;

import android.os.Bundle;

import com.agira.bhinfotech.R;
import com.agira.bhinfotech.ui.base.BaseActivity;

import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

}
