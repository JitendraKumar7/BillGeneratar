package com.agira.bhinfotech.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.agira.bhinfotech.R;
import com.agira.bhinfotech.adapter.ProductAdapter;
import com.agira.bhinfotech.modal.Product;
import com.agira.bhinfotech.ui.base.BaseActivity;
import com.agira.bhinfotech.utility.Utility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<Product> mDataList;
    private ProductAdapter mAdapter;

    @OnClick(R.id.btnNext)
    public void onClick() {
        ArrayList<Product> mTempList = new ArrayList<>();
        for (Product bean : mDataList) {

            if (bean.getCount() > 0) {
                mTempList.add(bean);
            }
        }
        if (mTempList.size() > 0) {
            if (Utility.requestPermission(activity)) {
                FragmentActivity.startActivity(activity, mTempList);
            }
        } else {
            toastShow("Select any item");
        }
    }

    private void prepareProductData() {
        Product Product = new Product("Mad Max: Fury Road", "Action & Adventure", "2015");
        mDataList.add(Product);

        Product = new Product("Inside Out", "Animation, Kids & Family", "2015");
        mDataList.add(Product);

        Product = new Product("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        mDataList.add(Product);

        Product = new Product("Shaun the Sheep", "Animation", "2015");
        mDataList.add(Product);

        Product = new Product("The Martian", "Science Fiction & Fantasy", "2015");
        mDataList.add(Product);

        Product = new Product("Mission: Impossible Rogue Nation", "Action", "2015");
        mDataList.add(Product);

        Product = new Product("Up", "Animation", "2009");
        mDataList.add(Product);

        Product = new Product("Star Trek", "Science Fiction", "2009");
        mDataList.add(Product);

        Product = new Product("The LEGO Product", "Animation", "2014");
        mDataList.add(Product);

        Product = new Product("Iron Man", "Action & Adventure", "2008");
        mDataList.add(Product);

        Product = new Product("Aliens", "Science Fiction", "1986");
        mDataList.add(Product);

        Product = new Product("Chicken Run", "Animation", "2000");
        mDataList.add(Product);

        Product = new Product("Back to the Future", "Science Fiction", "1985");
        mDataList.add(Product);

        Product = new Product("Raiders of the Lost Ark", "Action & Adventure", "1981");
        mDataList.add(Product);

        Product = new Product("Goldfinger", "Action & Adventure", "1965");
        mDataList.add(Product);

        Product = new Product("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        mDataList.add(Product);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDataList = new LinkedList<>();
        mAdapter = new ProductAdapter(mDataList) {
            @Override
            protected void onItemClick(View v, int position) {
                Product bean = mDataList.get(position);

                switch (v.getId()) {

                    case R.id.btnAdd: {
                        int count = bean.getCount();
                        bean.setCount(count + 1);
                        break;
                    }

                    case R.id.btnRemove: {
                        int count = bean.getCount();
                        bean.setCount(count - 1);
                        break;
                    }

                }

                mAdapter.notifyItemChanged(position);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        prepareProductData();

    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finishAffinity();
    }

}

