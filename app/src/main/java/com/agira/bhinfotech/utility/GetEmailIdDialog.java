package com.agira.bhinfotech.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.agira.bhinfotech.R;
import com.agira.bhinfotech.app.LayoutConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class GetEmailIdDialog extends
        AlertDialog implements LayoutConstant {

    @BindView(R.id.edit_txtEmailId)
    EditText editEmailId;

    @OnClick(R.id.btnSubmit)
    public void onclick(View v) {

        switch (v.getId()) {

            case R.id.btnSubmit: {
                String txtEmailId = editEmailId.getText().toString().trim();
                if (txtEmailId.length() > 10) {
                    getValue(txtEmailId);
                } else {
                    editEmailId.setError("Required");
                }
                break;
            }
        }
    }

    protected abstract void getValue(String value);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_EMAIL_ID);
        ButterKnife.bind(this);
    }

    protected GetEmailIdDialog(Context context) {
        super(context);
    }

}
