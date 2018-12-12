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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailDialogFragment extends DialogFragment implements AppConstant {

    View _rootView;
    static Uri fileURI;

    @BindView(R.id.edit_txtEmailId)
    EditText editEmailId;

    @OnClick(R.id.btnSubmit)
    public void onclick(View v) {

        switch (v.getId()) {

            case R.id.btnSubmit: {
                String txtEmailId = editEmailId.getText().toString().trim();
                if (txtEmailId.length() > 10) {
                    Uri uri = Uri.parse("mailto:" + txtEmailId);
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM, fileURI);
                    intent.putExtra(Intent.EXTRA_SUBJECT, TAG);
                    intent.putExtra(Intent.EXTRA_TEXT, TEXT);
                    startActivity(intent);
                    dismiss();
                } else {
                    editEmailId.setError("Required");
                }
                break;
            }
        }
    }

    public static EmailDialogFragment getInstance(Uri fileURI) {
        EmailDialogFragment.fileURI = fileURI;
        return new EmailDialogFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        _rootView = inflater.inflate(R.layout.dialog_get_email_id, container, false);
        ButterKnife.bind(this, _rootView);
        getDialog().setTitle("Share");
        return _rootView;
    }

}