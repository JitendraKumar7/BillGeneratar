package com.agira.bhinfotech.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.agira.bhinfotech.R;
import com.agira.bhinfotech.adapter.ProductAdapter;
import com.agira.bhinfotech.app.Networking;
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

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<Product> mDataList;
    private ProductAdapter mAdapter;

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
            showToast("Select any item");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(getActivity());

        setSupportActionBar(toolbar);
        mDataList = new LinkedList<>();
        mAdapter = new ProductAdapter(mDataList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        getNetworking().getHome(new Networking.Listener() {

            @Override
            public void onCompleted(String result) {

                try {
                    Home home = Response.getHome(result);

                    if (home.isStatus()) {
                        mDataList.addAll(home.getProduct());
                        mAdapter.notifyDataSetChanged();
                    } else {
                       showToast(home.getMsg());
                    }

                } catch (Exception e) {

                    Utility.log_d(e.getMessage(), e);
                }
            }
        });

        mSearchView.setEllipsize(true);
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
                return true;
            }
        });
        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                Utility.log_d("onSearchViewShown ");
                // Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                Utility.log_d("onSearchViewClosed ");
                mAdapter.getFilter().filter("");
                // Do some magic
            }
        });

    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finishAffinity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
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