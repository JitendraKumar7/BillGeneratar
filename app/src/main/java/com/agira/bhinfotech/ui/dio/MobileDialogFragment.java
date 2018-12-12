package com.agira.bhinfotech.ui.dio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.agira.bhinfotech.R;
import com.agira.bhinfotech.app.AppConstant;
import com.agira.bhinfotech.utility.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MobileDialogFragment extends DialogFragment implements AppConstant {

    View _rootView;
    static Uri apkURI;

    @BindView(R.id.edit_txtMobile)
    EditText editMobile;

    @OnClick(R.id.btnSubmit)
    public void onclick(View v) {

        switch (v.getId()) {

            case R.id.btnSubmit: {
                String txtMobile = editMobile.getText().toString().trim();
                if (txtMobile.length() == 10) {
                    try {
                        Bundle bundle = getArguments();
                        Intent intent = new Intent("android.intent.action.MAIN");
                        String jidValue = String.format("91%s@s.whatsapp.net", txtMobile);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(Intent.EXTRA_STREAM, apkURI);
                        intent.putExtra(Intent.EXTRA_SUBJECT, TAG);
                        intent.putExtra(Intent.EXTRA_TEXT, TEXT);
                        intent.putExtra("jid", jidValue);
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setPackage("com.whatsapp");
                        intent.setType("application/pdf");
                        startActivity(intent);
                        dismiss();
                    } catch (Exception e) {
                        Utility.log_d(e.getMessage(), e);
                    }
                } else {
                    editMobile.setError("Required");
                }
                break;
            }
        }
    }

    public static MobileDialogFragment getInstance(Uri apkURI) {
        MobileDialogFragment.apkURI = apkURI;
        return new MobileDialogFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        _rootView = inflater.inflate(R.layout.dialog_get_mobile, container, false);
        ButterKnife.bind(this, _rootView);
        getDialog().setTitle("Share");
        return _rootView;
    }

}