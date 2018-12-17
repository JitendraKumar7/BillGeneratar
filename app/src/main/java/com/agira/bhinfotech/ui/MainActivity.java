package com.agira.bhinfotech.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.agira.bhinfotech.R;
import com.agira.bhinfotech.adapter.ProductAdapter;
import com.agira.bhinfotech.app.IonService;
import com.agira.bhinfotech.modal.Response;
import com.agira.bhinfotech.modal.Product;
import com.agira.bhinfotech.modal.response.Home;
import com.agira.bhinfotech.ui.base.BaseActivity;
import com.agira.bhinfotech.utility.Utility;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btnNext)
    AppCompatButton btnNext;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;

    private List<Product> mDataList;
    private ProductAdapter mAdapter;
    private AlertDialog alertDialog;

    @OnClick(R.id.btnNext)
    public void onClick() {
        ArrayList<Product> mTempList = new ArrayList<>();
        for (Product bean : mAdapter.getDataList()) {

            if (bean.getCount() > 0) {
                mTempList.add(bean);
            }
        }
        if (mTempList.size() > 0) {
            if (Utility.requestPermission(getActivity())) {
                FragmentActivity.startActivity(getActivity(), mTempList);
            }
        } else {
            showToast("Please Select");
        }
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(getActivity());

        setSupportActionBar(toolbar);
        mDataList = new LinkedList<>();
        mAdapter = new ProductAdapter(mDataList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .build();
        alertDialog.show();
        getIonService().getHome(new IonService.Listener() {

            @Override
            public void onCompleted(String result) {

                Home home = Response.getHome(result);

                if (home.isStatus()) {
                    mDataList.addAll(home.getProduct());
                    mAdapter.notifyDataSetChanged();
                } else {
                    showToast(home.getMsg());
                }
                alertDialog.dismiss();
            }
        });

        mSearchView.setVoiceSearch(true);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Utility.log_d("onQueryTextSubmit " + query);
                mAdapter.getFilter().filter(query);
                mSearchView.setSuggestions(null);
                mSearchView.dismissSuggestions();
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Utility.log_d("onQueryTextChange " + newText);
                return false;
            }
        });
        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                btnNext.setVisibility(View.GONE);
                Utility.log_d("onSearchViewShown ");
            }

            @Override
            public void onSearchViewClosed() {
                btnNext.setVisibility(View.VISIBLE);
                mAdapter.getFilter().filter("");
                Utility.log_d("onSearchViewClosed ");
            }
        });

    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finishAffinity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            AddActivity.startActivity(getActivity());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    mSearchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

}