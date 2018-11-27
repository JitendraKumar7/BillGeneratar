package com.agira.bhinfotech.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agira.bhinfotech.R;
import com.agira.bhinfotech.modal.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.CustomViewHolder> {

    private List<Product> mDataList;

    protected abstract void onItemClick(View v, int position);

    class CustomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtName)
        TextView tvName;

        @BindView(R.id.txtDesc)
        TextView tvDesc;

        @BindView(R.id.txtPrice)
        TextView tvPrice;

        @BindView(R.id.txtCount)
        TextView tvCount;

        @OnClick({R.id.btnAdd,
                R.id.btnRemove})
        void onClick(View v) {

            onItemClick(v, getPosition());
        }

        void onBindData(int position) {
            Product bean = mDataList.get(position);

            tvName.setText(bean.getName());
            tvDesc.setText(bean.getBrand());
            tvPrice.setText(Html.fromHtml(bean.getPrice()));
            tvCount.setText(String.valueOf(bean.getCount()));
        }

        CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    protected ProductAdapter(List<Product> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ui_row_product, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.onBindData(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

}