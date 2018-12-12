package com.agira.bhinfotech.app;

import android.content.Context;
import android.util.Log;

import com.agira.bhinfotech.utility.Utility;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class Networking implements AppConstant {

    private Context _context;

    public interface Listener {
        void onCompleted(String result);
    }

    public Networking(Context _context) {

        this._context = _context;
    }

    public void getHome(final Listener _Listener) {

        Ion.with(_context).load(URL)
                //.setLogging(TAG, Log.VERBOSE)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Utility.log_d(e.getMessage(), e);
                        } else if (_Listener != null) {
                            _Listener.onCompleted(result);
                        }
                    }
                });
    }
}
