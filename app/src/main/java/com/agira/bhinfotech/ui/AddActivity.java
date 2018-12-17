package com.agira.bhinfotech.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.agira.bhinfotech.R;
import com.agira.bhinfotech.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(getActivity());
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, AddActivity.class);
        activity.startActivity(intent);
    }

    /*
    * Crop.of(inputUri, outputUri).asSquare().start(activity)
    *
    * @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            doSomethingWithCroppedImage(outputUri);
        }
    }
    *
    *
    * Crop.pickImage(activity)
    *
    * */

}
