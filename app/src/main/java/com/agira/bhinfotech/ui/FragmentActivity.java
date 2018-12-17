package com.agira.bhinfotech.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.agira.bhinfotech.R;
import com.agira.bhinfotech.modal.Product;
import com.agira.bhinfotech.ui.base.BaseActivity;
import com.agira.bhinfotech.ui.sub.InvoiceFragment;

import java.util.ArrayList;

public class FragmentActivity extends BaseActivity {

    final int res = R.id.fragment_container;

    public void addFragment(Fragment frag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(res, frag)
                .commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        //https://github.com/HendrixString/Android-PdfMyXmlc
        Bundle bundle = this.getIntent().getExtras();
        Fragment fragment = new InvoiceFragment();
        fragment.setArguments(bundle);
        addFragment(fragment);

    }

    public static void startActivity(Activity activity, ArrayList<Product> mDataList) {
        Intent intent = new Intent(activity, FragmentActivity.class);
        intent.putExtra(SHARE_DATA, mDataList);
        activity.startActivity(intent);
    }

}
