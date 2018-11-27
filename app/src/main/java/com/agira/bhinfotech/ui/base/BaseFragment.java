package com.agira.bhinfotech.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agira.bhinfotech.app.AppConstant;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements AppConstant {

    protected View _rootView;
    protected Activity _activity;

    protected abstract int getResourceId();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
    }

    protected abstract void onCreate(@NonNull LayoutInflater inflater);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        _rootView = inflater.inflate(getResourceId(), container, false);
        ButterKnife.bind(this, _rootView);
        onCreate(inflater);
        return _rootView;
    }

}
