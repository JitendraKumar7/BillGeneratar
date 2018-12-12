package com.agira.bhinfotech.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.agira.bhinfotech.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class GestureOverlayViewDialog extends AlertDialog {

    @BindView(R.id.sign_pad)
    GestureOverlayView gov;

    Bitmap getSignature() {

        // First destroy cached image.
        gov.destroyDrawingCache();

        // Enable drawing cache function.
        gov.setDrawingCacheEnabled(true);

        // Get drawing cache bitmap.
        Bitmap drawingCacheBitmap = gov.getDrawingCache();

        // Create a new bitmap
        return Bitmap.createBitmap(drawingCacheBitmap);
    }

    @OnClick({R.id.btnSave,
            R.id.btnRedraw})
    public void onclick(View v) {

        switch (v.getId()) {

            case R.id.btnSave: {
                getSignature(getSignature());
                break;
            }

            case R.id.btnRedraw: {
                gov.clear(false);
                break;
            }
        }
    }

    protected abstract void getSignature(Bitmap bitmap);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(layoutResourcesID);
        ButterKnife.bind(this);
    }

    protected GestureOverlayViewDialog(Context context) {
        super(context);
    }

    private final int layoutResourcesID = R.layout.dialog_gesture_overlay_view;

}
