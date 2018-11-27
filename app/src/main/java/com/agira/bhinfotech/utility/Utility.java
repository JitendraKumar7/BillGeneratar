package com.agira.bhinfotech.utility;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.agira.bhinfotech.app.AppConstant;

public class Utility implements AppConstant {

    public static void log_d(Object msg) {

        Log.d(TAG, String.valueOf(msg));
    }

    public static void log_d(Object msg, Throwable e) {

        Log.d(TAG, String.valueOf(msg), e);
    }

    public static boolean requestPermission(Activity activity) {
        int writePermissionCheck = ContextCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);
        int readPermissionCheck = ContextCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE);
        String[] permission = new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};

        if (readPermissionCheck != PackageManager.PERMISSION_GRANTED &&
                writePermissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, permission, PERMISSION_CODE);
            return false;
        }
        return true;
    }

}
